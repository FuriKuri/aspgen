package de.hbrs.aspgen.generator.equalshashcode;

import java.lang.reflect.Array;

public class HashCodeUtil {
    private HashCodeUtil() {}

    public static final int SEED = 23;
    private static final int ODD_PRIME_NUMBER = 37;

    public static int hash(final int seed, final boolean value) {
        return firstTerm(seed) + (value ? 1 : 0);
    }

    public static int hash(final int seed, final char value) {
        return firstTerm(seed) + value;
    }

    public static int hash(final int seed , final int value) {
        return firstTerm(seed) + value;
    }

    public static int hash(final int seed, final long value) {
        return firstTerm(seed) + (int) (value ^ (value >>> 32));
    }

    public static int hash(final int seed, final float value) {
        return hash(seed, Float.floatToIntBits(value));
    }

    public static int hash(final int seed, final double value) {
        return hash(seed, Double.doubleToLongBits(value));
    }

    public static int hash(final int aSeed, final Object obj) {
        int result = aSeed;
        if (obj == null) {
            result = hash(result, 0);
        } else if (!obj.getClass().isArray()) {
            result = hash(result, obj.hashCode());
        } else {
            final int length = Array.getLength(obj);
            for (int idx = 0; idx < length; ++idx) {
                final Object item = Array.get(obj, idx);
                result = hash(result, item);
            }
        }
        return result;
    }

    private static int firstTerm(final int seed){
        return ODD_PRIME_NUMBER * seed;
    }
}
