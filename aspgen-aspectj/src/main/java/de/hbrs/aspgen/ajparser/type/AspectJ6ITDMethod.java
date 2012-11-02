package de.hbrs.aspgen.ajparser.type;

import de.hbrs.aspgen.api.ast.AspectJITDMethod;
import de.hbrs.aspgen.api.ast.PositionContent;

public class AspectJ6ITDMethod extends AspectJ6BasicITDElement implements AspectJITDMethod {
    private PositionContent parameters;

    @Override
    public PositionContent getParameters() {
        return parameters;
    }

    public void setParameters(final PositionContent parameters) {
        this.parameters = parameters;
    }
}
