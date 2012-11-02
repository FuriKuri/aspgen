package de.hbrs.aspgen.logging;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;

public class LogFileWriter implements LogListener {

    private final File logFile = new File("debug.log");

    @Override
    public void logged(final LogEntry logEntry) {
        final String logMessage = "\n" + new Date(logEntry.getTime())
                + " " + logEntry.getLevel() + " " + logEntry.getMessage();
        try {
            FileUtils.writeStringToFile(logFile, logMessage, true);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
