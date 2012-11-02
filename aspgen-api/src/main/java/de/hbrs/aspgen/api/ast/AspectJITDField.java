package de.hbrs.aspgen.api.ast;

public interface AspectJITDField extends AspectJBlock {
    PositionContent getJavaDoc();
    PositionContent getAnnotations();
    PositionContent getModifer();
    PositionContent getType();
    PositionContent getOnType();
    PositionContent getBlock();

    // TODO test start and end position und Annotation data
}
