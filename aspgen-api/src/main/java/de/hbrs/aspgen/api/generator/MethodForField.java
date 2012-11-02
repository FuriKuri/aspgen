package de.hbrs.aspgen.api.generator;


public interface MethodForField {
    void setMethodDeclaration(String string);
    void addLine(String string);
    void setJavaDoc(String javaDoc);
    void addAnnotation(String annotation);
}
