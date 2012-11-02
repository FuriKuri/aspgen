package de.hbrs.aspgen.jparser.type;

import java.util.HashMap;
import java.util.Map;

import de.hbrs.aspgen.api.ast.JavaAnnotation;

public class Java6Annotation implements JavaAnnotation {
    private String name;
    private String singleValue;
    private final Map<String, String> attributes = new HashMap<>();
    private String annotationAsString;
    private int startPosition;
    private int endPosition;

    public void addAttribute(final String key, final String value) {
        attributes.put(key, value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAttribute(final String key) {
        return attributes.get(key);
    }

    @Override
    public String getSingleValue() {
        return singleValue;
    }

    public void setSingleValue(final String singleValue) {
        this.singleValue = singleValue;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public void updateFullQualifedName(final String fullQualifedAnnotation) {
        if (fullQualifedAnnotation.endsWith(name)) {
            name = fullQualifedAnnotation;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Map<String, String> getAllAttribute() {
        return attributes;
    }

    @Override
    public String getStringAttribute(final String key) {
        if (attributes.get(key) == null) {
            return null;
        } else {
            return attributes.get(key).substring(1, attributes.get(key).length() - 1);
        }
    }

    @Override
    public String getAnnotationAsString() {
        return annotationAsString;
    }

    public void setAnnotationAsString(final String annotationAsString) {
        this.annotationAsString = annotationAsString;
    }

    @Override
    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(final int startPosition) {
        this.startPosition = startPosition;
    }

    @Override
    public int getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(final int endPosition) {
        this.endPosition = endPosition;
    }

    @Override
    public void removeAttribute(final String attribute) {
        attributes.remove(attribute);
    }
}
