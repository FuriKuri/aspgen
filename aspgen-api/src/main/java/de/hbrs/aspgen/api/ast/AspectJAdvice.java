package de.hbrs.aspgen.api.ast;


public interface AspectJAdvice extends AspectJBlock {
    PositionContent getJavaDoc();
    PositionContent getPointcut();
    PositionContent getBlock();
    PositionContent getAdviceHead();
    PositionContent getAnnotations();

    // TODO test start and end position
}
