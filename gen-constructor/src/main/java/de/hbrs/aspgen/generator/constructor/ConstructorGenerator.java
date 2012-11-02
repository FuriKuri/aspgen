package de.hbrs.aspgen.generator.constructor;

import java.util.Map;

import de.hbrs.aspgen.api.generator.ConstructorForClass;
import de.hbrs.aspgen.api.generator.ConstructorForClassGenerator;
import de.hbrs.aspgen.api.generator.ExtendClassWithConstructors;
import de.hbrs.aspgen.core.export.AbstractGeneratorBundle;

public class ConstructorGenerator extends AbstractGeneratorBundle implements ConstructorForClassGenerator {

    @Override
    public String getName() {
        return Constructor.class.getName();
    }

    @Override
    public void extendJavaClass(final ExtendClassWithConstructors builder,
            final Map<String, String> properties) {
        final ConstructorForClass constructorForClass = builder.appendNewConstructor("Cons");
        constructorForClass.addParameterForFields("$fieldtype$ $fieldname$");
        constructorForClass.addLine("System.out.println(\"Init\");");
        constructorForClass.addLineForeachField("System.out.println(\"$fieldname$\");");
    }

}
