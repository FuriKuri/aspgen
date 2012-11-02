package de.hbrs.aspgen.api.generator;

import java.util.Map;

public interface AdviceForParameterGenerator extends Generator {
    void extendJavaClass(ExtendParameterWithAdvices builder,
            Map<String, String> properties);
}
