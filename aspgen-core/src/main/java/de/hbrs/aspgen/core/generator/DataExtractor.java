package de.hbrs.aspgen.core.generator;

import de.hbrs.aspgen.api.ast.JavaClass;

public interface DataExtractor<T> {
    T extractDatasFromClass(final JavaClass javaClass);
}
