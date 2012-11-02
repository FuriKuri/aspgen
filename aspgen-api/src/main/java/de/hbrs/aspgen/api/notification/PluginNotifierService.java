package de.hbrs.aspgen.api.notification;

import java.io.File;

public interface PluginNotifierService {
    void updateFolder(String path);

    void updateFolder(File path);
}
