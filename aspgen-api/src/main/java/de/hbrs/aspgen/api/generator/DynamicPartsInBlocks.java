package de.hbrs.aspgen.api.generator;

import java.util.List;

public interface DynamicPartsInBlocks {
    List<Block> getBlocks();

    String getId();

    Block getBlock(String blockName);
}
