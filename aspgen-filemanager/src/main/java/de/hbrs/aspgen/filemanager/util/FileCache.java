package de.hbrs.aspgen.filemanager.util;

import java.io.File;


public interface FileCache {
    boolean isNewFile(File file);
    void addFile(File file);
    void removeFile(File file);
}
