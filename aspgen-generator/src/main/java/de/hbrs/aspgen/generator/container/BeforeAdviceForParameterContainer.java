package de.hbrs.aspgen.generator.container;

import de.hbrs.aspgen.api.ast.JavaMethod;

public class BeforeAdviceForParameterContainer extends AdviceForParameterContainer {
    @Override
    protected String getAdviceType(final JavaMethod javaMethod) {
        return "before";
    }

    @Override
    protected String getAdditionalAdviceDeclartaation(final JavaMethod javaMethod) {
        return "";
    }
}
