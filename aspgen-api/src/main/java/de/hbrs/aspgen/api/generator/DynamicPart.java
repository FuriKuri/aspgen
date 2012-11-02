package de.hbrs.aspgen.api.generator;

import java.util.List;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.ast.JavaParameter;

public interface DynamicPart {
    List<String> getDynamicLines();

    JavaField getField();

    JavaParameter getParameter();
}
