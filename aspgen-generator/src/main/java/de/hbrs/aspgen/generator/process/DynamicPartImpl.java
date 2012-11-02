package de.hbrs.aspgen.generator.process;

import java.util.List;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.generator.DynamicPart;

public class DynamicPartImpl implements DynamicPart {

    private JavaField field;
    private JavaParameter parameter;

    private List<String> dynamicLines;

    public void setDynamicLines(final List<String> dynamicLines) {
        this.dynamicLines = dynamicLines;
    }

    @Override
    public List<String> getDynamicLines() {
        return dynamicLines;
    }

    @Override
    public JavaField getField() {
        return field;
    }

    @Override
    public JavaParameter getParameter() {
        return parameter;
    }

    public void setParameter(final JavaParameter parameter) {
        this.parameter = parameter;
    }

    public void setField(final JavaField field) {
        this.field = field;
    }

}
