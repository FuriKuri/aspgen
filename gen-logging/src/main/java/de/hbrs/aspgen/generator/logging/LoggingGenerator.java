package de.hbrs.aspgen.generator.logging;

import java.util.Map;

import de.hbrs.aspgen.api.generator.AdviceForMethod;
import de.hbrs.aspgen.api.generator.AdviceForMethodGenerator;
import de.hbrs.aspgen.api.generator.ExtendClassWithFields;
import de.hbrs.aspgen.api.generator.ExtendMethodWithAdvices;
import de.hbrs.aspgen.api.generator.FieldForClass;
import de.hbrs.aspgen.api.generator.FieldForClassGenerator;
import de.hbrs.aspgen.core.export.AbstractGeneratorBundle;

public class LoggingGenerator extends AbstractGeneratorBundle implements FieldForClassGenerator, AdviceForMethodGenerator {

    @Override
    public String getName() {
        return Logging.class.getName();
    }

    @Override
    public void extendJavaClass(final ExtendMethodWithAdvices builder,
            final Map<String, String> properties) {
        final AdviceForMethod adviceForMethod = builder.appendNewBeforeAdvice("Before");
        adviceForMethod.addLineForeachParameter("System.out.println($parametername$);");

        final AdviceForMethod adviceForMethod2 = builder.appendNewAfterAdvice("After");
        adviceForMethod2.addLineForeachParameter("System.out.println($parametername$);");
    }

    @Override
    public void extendJavaClass(final ExtendClassWithFields fieldBuilder,
            final Map<String, String> properties) {
        final FieldForClass fieldForClass = fieldBuilder.appendNewField("Logger");
        fieldForClass.setContent("private String logger = \"Logger.get($classname$.class)\";");

    }

}
