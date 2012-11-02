package de.hbrs.aspgen.history.storage;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import de.hbrs.aspgen.api.dir.FileEvent;
import de.hbrs.aspgen.api.dir.ProjectWriterObserver;

public class HistoryStorageQueue implements ProjectWriterObserver, HistoryStorage {

    private final LinkedList<HistoryEntry> entries = new LinkedList<>();

    @Override
    public void notify(final FileEvent event, final File file, final String content) {
        final HistoryEntry entry = new HistoryEntryImpl(event, file, content);
        entries.add(entry);
    }

    @Override
    public List<HistoryEntry> getEntries() {
        return entries;
    }

    @Override
    public HistoryEntry getEntry(final long id) {
        for (final HistoryEntry entry : entries) {
            if (entry.getId() == id) {
                return entry;
            }
        }
        return null;
    }

}
