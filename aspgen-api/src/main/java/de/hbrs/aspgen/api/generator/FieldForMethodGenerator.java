package de.hbrs.aspgen.api.generator;

import java.util.Map;

public interface FieldForMethodGenerator extends Generator {
    void extendJavaClass(ExtendMethodWithFields builder,
            Map<String, String> properties);
}
