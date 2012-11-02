package de.hbrs.aspgen.api.generator;


public interface ConstructorForClass {
    void addLine(String line);
    void addLineForeachField(String line);
    void addParameter(String parameter);
    void addParameterForFields(String dynamicPart);
    void setJavaDoc(String javaDoc);
    void addAnnotation(String annotation);
}
