package de.hbrs.aspgen.filemanager.project;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.dir.FileEvent;
import de.hbrs.aspgen.api.dir.ObservableProjectWriter;
import de.hbrs.aspgen.api.dir.ProjectDirManager;
import de.hbrs.aspgen.api.dir.ProjectWriterObserver;
import de.hbrs.aspgen.api.file.GeneratedContent;
import de.hbrs.aspgen.api.file.WrittenCache;
import de.hbrs.aspgen.filemanager.util.FileCache;

public class ProjectDirAccessCordinator implements ProjectDirManager, ObservableProjectWriter {
    private final Set<ProjectWriterObserver> observers = new HashSet<>();
    private final FileCache writtenCache;

    @Inject
    public ProjectDirAccessCordinator(@WrittenCache final FileCache writtenCache) {
        this.writtenCache = writtenCache;
    }

    private void notiyAboutEvents(final FileEvent event, final File file, final String content) {
        for (final ProjectWriterObserver observer : observers) {
            observer.notify(event, file, content);
        }
    }

    @Override
    public boolean fileExisit(final String filename) {
        return new File(filename).exists();
    }

    @Override
    public void writeFile(final String dir, final GeneratedContent generatedContent) {
        final File fileToWrite = new File(dir, generatedContent.getFilename());
        updateFile(fileToWrite, generatedContent.getContent());
    }

    @Override
    public void updateFile(final File file, final String content) {
        boolean updateFile;
        if (!file.exists()) {
            updateFile = true;
        } else {
            final String oldContent = readFileContent(file);
            updateFile = !oldContent.equals(content);
        }

        if (updateFile) {
            try {
                notiyAboutEvents(FileEvent.UPDATED, file, readFileContent(file));
                FileUtils.writeStringToFile(file, content);
                writtenCache.addFile(file);
            } catch (final IOException e) {
                throw new RuntimeException("Fehler beim Schreiben der Datei: " + file, e);
            }
        }
    }

    @Override
    public String readFileContent(final File file) {
        if (file.exists()) {
            try {
                return FileUtils.readFileToString(file);
            } catch (final IOException e) {
                throw new RuntimeException("Fehler beim Lesen der Datei: " + file, e);
            }
        } else {
            return null;
        }
    }

    @Override
    public void deleteFile(final File file) {
        notiyAboutEvents(FileEvent.DELETED, file, readFileContent(file));
        file.delete();
    }

    @Override
    public void register(final ProjectWriterObserver observer) {
        observers.add(observer);
    }

    @Override
    public void remove(final ProjectWriterObserver observer) {
        observers.remove(observer);
    }

}
