package de.hbrs.aspgen.history.formatter;

public class RowFormatter implements Formatter {
    private final int[] columnWidths;

    public RowFormatter(final int... columnWidths) {
        this.columnWidths = columnWidths;
    }

    @Override
    public String formatToLine(final String... columnValues) {
        if (columnValues.length != columnWidths.length) {
            throw new IllegalArgumentException("Number of columns do not match to the number of " +
            		"defined columns in construcutor");
        }
        final StringBuilder builder = new StringBuilder();
        builder.append("|");
        for (int i = 0; i < columnValues.length; i++) {
            builder.append(fillWithSpaces(columnValues[i], columnWidths[i]));
            builder.append("|");
        }
        return builder.toString();
    }

    private Object fillWithSpaces(final String value, final int width) {
        if (value.length() > width) {
            final int spacesToMuch = value.length() - width;
            return value.substring(0, value.length() - spacesToMuch);
        } else {
            final int spacesLeft = width - value.length();
            final StringBuilder spaces = new StringBuilder();
            for (int i = 0; i < spacesLeft; i++) {
                spaces.append(" ");
            }
            return spaces.toString() + value;
        }
    }

    public String getSeparatorLine() {
        final StringBuilder builder = new StringBuilder();
        builder.append("|");

        for (int i = 0; i < columnWidths.length; i++) {
            builder.append(getSeparatorLine(columnWidths[i]));
            builder.append("|");
        }
        return builder.toString();
    }

    private Object getSeparatorLine(final int width) {
        final StringBuilder separator = new StringBuilder();
        for (int i = 0; i < width; i++) {
            separator.append("-");
        }
        return separator.toString();
    }
}
