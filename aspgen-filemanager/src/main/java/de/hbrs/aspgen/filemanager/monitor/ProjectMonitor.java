package de.hbrs.aspgen.filemanager.monitor;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.osgi.service.log.LogService;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.dir.DirectoryObserver;
import de.hbrs.aspgen.api.dir.OberservableDirectoryMonitor;
import de.hbrs.aspgen.api.file.ChangedCache;
import de.hbrs.aspgen.api.file.WrittenCache;
import de.hbrs.aspgen.filemanager.util.FileCache;

public class ProjectMonitor implements Runnable, OberservableDirectoryMonitor {
    private final List<DirectoryObserver> observers = new LinkedList<>();
    private volatile boolean stopRun = false;
    private final LogService logService;
    private WatchService watcher;
    private Map<WatchKey, Path> keys;
    private boolean trace = false;
    private final FileCache changedCache;
    private final FileCache writtenCache;

    @Inject
    public ProjectMonitor(@ChangedCache final FileCache changedCache,
            @WrittenCache final FileCache writtenCache,
            final LogService logService) {
        this.writtenCache = writtenCache;
        this.logService = logService;
        this.changedCache = changedCache;
    }

    @Override
    public void stopMonitor() {
        stopRun = true;
    }

    @Override
    public void register(final DirectoryObserver observer) {
        observers.add(observer);
    }

    @Override
    public void remove(final DirectoryObserver observer) {
        observers.remove(observer);
    }

    public void notifyListener(final FileEvents events) {
        if (events.getDeletedFiles().size() > 0) {
            logService.log(LogService.LOG_DEBUG, "Notify observers for " + events.getDeletedFiles().size() + " created files");
            for (final DirectoryObserver observer : observers) {
                observer.updateFileForDeletions(events.getDeletedFiles());
            }
        }

        if (events.getCreatedFiles().size() > 0) {
            logService.log(LogService.LOG_DEBUG, "Notify observers for " + events.getCreatedFiles().size() + " created files");
            for (final DirectoryObserver observer : observers) {
                observer.updateFileForCreations(events.getCreatedFiles());
            }
        }

        if (events.getChangedFiles().size() > 0) {
            logService.log(LogService.LOG_DEBUG, "Notify observers for " + events.getChangedFiles().size() + " changed files");
            for (final DirectoryObserver observer : observers) {
                observer.updateFileForChanges(events.getChangedFiles());
            }
        }
    }

    @Override
    public void startMonitor(final String projectDirPath) {
        final Path dir = Paths.get(projectDirPath);
        try {
            this.watcher = FileSystems.getDefault().newWatchService();
        } catch (final IOException e) {
            new RuntimeException();
        }
        this.keys = new HashMap<WatchKey, Path>();
        logService.log(LogService.LOG_DEBUG, "Scanning " + dir + " ...");
        try {
            registerAll(dir);
        } catch (final IOException e) {
            new RuntimeException();
        }
        logService.log(LogService.LOG_DEBUG, "Done.");
        this.trace = true; // enable trace after initial registration
        new Thread(this).start();
        logService.log(LogService.LOG_INFO, "Started to monitor project: " + dir);
    }

    private void register(final Path dir) throws IOException {
        final WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (trace) {
            final Path prev = keys.get(key);
            if (prev == null) {
                logService.log(LogService.LOG_DEBUG, "register: " + dir + "\n");
            } else {
                if (!dir.equals(prev)) {
                    logService.log(LogService.LOG_DEBUG, "register: " + dir + "\n");
                }
            }
        }
        keys.put(key, dir);
    }

    /**
     * Register the given directory, and all its sub-directories, with the
     * WatchService.
     */
    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs)
                        throws IOException {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        while (!stopRun) {
            WatchKey key;
            try {
                key = watcher.take();
            } catch (final InterruptedException x) {
                return;
            }
            final Path dir = keys.get(key);
            if (dir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }
            final FileEvents events = new FileEvents();

            // TODO do deplay
            try {
                // delay configurieren
                Thread.sleep(400);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }

            for (final WatchEvent<?> event : key.pollEvents()) {
                @SuppressWarnings("rawtypes")
                final WatchEvent.Kind kind = event.kind();
                if (kind == OVERFLOW) {
                    continue;
                }
                handleEvent(event, dir, events);
            }
            notifyListener(events);
            cleanUpKeys(key);
        }
    }

    private void handleEvent(final WatchEvent<?> event, final Path dir, final FileEvents events) {
        final WatchEvent.Kind kind = event.kind();
        final WatchEvent<Path> ev = cast(event);
        final Path name = ev.context();
        final Path child = dir.resolve(name);
        logService.log(LogService.LOG_DEBUG, kind.name() + ": " + child);

        final File file = child.toFile();
        if (file.getName().endsWith("java") || file.getName().endsWith("aj")) {
            if (kind == ENTRY_DELETE) {
                writtenCache.removeFile(file);
                events.addFileEvent(kind, file);
            } else if (changedCache.isNewFile(file) && writtenCache.isNewFile(file)) {
                writtenCache.removeFile(file);
                changedCache.addFile(file);
                events.addFileEvent(kind, file);
            } else {
                logService.log(LogService.LOG_DEBUG, "Found " + file + " in cache");
            }
        }

        registerSubFilesForNewDir(kind, child);
    }

    private void registerSubFilesForNewDir(@SuppressWarnings("rawtypes") final WatchEvent.Kind kind,
            final Path child) {
        if (kind == ENTRY_CREATE) {
            try {
                if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                    registerAll(child);
                }
            } catch (final IOException x) {
                logService.log(LogService.LOG_DEBUG, "Exception while register file: " + child);
            }
        }
    }

    private void cleanUpKeys(final WatchKey key) {
        final boolean valid = key.reset();
        if (!valid) {
            keys.remove(key);
        }
    }

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(final WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }

}
