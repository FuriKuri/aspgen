package de.hbrs.aspgen.filemanager.monitor;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.nio.file.WatchEvent;
import java.util.LinkedList;
import java.util.List;

public class FileEvents {
    private final List<File> changedFiles = new LinkedList<>();
    private final List<File> deletedFiles = new LinkedList<>();
    private final List<File> createdFiles = new LinkedList<>();

    public void addFileEvent(final WatchEvent.Kind kind, final File file) {
        if (file.getName().endsWith(".java") || file.getName().endsWith(".aj")) {
            if (kind == ENTRY_DELETE) {
                deletedFiles.add(file);
            } else if (kind == ENTRY_MODIFY) {
                changedFiles.add(file);
            } else if (kind == ENTRY_CREATE) {
                createdFiles.add(file);
            }
        }
    }

    public List<File> getChangedFiles() {
        return changedFiles;
    }

    public List<File> getDeletedFiles() {
        return deletedFiles;
    }

    public List<File> getCreatedFiles() {
        return createdFiles;
    }
}
