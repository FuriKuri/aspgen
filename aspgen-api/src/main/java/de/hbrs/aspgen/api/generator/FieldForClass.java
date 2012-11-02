package de.hbrs.aspgen.api.generator;

public interface FieldForClass {

    void setContent(String string);
    void setJavaDoc(String javaDoc);
    void addAnnotation(String annotation);
}
