package de.hbrs.aspgen.history.formatter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RowFormatterTest {
    private RowFormatter formatter;

    @Test
    public void createWithWidthTwoAndFive() {
        formatter = new RowFormatter(2, 5);
        final String result = formatter.formatToLine("id", "value");
        assertEquals("|id|value|", result);
    }

    @Test
    public void createRowWidthFiveAndSeven() {
        formatter = new RowFormatter(5, 7);
        final String result = formatter.formatToLine("id", "wert");
        assertEquals("|   id|   wert|", result);
    }

    @Test
    public void cutToLongString() {
        formatter = new RowFormatter(2, 5);
        final String result = formatter.formatToLine("ident", "wert");
        assertEquals("|id| wert|", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toMuchColumnThanDefined() {
        formatter = new RowFormatter(2, 5);
        formatter.formatToLine("ident", "wert", "asd");
    }

    @Test
    public void printSeparator() {
        formatter = new RowFormatter(2, 5);
        final String result = formatter.getSeparatorLine();
        assertEquals("|--|-----|", result);
    }
}
