package de.hbrs.aspgen.api.dir;

public interface OberservableDirectoryMonitor {
    void startMonitor(final String projectDir);
    void stopMonitor();
    void register(DirectoryObserver observer);
    void remove(DirectoryObserver observer);
}
