package de.hbrs.aspgen.core.file;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class StorageEntry {

    private final File javaFile;
    private final List<FileContentPair> contentPairs = new LinkedList<>();

    public StorageEntry(final File javaFile) {
        this.javaFile = javaFile;
    }

    public void addGeneratedFile(final File aspectJFile, final String fileContent) {
        getContentPairs().add(new FileContentPair(aspectJFile, fileContent));
    }

    public static final class FileContentPair {
        private final File file;
        private final String content;

        private FileContentPair(final File file, final String content) {
            this.file = file;
            this.content = content;
        }

        public File getFile() {
            return file;
        }

        public String getContent() {
            return content;
        }
    }

    public File getFile() {
        return javaFile;
    }

    public List<FileContentPair> getContentPairs() {
        return contentPairs;
    }
}
