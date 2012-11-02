package de.hbrs.aspgen.api.generator;

import java.util.Map;

public interface ConstructorForClassGenerator extends Generator {

    void extendJavaClass(ExtendClassWithConstructors builder,
            Map<String, String> properties);

}
