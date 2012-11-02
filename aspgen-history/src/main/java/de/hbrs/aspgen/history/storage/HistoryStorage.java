package de.hbrs.aspgen.history.storage;

import java.util.List;

public interface HistoryStorage {

    List<HistoryEntry> getEntries();

    HistoryEntry getEntry(long id);

}
