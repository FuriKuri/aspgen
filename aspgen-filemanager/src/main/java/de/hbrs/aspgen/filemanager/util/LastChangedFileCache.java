package de.hbrs.aspgen.filemanager.util;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class LastChangedFileCache implements FileCache {
    private final Queue<File> cache = new LinkedList<>();
    private final Map<File, Long> fileSavedModTimeMap = new HashMap<>();
    private final int size;

    public LastChangedFileCache(final int size) {
        this.size = size;
    }

    // TODO threadsafe???
    @Override
    public void addFile(final File file) {
        if (cache.size() == size) {
            final File firstFileInQueue = cache.remove();
            fileSavedModTimeMap.remove(firstFileInQueue);
        }

        if (cache.contains(file) && isNewFile(file)) {
            cache.remove(file);
            fileSavedModTimeMap.remove(file);
        }
        cache.add(file);
        fileSavedModTimeMap.put(file, file.lastModified());
    }

    @Override
    public boolean isNewFile(final File file) {
        if (cache.contains(file)) {
            final long modTimeFromCachedFile = fileSavedModTimeMap.get(file);
            final long acutalFileTime = file.lastModified();
            return modTimeFromCachedFile < acutalFileTime;
        } else {
            return true;
        }
    }

    @Override
    public void removeFile(final File file) {
        fileSavedModTimeMap.remove(file);
        cache.remove(file);
    }
}
