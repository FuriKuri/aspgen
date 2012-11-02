package de.hbrs.aspgen.api.dir;

import java.io.File;
import java.util.List;

public interface DirectoryObserver {
    void updateFileForChanges(List<File> files);
    void updateFileForCreations(List<File> files);
    void updateFileForDeletions(List<File> files);
}
