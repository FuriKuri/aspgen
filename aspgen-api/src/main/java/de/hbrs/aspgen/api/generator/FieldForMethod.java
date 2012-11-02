package de.hbrs.aspgen.api.generator;

public interface FieldForMethod {
    void setContent(String string);
    void setJavaDoc(String javaDoc);
    void addAnnotation(String annotation);
}
