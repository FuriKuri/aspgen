package de.hbrs.aspgen.generator.process;

import java.util.LinkedList;
import java.util.List;

import de.hbrs.aspgen.api.generator.Block;
import de.hbrs.aspgen.api.generator.DynamicPartsInBlocks;

public class DynamicPartInBlockImpl implements DynamicPartsInBlocks {

    private final String id;
    private final List<Block> blocks = new LinkedList<>();

    public DynamicPartInBlockImpl(final String id) {
        this.id = id;
    }

    public void add(final Block block) {
        getBlocks().add(block);
    }

    @Override
    public List<Block> getBlocks() {
        return blocks;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Block getBlock(final String blockName) {
        for (final Block block : blocks) {
            if (block.getName().equals(blockName)) {
                return block;
            }
        }
        return null;
    }

}
