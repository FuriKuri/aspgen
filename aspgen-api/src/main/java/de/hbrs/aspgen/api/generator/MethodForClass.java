package de.hbrs.aspgen.api.generator;

public interface MethodForClass {
    // TODO check das gesetzt wurde
    void setMethodDeclaration(String string);
    void addLine(String string);
    void addLineForeachField(String line);
    void addLineForeachField(String... lines);
    void setJavaDoc(String javaDoc);
    void addAnnotation(String annotation);
}
