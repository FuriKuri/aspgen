package de.hbrs.aspgen.merger.impl;

import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.merge.GeneratorData;

public interface Merger {
    String mergeContent(AspectJUnit updatedUnit, String updatedConsContent, AspectJUnit newUnit, GeneratorData data);
}
