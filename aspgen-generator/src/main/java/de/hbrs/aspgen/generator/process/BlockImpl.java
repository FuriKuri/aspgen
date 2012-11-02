package de.hbrs.aspgen.generator.process;

import java.util.LinkedList;
import java.util.List;

import de.hbrs.aspgen.api.generator.Block;
import de.hbrs.aspgen.api.generator.DynamicPart;

public class BlockImpl implements Block {

    private final String name;
    private List<DynamicPart> dynamicParts = new LinkedList<>();

    public BlockImpl(final String name) {
        this.name = name;
    }

    public void add(final DynamicPart dynamicPart) {
        getDynamicParts().add(dynamicPart);
    }

    public List<DynamicPart> getDynamicParts() {
        return dynamicParts;
    }

    public void setDynamicParts(List<DynamicPart> dynamicParts) {
        this.dynamicParts = dynamicParts;
    }

    public String getName() {
        return name;
    }

}
