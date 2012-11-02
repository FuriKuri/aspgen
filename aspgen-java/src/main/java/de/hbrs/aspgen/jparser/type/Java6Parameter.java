package de.hbrs.aspgen.jparser.type;

import de.hbrs.aspgen.api.ast.JavaParameter;

public class Java6Parameter extends Java6BasicElement implements JavaParameter {

    private int startPosition;

    @Override
    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(final int startPosition) {
        this.startPosition = startPosition;
    }

}
