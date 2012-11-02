package de.hbrs.aspgen.history.formatter;

public interface Formatter {
    String formatToLine(final String... columnValues);
    String getSeparatorLine();
}
