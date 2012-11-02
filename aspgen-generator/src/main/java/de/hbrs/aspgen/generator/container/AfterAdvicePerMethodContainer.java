package de.hbrs.aspgen.generator.container;

import de.hbrs.aspgen.api.ast.JavaMethod;


public class AfterAdvicePerMethodContainer extends AdvicePerMethodContainer {
    @Override
    protected String getAdviceType(final JavaMethod javaMethod) {
        return "after";
    }

    @Override
    protected String getAdditionalAdviceDeclartaation(final JavaMethod javaMethod) {
        if (javaMethod.getType().equals("void")) {
            return "";
        } else {
            final String returnInfo = javaMethod.getType() + " returnValue";
            return "returning(" + returnInfo + ") ";
        }
    }
}
