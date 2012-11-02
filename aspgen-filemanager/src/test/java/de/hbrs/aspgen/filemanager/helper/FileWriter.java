package de.hbrs.aspgen.filemanager.helper;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public final class FileWriter {
    private FileWriter() { }

    public static void writeStringToFile(final String content, final File file) {
        try {
            FileUtils.writeStringToFile(file, content);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
