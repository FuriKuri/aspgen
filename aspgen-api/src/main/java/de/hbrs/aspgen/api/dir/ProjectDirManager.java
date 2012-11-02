package de.hbrs.aspgen.api.dir;

import java.io.File;

import de.hbrs.aspgen.api.file.GeneratedContent;

public interface ProjectDirManager {

    boolean fileExisit(String filename);

    void writeFile(String dir, GeneratedContent generatedContent);

    void updateFile(File aspectJFile, String newGeneratedContent);

    String readFileContent(File file);

    void deleteFile(File file);

}
