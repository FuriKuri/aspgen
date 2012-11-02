package de.hbrs.aspgen.api.ast;

public interface AspectJBlock {
    int getStartPosition();
    int getEndPosition();
    String getAnnotationData();
    String getAnnotationName();
    String getAnnotationId();
}
