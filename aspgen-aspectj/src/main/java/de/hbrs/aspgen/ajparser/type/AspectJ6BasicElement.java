package de.hbrs.aspgen.ajparser.type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hbrs.aspgen.api.ast.PositionContent;

public abstract class AspectJ6BasicElement {
    private PositionContent javaDoc;
    private PositionContent block;
    private PositionContent annotations;
    private int startPosition;
    private int endPosition;

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(final int startPosition) {
        this.startPosition = startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(final int endPosition) {
        this.endPosition = endPosition;
    }
    public PositionContent getJavaDoc() {
        return javaDoc;
    }

    public void setJavaDoc(final PositionContent javaDoc) {
        this.javaDoc = javaDoc;
    }

    public PositionContent getBlock() {
        return block;
    }

    public void setBlock(final PositionContent block) {
        this.block = block;
    }

    public PositionContent getAnnotations() {
        return annotations;
    }

    public void setAnnotations(final PositionContent annotations) {
        this.annotations = annotations;
    }

    public String getAnnotationData() {
        return getAnnotationStringPropertyFromGeneratorAnnotation("data").trim();
    }

    public String getAnnotationName() {
        return getAnnotationStringPropertyFromGeneratorAnnotation("name").trim();
    }

    public String getAnnotationId() {
        return getAnnotationPropertyFromGeneratorAnnotation("id").trim();
    }

    private String getAnnotationStringPropertyFromGeneratorAnnotation(final String property) {
        final Pattern generatorAnno = Pattern.compile("@Generated\\([^)]*\\)");
        final Matcher generatorMatcher = generatorAnno.matcher(annotations.getContent());
        if (generatorMatcher.find()) {
            final String annotationString = generatorMatcher.group();
            final Pattern propertyPart = Pattern.compile(".*" + property + "\\s*=\\s*\"(.+?(?<!\\\\))\".*");
            final Matcher propertyMatcher = propertyPart.matcher(annotationString);
            if (propertyMatcher.find()) {
                return propertyMatcher.group(1).replace("\\\"", "\"");
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private String getAnnotationPropertyFromGeneratorAnnotation(final String property) {
        final Pattern generatorAnno = Pattern.compile("@Generated\\([^)]*\\)");
        final Matcher generatorMatcher = generatorAnno.matcher(annotations.getContent());
        if (generatorMatcher.find()) {
            final String annotationString = generatorMatcher.group();
            final Pattern propertyPart = Pattern.compile(property + "\\s*=\\s*[^,]*,");
            final Matcher propertyMatcher = propertyPart.matcher(annotationString);
            if (propertyMatcher.find()) {
                return propertyMatcher.group().split("=")[1].replace(",", "");
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
