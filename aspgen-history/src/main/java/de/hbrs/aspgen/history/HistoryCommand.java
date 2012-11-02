package de.hbrs.aspgen.history;

import java.util.List;

import org.apache.felix.service.command.Descriptor;

import de.hbrs.aspgen.history.formatter.Formatter;
import de.hbrs.aspgen.history.formatter.RowFormatter;
import de.hbrs.aspgen.history.storage.HistoryEntry;
import de.hbrs.aspgen.history.storage.HistoryStorage;

public class HistoryCommand {
    private final HistoryStorage historyStorage;
    private final Formatter formatter = new RowFormatter(4, 9, 40, 10);

    public HistoryCommand(final HistoryStorage historyStorage) {
        this.historyStorage = historyStorage;
    }

    @Descriptor("Show history of changed files from generator system")
    public void history() {
        final List<HistoryEntry> entries = historyStorage.getEntries();
        printHeadline();
        for (final HistoryEntry historyEntry : entries) {
            printEntryLine(historyEntry);
        }
    }

    private void printEntryLine(final HistoryEntry historyEntry) {
        final String line = createLienForOutput(historyEntry);
        System.out.println(line);
    }

    private void printHeadline() {
        System.out.println(formatter.getSeparatorLine());
        System.out.println(formatter.formatToLine("id", "time", "file", "event"));
        System.out.println(formatter.getSeparatorLine());
    }

    private String createLienForOutput(final HistoryEntry historyEntry) {
        final String line = formatter.formatToLine(String.valueOf(historyEntry.getId()), historyEntry.getTime(), historyEntry.getFilename(), historyEntry.getEvent());
        return line;
    }

    @Descriptor("Show history detail of an entry")
    public void history(@Descriptor("The id of a history entry") final long id) {
        final HistoryEntry entry = historyStorage.getEntry(id);
        printHeadline();
        if (entry != null) {
            printEntryLine(entry);
            System.out.println();
            System.out.println(entry.getContent());
            System.out.println();
            System.out.println();
            System.out.println();
        }
    }

    @Descriptor("Show history details of a range of entries")
    public void history(@Descriptor("Start id of the range") final long fromId,
            @Descriptor("End id of the range") final long toId) {
        for (long id = fromId; id <= toId; id++) {
            final HistoryEntry entry = historyStorage.getEntry(id);
            if (entry != null) {
                printHeadline();
                printEntryLine(entry);
                System.out.println("============== CONTENT =================");
                System.out.println(entry.getContent());
                System.out.println();
                System.out.println();
                System.out.println();
            }
        }
    }
}
