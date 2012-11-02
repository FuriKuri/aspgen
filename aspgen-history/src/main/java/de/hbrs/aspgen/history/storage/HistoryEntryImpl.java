package de.hbrs.aspgen.history.storage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hbrs.aspgen.api.dir.FileEvent;

public class HistoryEntryImpl implements HistoryEntry {
    private static final SimpleDateFormat SIMPLE_TIME_FORAMTTER = new SimpleDateFormat("HH:mm");
    private final FileEvent event;
    private final File file;
    private final String content;
    private final Date entryCreationDate;
    private final long id;
    private static long idCounter = 1;

    public HistoryEntryImpl(final FileEvent event, final File file, final String content) {
        this.event = event;
        this.file = file;
        this.content = content;
        entryCreationDate = new Date();
        id = idCounter++;
    }

    @Override
    public String toString() {
        return id + " - " + entryCreationDate +  " - " + event + " - " +file.getName();
    }

    public String getTime() {
        return SIMPLE_TIME_FORAMTTER.format(entryCreationDate);
    }

    public String getEvent() {
        return event.toString();
    }

    public String getFilename() {
        return file.getName();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getContent() {
        return content;
    }


}
