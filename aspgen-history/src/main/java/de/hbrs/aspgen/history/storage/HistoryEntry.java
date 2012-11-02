package de.hbrs.aspgen.history.storage;

public interface HistoryEntry {

    long getId();

    String getContent();
    String getTime();
    String getEvent();
    String getFilename();
}
