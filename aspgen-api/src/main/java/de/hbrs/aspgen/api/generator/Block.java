package de.hbrs.aspgen.api.generator;

import java.util.List;

public interface Block {
    List<DynamicPart> getDynamicParts();

    String getName();
}
