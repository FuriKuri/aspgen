package de.hbrs.aspgen.filemanager.monitor;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hbrs.aspgen.api.dir.DirectoryObserver;
import de.hbrs.aspgen.filemanager.helper.FileWriter;
import de.hbrs.aspgen.filemanager.util.LastChangedFileCache;
import de.hbrs.aspgen.filemanager.util.LastWrittenFileCache;
import de.hbrs.aspgen.testhelper.DummyLogService;

public class ProjectMonitorTest {
    private static final int CACHE_SIZE = 5;
    private ProjectMonitor projectMonitor;
    private SaveChangesListener observer;

    @Before
    public void initProjectWatcherAndObserver() {
        projectMonitor = new ProjectMonitor(new LastChangedFileCache(CACHE_SIZE),
                new LastWrittenFileCache(CACHE_SIZE),
                new DummyLogService());
        observer = new SaveChangesListener();
        projectMonitor.register(observer);
    }

    @After
    public void stopMonitor() {
        projectMonitor.stopMonitor();
    }

    @After
    public void removeObserver() {
        projectMonitor.remove(observer);
    }

    @Test
    public void detectNewFiles() {
        projectMonitor.startMonitor("src/test/resources/dummydir");
        final File file = new File("src/test/resources/dummydir/newfiles/SimpleJavaFile.java");
        FileWriter.writeStringToFile(new Date().toString(), file);
        sleep(1);
        assertEquals(1 , observer.files.size());
        assertEquals(file , observer.files.get(0));
    }

    private void sleep(final int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void detectChangedFile() {
        final File file = new File("src/test/resources/dummydir/newfiles/SimpleJavaFile.java");
        FileWriter.writeStringToFile(new Date().toString(), file);
        projectMonitor.startMonitor("src/test/resources/dummydir");
        FileWriter.writeStringToFile(new Date().toString(), file);
        sleep(1);
        assertEquals(1 , observer.files.size());
        assertEquals(file , observer.files.get(0));
    }

    @Test
    public void detectNoChangedFile() {
        final File file = new File("src/test/resources/dummydir/newfiles/SimpleJavaFile.java");
        FileWriter.writeStringToFile(new Date().toString(), file);
        projectMonitor.startMonitor("src/test/resources/dummydir");
        sleep(1);
        assertEquals(0 , observer.files.size());
    }

    private static class SaveChangesListener implements DirectoryObserver {
        private List<File> files = new LinkedList<>();

        @Override
        public void updateFileForChanges(final List<File> changedFiles) {
            files = changedFiles;
        }

        @Override
        public void updateFileForCreations(final List<File> files) {
            this.files = files;
        }

        @Override
        public void updateFileForDeletions(final List<File> files) {
            this.files = files;

        }
    }
}
