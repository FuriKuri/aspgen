package de.hbrs.aspgen.api.generator;

public interface AdviceForField {
    void setAdviceDeclaration(String adviceDeclaration);
    void addLine(String string);
    void setJavaDoc(String javaDoc);
    void addAnnotation(String annotation);
}
