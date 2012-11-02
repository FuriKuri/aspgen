package de.hbrs.aspgen.api.generator;

import java.util.Map;

public interface AdviceForFieldGenerator extends Generator {
    void extendJavaClass(ExtendFieldWithAdvices builder,
            Map<String, String> properties);
}
