package de.hbrs.aspgen.api.ast;

public interface AspectJITDConstructor extends AspectJBlock {
    PositionContent getJavaDoc();
    PositionContent getAnnotations();
    PositionContent getModifer();
    PositionContent getOnType();
    PositionContent getParameters();
    PositionContent getBlock();
}
