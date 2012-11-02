package de.hbrs.aspgen.ajparser.type;

import de.hbrs.aspgen.api.ast.AspectJAdvice;
import de.hbrs.aspgen.api.ast.PositionContent;

public class AspectJ6Advice extends AspectJ6BasicElement implements AspectJAdvice {
    private PositionContent adviceHead;
    private PositionContent pointcut;

    @Override
    public PositionContent getPointcut() {
        return pointcut;
    }

    public void setPointcut(final PositionContent pointcut) {
        this.pointcut = pointcut;
    }

    @Override
    public PositionContent getAdviceHead() {
        return adviceHead;
    }

    public void setAdviceHead(final PositionContent adviceHead) {
        this.adviceHead = adviceHead;
    }
}
