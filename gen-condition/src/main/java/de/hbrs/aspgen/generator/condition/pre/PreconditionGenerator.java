package de.hbrs.aspgen.generator.condition.pre;

import java.util.Map;

import de.hbrs.aspgen.api.generator.AdviceForMethod;
import de.hbrs.aspgen.api.generator.AdviceForMethodGenerator;
import de.hbrs.aspgen.api.generator.ExtendMethodWithAdvices;

public class PreconditionGenerator implements AdviceForMethodGenerator {

    @Override
    public String getName() {
        return Precondition.class.getName();
    }

    @Override
    public void extendJavaClass(final ExtendMethodWithAdvices builder,
            final Map<String, String> properties) {
        final AdviceForMethod adviceForMethod = builder.appendNewBeforeAdvice("Pre");
        adviceForMethod.addLineForeachParameter(
                "if ($parametername$ == null) {",
                "    throw new RuntimeException();",
                "}");
    }

}
