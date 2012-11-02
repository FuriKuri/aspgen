package de.hbrs.aspgen.api.generator;

import java.util.Map;

public interface FieldForClassGenerator extends Generator {
    void extendJavaClass(ExtendClassWithFields fieldBuilder,
            Map<String, String> properties);
}
