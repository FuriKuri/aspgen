package de.hbrs.aspgen.generator.errorhandling;

import java.util.Map;

import de.hbrs.aspgen.api.generator.AdviceForMethod;
import de.hbrs.aspgen.api.generator.AdviceForMethodGenerator;
import de.hbrs.aspgen.api.generator.ExtendMethodWithAdvices;
import de.hbrs.aspgen.core.export.AbstractGeneratorBundle;

public class HandleExceptionGenerator extends AbstractGeneratorBundle implements AdviceForMethodGenerator {

    @Override
    public String getName() {
        return HandleException.class.getName();
    }

    @Override
    public void extendJavaClass(final ExtendMethodWithAdvices builder,
            final Map<String, String> properties) {
        String[] expetions;
        if (properties.get("exception") != null) {
            expetions = properties.get("exception").replace("{", "")
                    .replace("}", "")
                    .replace(".class", "")
                    .replace(" ", "").split(",");
        } else {
            expetions = new String[]{"Exception"};
        }

        final AdviceForMethod adviceForMethod = builder.appendNewAroundAdvice("Catch");
        adviceForMethod.addLine("try {");
        adviceForMethod.addLine("    $returnproceedcall$");
        for (final String exceptionToCatch : expetions) {
            adviceForMethod.addLine("} catch (" + exceptionToCatch + " e) {");
            adviceForMethod.addLine("    throw new RuntimeException(e);");
        }
        adviceForMethod.addLine("}");
        for (final String exceptionToCatch : expetions) {
            adviceForMethod.addSofteningExcption(exceptionToCatch);
        }
    }

}
