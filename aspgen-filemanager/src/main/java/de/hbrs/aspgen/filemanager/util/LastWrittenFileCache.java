package de.hbrs.aspgen.filemanager.util;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.io.FileUtils;

public class LastWrittenFileCache implements FileCache {
    private final Queue<File> lastAddedFiles = new LinkedList<>();
    private final int size;
    private final MessageDigest md;
    private final Map<File, String> filesWithHashedContent = new HashMap<>();

    public LastWrittenFileCache(final int size) {
        this.size = size;
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while init MessageDigest", e);
        }
    }

    @Override
    public boolean isNewFile(final File file) {
        if (filesWithHashedContent.containsKey(file)) {
            final String fileContent = readFileContent(file);
            if (fileContent == null) {
                removeFile(file);
                return true;
            }
            final String hashValue = new String(md.digest(fileContent.getBytes()));
            final String storedHashValue = filesWithHashedContent.get(file);
            return !storedHashValue.equals(hashValue);
        } else {
            return true;
        }
    }

    @Override
    public void addFile(final File file) {
        final String fileContent = readFileContent(file);
        if (fileContent == null) {
            return;
        }
        if (filesWithHashedContent.size() == size) {
            final File firstFileInQueue = lastAddedFiles.remove();
            filesWithHashedContent.remove(firstFileInQueue);
        }
        if (lastAddedFiles.contains(file)) {
            lastAddedFiles.remove(file);
        }
        lastAddedFiles.add(file);
        filesWithHashedContent.put(file, new String(md.digest(fileContent.getBytes())));
    }

    private String readFileContent(final File file) {
        String fileContent;
        try {
            fileContent = FileUtils.readFileToString(file);
        } catch (final IOException e) {
            return null;
        }
        return fileContent;
    }

    @Override
    public void removeFile(final File file) {
        filesWithHashedContent.remove(file);
        lastAddedFiles.remove(file);
    }

}
