package de.hbrs.aspgen.api.ast;


public interface AspectJITDMethod extends AspectJBlock {
    PositionContent getJavaDoc();
    PositionContent getAnnotations();
    PositionContent getModifer();
    PositionContent getType();
    PositionContent getOnType();
    PositionContent getParameters();
    PositionContent getBlock();
}
