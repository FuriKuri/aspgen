package de.hbrs.aspgen.generator.container;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.generator.AdviceForField;
import de.hbrs.aspgen.generator.builder.AspectJBlock;
import de.hbrs.aspgen.generator.builder.ContentPart;
import de.hbrs.aspgen.generator.builder.StaticLine;

public class AdviceForFieldContainer extends AspectJContainer implements AspectJBlock, AdviceForField {
    private JavaField javaField;
    private String adviceDeclaration;
    private final List<ContentPart> contentParts = new LinkedList<>();


    public void setField(final JavaField javaField) {
        this.javaField = javaField;
    }

    @Override
    public void setAdviceDeclaration(final String adviceDeclaration) {
        this.adviceDeclaration = adviceDeclaration.replace("$classname$", onType);
    }

    @Override
    public void addLine(final String line) {
        contentParts.add(new StaticLine(line));
    }

    @Override
    public String createBlockContent(final String id, final Map<String, String> properties) {
        final String javaDoc = createJavaDocPart();
        // FIXME add annotationProperties
        final String data = javaField.getType() + ":" + javaField.getName() + ";" + getPropertiesAsString(properties);
        final String annotationPart = createAnnotationPart(id, data);
        final StringBuffer content = new StringBuffer();
        for (final ContentPart part : contentParts) {
            content.append(replacer.replace(part.getContent(), onType, javaField));
        }
        return javaDoc + annotationPart
                + TAB + adviceDeclaration + " {"
                + content.toString()
                + NEWLINE + TAB + "}";
    }
}
