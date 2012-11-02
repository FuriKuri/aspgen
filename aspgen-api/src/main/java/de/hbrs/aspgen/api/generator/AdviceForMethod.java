package de.hbrs.aspgen.api.generator;

public interface AdviceForMethod {
    void addLine(String string);
    void addLineForeachParameter(String line);
    void addLineForeachParameter(String... lines);

    void setJavaDoc(String javaDoc);
    void addAnnotation(String annotation);
    void addThisParameter();
    void addSofteningExcption(final String exception);
}
