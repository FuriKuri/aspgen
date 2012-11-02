package de.hbrs.aspgen.generator.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodCache<T> {
    Map<List<Object>, T> cache = new HashMap<>();

    public T getCachedValue(final List<Object> parameters) {
        return cache.get(parameters);
    }

    public void putNewValue(final List<Object> parameters, final T value) {
        cache.put(parameters, value);
    }
}
