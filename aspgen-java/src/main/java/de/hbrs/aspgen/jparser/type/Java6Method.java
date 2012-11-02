package de.hbrs.aspgen.jparser.type;

import java.util.LinkedList;
import java.util.List;

import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;

public class Java6Method extends Java6BasicElement implements JavaMethod {
    private final List<JavaParameter> parameters = new LinkedList<>();
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
    public List<JavaParameter> getParameters() {
        return parameters;
    }

    public void addParameter(final JavaParameter parameter) {
        parameters.add(parameter);
    }

    @Override
    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(final String accessType) {
        this.accessType = accessType;
    }

    @Override
    public String getMethodSignature() {
        final StringBuffer methodSignature = new StringBuffer();
        boolean isFirst = true;
        for (final JavaParameter parameter : parameters) {
            if (isFirst) {
                isFirst = false;
            } else {
                methodSignature.append(", ");
            }
            methodSignature.append(parameter.getType());
        }
        return getName() + "(" + methodSignature + ")";
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

}
