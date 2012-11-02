package de.hbrs.aspgen.filemanager.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hbrs.aspgen.filemanager.helper.FileWriter;

public class LastWrittenFileCacheTest {
    private LastWrittenFileCache cache;
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
        cache = new LastWrittenFileCache(3);
    }

    @Test
    public void addNoFile() {
        final File file = new File("src/test/resources/dummydir/newfiles/Firstfile");
        FileWriter.writeStringToFile("Content", file);
        assertTrue(cache.isNewFile(file));
    }

    @Test
    public void addFile() {
        final File file = new File("src/test/resources/dummydir/newfiles/Firstfile");
        FileWriter.writeStringToFile("Content", file);
        cache.addFile(file);
        assertFalse(cache.isNewFile(file));
    }

    @Test
    public void addFileAndChangeContent() {
        final File file = new File("src/test/resources/dummydir/newfiles/Firstfile");
        FileWriter.writeStringToFile("Content", file);
        cache.addFile(file);
        FileWriter.writeStringToFile("New Content", file);
        assertTrue(cache.isNewFile(file));
    }

    @Test
    public void addNewContentFile() {
        final File file = new File("src/test/resources/dummydir/newfiles/Firstfile");
        FileWriter.writeStringToFile("Content", file);
        cache.addFile(file);
        FileWriter.writeStringToFile("New Content", file);
        cache.addFile(file);
        assertFalse(cache.isNewFile(file));
    }

    @Test
    public void addMoreThanCacheSize() {
        final File file = new File("src/test/resources/dummydir/newfiles/Firstfile");
        FileWriter.writeStringToFile("Content", file);
        cache.addFile(file);

        final File file2 = new File("src/test/resources/dummydir/newfiles/Firstfile2");
        FileWriter.writeStringToFile("Content", file2);
        cache.addFile(file2);

        final File file3 = new File("src/test/resources/dummydir/newfiles/Firstfile3");
        FileWriter.writeStringToFile("Content", file3);
        cache.addFile(file3);

        final File file4 = new File("src/test/resources/dummydir/newfiles/Firstfile4");
        FileWriter.writeStringToFile("Content", file4);
        cache.addFile(file4);

        assertTrue(cache.isNewFile(file));
    }
}
