package de.hbrs.aspgen.api.generator;

import java.util.Map;

public interface MethodForClassGenerator extends Generator {

    void extendJavaClass(ExtendClassWithMethods methodBuilder,
            Map<String, String> properties);

}
