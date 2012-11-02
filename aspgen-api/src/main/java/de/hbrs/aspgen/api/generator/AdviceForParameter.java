package de.hbrs.aspgen.api.generator;

public interface AdviceForParameter {
    void addLine(String string);
    void setJavaDoc(String javaDoc);
    void addAnnotation(String annotation);
}
