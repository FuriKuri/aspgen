package de.hbrs.aspgen.api.dir;

public interface ObservableProjectWriter {
    void register(ProjectWriterObserver observer);
    void remove(ProjectWriterObserver observer);
}
