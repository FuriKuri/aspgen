package de.hbrs.aspgen.api.generator;

import java.util.Map;

public interface MethodForFieldGenerator extends Generator {
    void extendJavaClass(ExtendFieldWithMethods builder,
            Map<String, String> properties);
}
