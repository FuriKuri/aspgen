package de.hbrs.aspgen.jparser.type;

import java.util.LinkedList;
import java.util.List;

import de.hbrs.aspgen.api.ast.JavaAnnotation;
import de.hbrs.aspgen.api.ast.JavaBlock;

public class Java6BasicElement implements JavaBlock {
    private String name;
    private String type;
    private final List<JavaAnnotation> annotations = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public List<JavaAnnotation> getAnnotations() {
        return annotations;
    }

    public void addAnnotation(final JavaAnnotation annotation) {
        annotations.add(annotation);
    }
}
