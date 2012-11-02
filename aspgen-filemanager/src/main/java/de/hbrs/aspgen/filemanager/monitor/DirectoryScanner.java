package de.hbrs.aspgen.filemanager.monitor;

import java.io.File;
import java.util.Date;
import java.util.List;

public interface DirectoryScanner {

    List<File> getChangedFilesSinceTime(File dir, Date date);

}
