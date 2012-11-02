package de.hbrs.aspgen.ajparser.type;

import de.hbrs.aspgen.api.ast.AspectJITDConstructor;
import de.hbrs.aspgen.api.ast.PositionContent;

public class AspectJ6ITDConstructor extends AspectJ6BasicElement implements AspectJITDConstructor {
    private PositionContent modifer;
    private PositionContent onType;
    private PositionContent parameters;

    @Override
    public PositionContent getModifer() {
        return modifer;
    }

    public void setModifer(final PositionContent modifer) {
        this.modifer = modifer;
    }

    @Override
    public PositionContent getOnType() {
        return onType;
    }

    public void setOnType(final PositionContent onType) {
        this.onType = onType;
    }


    @Override
    public PositionContent getParameters() {
        return parameters;
    }

    public void setParameters(final PositionContent parameters) {
        this.parameters = parameters;
    }
}
