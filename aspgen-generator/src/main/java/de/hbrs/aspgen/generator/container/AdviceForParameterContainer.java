package de.hbrs.aspgen.generator.container;

import java.util.Map;

import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.generator.AdviceForParameter;
import de.hbrs.aspgen.generator.builder.ContentPart;

public abstract class AdviceForParameterContainer extends AdviceContainer implements AdviceForParameter {
    protected JavaParameter parameter;

    public void setParameter(final JavaParameter javaParameter) {
        parameter = javaParameter;
    }

    @Override
    public String createBlockContent(final String id, final Map<String, String> properties) {
        final String javaDoc = createJavaDocPart();
        // FIXME add annotationProperties
        final String data = parameter.getType() + ":" + parameter.getName() + ";" + getMethodData(method) + ";" + getPropertiesAsString(properties);
        final String annotationPart = createAnnotationPart(id, data);
        final String advicebegin = TAB + getAdviceType(method) + getParameterList(parameter) + getAdditionalAdviceDeclartaation(method) + ": ";
        final String pointcut = createPointcut(method);
        final String argsPointcut = createArgsPointcut(parameter);
        final String blockBegin = SPACE + "{";
        final String content = createAdviceBody(parameter, method);
        final String blockEnd = NEWLINE + TAB + "}";
        return javaDoc
                + annotationPart
                + advicebegin
                + pointcut
                + argsPointcut
                + blockBegin
                + content
                + blockEnd;
    }

    protected String createAdviceBody(final JavaMethod javaMethod) {
        String methodBodyContent = "";
        for (final ContentPart part : contentParts) {
            methodBodyContent += replacer.replace(part.getContent(), onType, method, parameter);
        }
        return methodBodyContent.replace("$proceedcall$", createProceedCall(javaMethod));
    }
}
