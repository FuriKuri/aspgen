package de.hbrs.aspgen.filemanager.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hbrs.aspgen.filemanager.helper.FileWriter;

public class LastChangedFileCacheTest {
    private LastChangedFileCache cache;

    @After
    public void deleteNewFilesFolder() throws IOException {
        FileUtils.cleanDirectory(new File("src/test/resources/dummydir/newfiles"));
    }

    @Before
    public void prepareTestFolder() {
        final File file = new File("src/test/resources/dummydir/newfiles");
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Before
    public void init() {
        cache = new LastChangedFileCache(3);
    }

    @Test
    public void cacheFileWithAndRequestSameFileModTime() {
        final File file = new File("src/test/resources/dummydir/newfiles/Firstfile");
        FileWriter.writeStringToFile("Content", file);
        cache.addFile(file);
        assertFalse(cache.isNewFile(file));
    }

    @Test
    public void cacheFileWithAndRequestNewerFileModTime() {
        final File file = new File("src/test/resources/dummydir/newfiles/Firstfile");
        FileWriter.writeStringToFile("Content", file);
        cache.addFile(file);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
        FileWriter.writeStringToFile("Content2", file);
        assertTrue(cache.isNewFile(file));
    }

    @Test
    public void cacheFileWithAndRequestOlderFileModTime() {
        final File file = new File("src/test/resources/dummydir/newfiles/Firstfile");
        FileWriter.writeStringToFile("Content", file);
        cache.addFile(file);
        file.setLastModified(1L);
        assertFalse(cache.isNewFile(file));
    }

    @Test
    public void repalceFile() {
        final File file = new File("src/test/resources/dummydir/newfiles/Firstfile");
        FileWriter.writeStringToFile("Content", file);
        cache.addFile(file);

        final File sameFile = new File("src/test/resources/dummydir/newfiles/Firstfile");
        FileWriter.writeStringToFile("Content", sameFile);
        cache.addFile(sameFile);

        assertFalse(cache.isNewFile(sameFile));
    }

    @Test
    public void cacheMaxOneFile() {
        final LastChangedFileCache cacheWithSizeOne = new LastChangedFileCache(1);

        final File file1 = new File("src/test/resources/dummydir/newfiles/file1");
        FileWriter.writeStringToFile("Content", file1);
        cacheWithSizeOne.addFile(file1);

        final File file2 = new File("src/test/resources/dummydir/newfiles/file2");
        FileWriter.writeStringToFile("Content", file2);
        cacheWithSizeOne.addFile(file2);

        assertTrue(cacheWithSizeOne.isNewFile(file1));
        assertFalse(cacheWithSizeOne.isNewFile(file2));
    }

    @Test
    public void cacheMaxThreeFiles() {
        final File file1 = new File("src/test/resources/dummydir/newfiles/file1");
        FileWriter.writeStringToFile("Content", file1);
        cache.addFile(file1);

        final File file2 = new File("src/test/resources/dummydir/newfiles/file2");
        FileWriter.writeStringToFile("Content", file2);
        cache.addFile(file2);

        final File file3 = new File("src/test/resources/dummydir/newfiles/file3");
        FileWriter.writeStringToFile("Content", file3);
        cache.addFile(file3);

        final File file4 = new File("src/test/resources/dummydir/newfiles/file4");
        FileWriter.writeStringToFile("Content", file4);
        cache.addFile(file4);

        assertTrue(cache.isNewFile(file1));
        assertFalse(cache.isNewFile(file2));
        assertFalse(cache.isNewFile(file3));
        assertFalse(cache.isNewFile(file4));
    }
}
