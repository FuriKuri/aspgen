package de.hbrs.aspgen.generator.equalshashcode;

import java.util.Arrays;

public class EqualsUtil {

    private EqualsUtil() {}

    public static boolean isEquals(final Object lhs, final Object rhs) {
        if (!lhs.getClass().isArray()) {
            return lhs.equals(rhs);
        } else if (lhs instanceof int[]) {
            return Arrays.equals((int []) lhs, (int []) rhs);
        } else if (lhs instanceof long[]) {
            return Arrays.equals((long []) lhs, (long []) rhs);
        } else if (lhs instanceof byte[]) {
            return Arrays.equals((byte []) lhs, (byte []) rhs);
        } else if (lhs instanceof short[]) {
            return Arrays.equals((short []) lhs, (short []) rhs);
        } else if (lhs instanceof char[]) {
            return Arrays.equals((char []) lhs, (char []) rhs);
        } else if (lhs instanceof double[]) {
            return Arrays.equals((double []) lhs, (double []) rhs);
        } else if (lhs instanceof float[]) {
            return Arrays.equals((float []) lhs, (float []) rhs);
        } else if (lhs instanceof boolean[]) {
            return Arrays.equals((boolean []) lhs, (boolean []) rhs);
        } else {
            return Arrays.equals((Object []) lhs, (Object []) rhs);
        }
    }
}
