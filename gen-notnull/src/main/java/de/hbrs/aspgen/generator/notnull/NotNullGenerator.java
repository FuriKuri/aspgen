package de.hbrs.aspgen.generator.notnull;

import java.util.Map;

import de.hbrs.aspgen.api.generator.AdviceForParameter;
import de.hbrs.aspgen.api.generator.AdviceForParameterGenerator;
import de.hbrs.aspgen.api.generator.ExtendParameterWithAdvices;
import de.hbrs.aspgen.core.export.AbstractGeneratorBundle;

public class NotNullGenerator extends AbstractGeneratorBundle implements AdviceForParameterGenerator {

    @Override
    public String getName() {
        return NotNull.class.getName();
    }

    @Override
    public void extendJavaClass(final ExtendParameterWithAdvices builder,
            final Map<String, String> properties) {
        final AdviceForParameter advice = builder.appendNewBeforeAdviceForParameter("NotNull");

        advice.addLine("if ($parametername$ == null) {");
        advice.addLine("    throw new RuntimeException(\"$parametername$ is null\");");
        advice.addLine("}");
    }

}
