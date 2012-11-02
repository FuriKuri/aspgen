package de.hbrs.aspgen.generator.container;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.generator.MethodForClass;
import de.hbrs.aspgen.generator.builder.ContentPart;
import de.hbrs.aspgen.generator.builder.DynamicLineForFields;
import de.hbrs.aspgen.generator.builder.DynamicMultiLineForFields;


public class MethodForClassContainer extends MethodContainer implements MethodForClass {
    protected List<JavaField> notExcludefields;
    protected List<JavaField> fields;

    public void setNotExcludedFields(final List<JavaField> javaFields) {
        notExcludefields = javaFields;
    }

    public void setJavaFields(final List<JavaField> javaFields) {
        fields = javaFields;
    }

    @Override
    public void addLineForeachField(final String dynamicPart) {
        contentParts.add(new DynamicLineForFields(dynamicPart));
    }

    @Override
    public void addLineForeachField(final String... dynamicParts) {
        contentParts.add(new DynamicMultiLineForFields(dynamicParts));
    }

    @Override
    public String createBlockContent(final String id, final Map<String, String> properties) {
        // FIXME add annotationProperties
        final String data = getFieldList(fields) + ";" + getPropertiesAsString(properties);

        final String javaDocPart = createJavaDocPart();
        final String annotationPart = createAnnotationPart(id, data);
        final String methodSignatur2 = TAB + methodSignatur;
        final String blockBegin = SPACE + "{";
        final String content = createMethodBody();
        final String blockEnd = NEWLINE + TAB + "}";
        return javaDocPart
                + annotationPart
                + methodSignatur2
                + blockBegin
                + content
                + blockEnd;
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
