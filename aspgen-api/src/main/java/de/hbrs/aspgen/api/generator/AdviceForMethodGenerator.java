package de.hbrs.aspgen.api.generator;

import java.util.Map;

public interface AdviceForMethodGenerator extends Generator {
    void extendJavaClass(ExtendMethodWithAdvices builder,
            Map<String, String> properties);
}
