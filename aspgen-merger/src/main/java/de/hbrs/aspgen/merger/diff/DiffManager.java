package de.hbrs.aspgen.merger.diff;

import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.merge.GeneratorData;

public interface DiffManager {
    void updateDiff(AspectJUnit actualUnit, String actualContent, AspectJDiffImpl diff, GeneratorData data);
}
