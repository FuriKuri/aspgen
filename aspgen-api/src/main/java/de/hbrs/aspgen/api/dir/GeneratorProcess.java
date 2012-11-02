package de.hbrs.aspgen.api.dir;

import java.io.File;

public interface GeneratorProcess {
    void startGeneratorProcessForUpdatedFile(File file);

    void startGeneratorProcessForDeletedFile(File file);

    void startGeneratorProcessForCreatedFile(File file);
}
