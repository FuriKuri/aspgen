package de.hbrs.aspgen.api.dir;

import java.io.File;
import java.util.List;

public interface GeneratedFileRelationshipService {
    File getRootFile(final File file);
    List<File> getAssociatedFiles(final File file, final List<String> suffixNames);
}
