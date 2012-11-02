package de.hbrs.aspgen.jparser.type;

import de.hbrs.aspgen.api.ast.JavaField;

public class Java6Field extends Java6BasicElement implements JavaField {
    private boolean isStatic;
    private String accessType;
    private int startPosition;

    @Override
    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(final boolean isStaticValue) {
        this.isStatic = isStaticValue;
    }

    @Override
    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(final String accessType) {
        this.accessType = accessType;
    }

    @Override
    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(final int startPosition) {
        this.startPosition = startPosition;
    }
}
