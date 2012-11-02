package de.hbrs.aspgen.core.file;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import de.hbrs.aspgen.core.file.StorageEntry.FileContentPair;

public class DeletedFileStorage {
    private final Queue<StorageEntry> storageEntries = new LinkedList<>();
    private final int size;

    public DeletedFileStorage(final int size) {
        this.size = size;
    }

    public void add(final StorageEntry entry) {
        if (storageEntries.size() == size) {
            storageEntries.remove();
        }
        storageEntries.add(entry);
    }

    public boolean containsFileName(final File file) {
        for (final StorageEntry entry : storageEntries) {
            if (entry.getFile().getName().equals(file.getName())) {
                return true;
            }
        }
        return false;
    }

    public List<FileContentPair> getFileContentPairsForFileName(final File file) {
        for (final StorageEntry entry : storageEntries) {
            if (entry.getFile().getName().equals(file.getName())) {
                return entry.getContentPairs();
            }
        }
        return new LinkedList<>();
    }

    public int size() {
        return storageEntries.size();
    }

    public void deleteEntry(final File file) {
        StorageEntry entryToDelete = null;
        for (final StorageEntry entry : storageEntries) {
            if (entry.getFile().getName().equals(file.getName())) {
                entryToDelete = entry;
            }
        }
        if (entryToDelete != null) {
            storageEntries.remove(entryToDelete);
        }
    }

    public File getFileForNeededAnnotations(final List<String> names) {
        for (final StorageEntry entry : storageEntries) {
            boolean containsAllNames = true;
            for (final FileContentPair pair : entry.getContentPairs()) {
                final String annoNameFromFile = pair.getFile().getName().split("_")[1].replace(".aj", "");
                if (!names.contains(annoNameFromFile)) {
                    containsAllNames = false;
                }
            }
            if (containsAllNames) {
                return entry.getFile();
            }
        }
        return null;
    }
}
