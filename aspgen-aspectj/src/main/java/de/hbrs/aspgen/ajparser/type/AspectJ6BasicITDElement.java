package de.hbrs.aspgen.ajparser.type;

import de.hbrs.aspgen.api.ast.PositionContent;

public abstract class AspectJ6BasicITDElement extends AspectJ6ITDConstructor {
    private PositionContent type;


    public PositionContent getType() {
        return type;
    }

    public void setType(final PositionContent type) {
        this.type = type;
    }

}
