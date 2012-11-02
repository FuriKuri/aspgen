package de.hbrs.aspgen.generator.time;

import java.util.Map;

import de.hbrs.aspgen.api.generator.AdviceForMethod;
import de.hbrs.aspgen.api.generator.AdviceForMethodGenerator;
import de.hbrs.aspgen.api.generator.ExtendClassWithFields;
import de.hbrs.aspgen.api.generator.ExtendMethodWithAdvices;
import de.hbrs.aspgen.api.generator.FieldForClass;
import de.hbrs.aspgen.api.generator.FieldForClassGenerator;
import de.hbrs.aspgen.core.export.AbstractGeneratorBundle;

public class TimeGenerator extends AbstractGeneratorBundle implements AdviceForMethodGenerator, FieldForClassGenerator {

    @Override
    public String getName() {
        return LogTime.class.getName();
    }

    @Override
    public void extendJavaClass(final ExtendMethodWithAdvices builder,
            final Map<String, String> properties) {
        final AdviceForMethod adviceForMethod = builder.appendNewAroundAdvice("Time");
        adviceForMethod.addLine("long currentTime = System.currentTimeMillis();");
        adviceForMethod.addLine("$storeproceedcall$");
        adviceForMethod.addLine("logger.debug(System.currentTimeMillis() - currentTime);");
        adviceForMethod.addLine("$returnstoredproceedcall$");
    }

    @Override
    public void extendJavaClass(final ExtendClassWithFields fieldBuilder,
            final Map<String, String> properties) {
        final FieldForClass fieldForClass = fieldBuilder.appendNewField("Logger");
        fieldForClass.setContent("private String logger = \"Logger.get($classname$.class)\";");
    }

}
