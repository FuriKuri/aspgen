package de.hbrs.aspgen.api.ast;


public interface AspectJDeclare extends AspectJBlock {
    PositionContent getJavaDoc();
    PositionContent getBlock();
    PositionContent getAnnotations();

    // TODO test start and end position
}
