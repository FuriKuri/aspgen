package de.hbrs.aspgen.filemanager.monitor;

import java.io.File;
import java.util.List;

public interface FileChangedListener {

    void updateChangedFiles(List<File> changedFiles);

}
