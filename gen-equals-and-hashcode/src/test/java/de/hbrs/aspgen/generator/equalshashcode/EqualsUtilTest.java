package de.hbrs.aspgen.generator.equalshashcode;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EqualsUtilTest {

    @Test
    public void equalStrings() {
        final String value = "Hello World";
        final String value2 = "Hello World";

        assertTrue(EqualsUtil.isEquals(value, value2));
    }

    @Test
    public void nonEqualStrings() {
        final String value = "Hello World";
        final String value2 = "Goodbye";

        assertFalse(EqualsUtil.isEquals(value, value2));
    }

    @Test
    public void equalPrimitivs() {
        final int a = 1;
        final int b = 1;

        assertTrue(EqualsUtil.isEquals(a, b));
    }

    @Test
    public void nonEqualPrimitivs() {
        final int a = 1;
        final int b = 2;

        assertFalse(EqualsUtil.isEquals(a, b));
    }

    @Test
    public void equalArray() {
        final int[] a = {1, 2, 3};
        final int[] b = {1, 2, 3};

        assertTrue(EqualsUtil.isEquals(a, b));
    }

    @Test
    public void nonEqualArray() {
        final int[] a = {1, 2, 3};
        final int[] b = {2, 2, 3};

        assertFalse(EqualsUtil.isEquals(a, b));
    }

    @Test
    public void equalStringArray() {
        final String[] a = {"1", "2", "3"};
        final String[] b = {"1", "2", "3"};

        assertTrue(EqualsUtil.isEquals(a, b));
    }

    @Test
    public void nonEqualStringArray() {
        final String[] a = {"1", "2", "3"};
        final String[] b = {"2", "2", "3"};

        assertFalse(EqualsUtil.isEquals(a, b));
    }
}
