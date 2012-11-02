package de.hbrs.aspgen.api.dir;

import java.io.File;

public interface ProjectWriterObserver {
    void notify(FileEvent event, File file, String content);
}
