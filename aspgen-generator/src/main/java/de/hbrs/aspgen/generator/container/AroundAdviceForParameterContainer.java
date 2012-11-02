package de.hbrs.aspgen.generator.container;

import de.hbrs.aspgen.api.ast.JavaMethod;

public class AroundAdviceForParameterContainer extends AdviceForParameterContainer {
    @Override
    protected String getAdviceType(final JavaMethod javaMethod) {
        return javaMethod.getType() + " around";
    }

    @Override
    protected String getAdditionalAdviceDeclartaation(final JavaMethod javaMethod) {
        return "";
    }
}
