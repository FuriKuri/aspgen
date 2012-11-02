package de.hbrs.aspgen.generator.container;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.generator.ConstructorForClass;
import de.hbrs.aspgen.generator.builder.AspectJBlock;
import de.hbrs.aspgen.generator.builder.ContentPart;
import de.hbrs.aspgen.generator.builder.DynamicLineForFields;
import de.hbrs.aspgen.generator.builder.DynamicParameterForFields;
import de.hbrs.aspgen.generator.builder.StaticLine;
import de.hbrs.aspgen.generator.builder.StaticParameter;


public class ConstructorForClassContainer extends AspectJContainer implements ConstructorForClass, AspectJBlock {
    protected List<JavaField> notExcludefields;
    protected List<JavaField> fields;
    private final List<ContentPart> contentParts = new LinkedList<>();
    private final List<ContentPart> parameters = new LinkedList<>();

    public void setJavaFields(final List<JavaField> javaFields) {
        fields = javaFields;
    }

    public void setNotExcludedFields(final List<JavaField> javaFields) {
        notExcludefields = javaFields;
    }

    @Override
    public void addLine(final String staticPart) {
        contentParts.add(new StaticLine(staticPart));
    }

    @Override
    public void addLineForeachField(final String dynamicPart) {
        contentParts.add(new DynamicLineForFields(dynamicPart));
    }

    @Override
    public String createBlockContent(final String id, final Map<String, String> properties) {
        // FIXME add annotationProperties
        final String data = getFieldList(fields) + ";" + getPropertiesAsString(properties);
        final String javaDocPart = createJavaDocPart();
        final String annotationPart = createAnnotationPart(id, data);
        final String constructor = TAB + "public " + onType + ".new(" + getParameters() + ")";
        final String blockBegin = SPACE + "{";
        final String content = createMethodBody();
        final String blockEnd = NEWLINE + TAB + "}";
        return javaDocPart
                + annotationPart
                + constructor
                + blockBegin
                + content
                + blockEnd;
    }

    private String getParameters() {
        boolean isFirst = true;
        final StringBuffer parametersString = new StringBuffer();
        for (final ContentPart part : parameters) {
            if (part.isForEachPlaceHolder()) {
                for (final JavaField javaField : fields) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        parametersString.append(", ");
                    }
                    parametersString.append(replacer.replace(part.getContent(), onType, javaField));
                }
            } else {
                if (isFirst) {
                    isFirst = false;
                } else {
                    parametersString.append(", ");
                }
                parametersString.append(replacer.replace(part.getContent(), onType));
            }
        }
        return parametersString.toString();
    }

    private String createMethodBody() {
        String methodBodyContent = "";
        for (final ContentPart part : contentParts) {
            if (part.isForEachPlaceHolder()) {
                for (final JavaField javaField : notExcludefields) {
                    methodBodyContent += replacer.replace(part.getContent(), onType, javaField);
                }
            } else {
                methodBodyContent += replacer.replace(part.getContent(), onType);
            }
        }
        return methodBodyContent;
    }

    @Override
    public void addParameter(final String parameter) {
        parameters.add(new StaticParameter(parameter));
    }

    @Override
    public void addParameterForFields(final String dynamicPart) {
        parameters.add(new DynamicParameterForFields(dynamicPart));
    }

    public List<String> getDynamicLinesFor(final JavaField field) {
        final List<String> lines = new LinkedList<>();
        for (final ContentPart part : contentParts) {
            if (part.isForEachPlaceHolder()) {
                for (final String line : part.getContentWithoutNewLinesAndTabs()) {
                    lines.add(replacer.replace(line, onType, field));
                }
            }
        }
        return lines;
    }
}
