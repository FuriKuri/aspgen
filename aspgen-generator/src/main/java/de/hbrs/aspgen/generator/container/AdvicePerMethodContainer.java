package de.hbrs.aspgen.generator.container;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.generator.AdviceForMethod;
import de.hbrs.aspgen.generator.builder.ContentPart;
import de.hbrs.aspgen.generator.builder.DynamicLineForFields;
import de.hbrs.aspgen.generator.builder.DynamicMultiLineForFields;


public abstract class AdvicePerMethodContainer extends AdviceContainer implements AdviceForMethod {
    private List<JavaParameter> notExcludedParameters;

    @Override
    public void addLineForeachParameter(final String dynamicPart) {
        contentParts.add(new DynamicLineForFields(dynamicPart));
    }

    @Override
    public void addLineForeachParameter(final String... dynamicParts) {
        contentParts.add(new DynamicMultiLineForFields(dynamicParts));
    }

    @Override
    public String createBlockContent(final String id, final Map<String, String> properties) {
        // FIXME add annotationProperties and Parameters
        final String data = getMethodData(method) + ";" + getPropertiesAsString(properties);

        final StringBuffer blocks = new StringBuffer();
        final String annotationPartForSoftening = createAnnotationPartForSoftening(id, data, "softening");

        final String softeningsExceptions = createSofteningsExceptions(method, annotationPartForSoftening);
        final String javaDoc = createJavaDocPart();
        final String annotationPart = createAnnotationPart(id, data);
        final String advicebegin = TAB + getAdviceType(method) + getParameterList(method) + getAdditionalAdviceDeclartaation(method) + ": ";
        final String pointcut = createPointcut(method);
        final String argsPointcut = createArgsPointcut(method);
        final String blockBegin = SPACE + "{";
        final String content = createAdviceBody(method);
        final String blockEnd = NEWLINE + TAB + "}";
        blocks.append(softeningsExceptions
                + javaDoc
                + annotationPart
                + advicebegin
                + pointcut
                + argsPointcut
                + blockBegin
                + content
                + blockEnd);
        return blocks.toString();
    }

    protected String createAdviceBody(final JavaMethod javaMethod) {
        String methodBodyContent = "";
        for (final ContentPart part : contentParts) {
            if (part.isForEachPlaceHolder()) {
                for (final JavaParameter parameter : notExcludedParameters) {
                    methodBodyContent += replacer.replace(part.getContent(), onType, method, parameter);
                }
            } else {
                methodBodyContent += replacer.replace(part.getContent(), onType, method);
            }
        }
        return methodBodyContent.replace("$proceedcall$", createProceedCall(javaMethod))
                .replace("$returnproceedcall$", createReturnProceedCall(javaMethod))
                .replace("$storeproceedcall$", createStoreProceedCall(javaMethod))
                .replace("$returnstoredproceedcall$", returnStoreProceedCall(javaMethod));
    }

    public void setNotExcludedParameters(final List<JavaParameter> notExcludedParameter) {
        this.notExcludedParameters = notExcludedParameter;
    }

    public List<String> getDynamicLinesFor(final JavaParameter parameter) {
        final List<String> lines = new LinkedList<>();
        for (final ContentPart part : contentParts) {
            if (part.isForEachPlaceHolder()) {
                for (final String line : part.getContentWithoutNewLinesAndTabs()) {
                    lines.add(replacer.replace(line, onType, method, parameter));
                }
            }
        }
        return lines;
    }
}
