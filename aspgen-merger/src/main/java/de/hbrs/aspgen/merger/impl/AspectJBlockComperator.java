package de.hbrs.aspgen.merger.impl;

import java.util.Comparator;

import de.hbrs.aspgen.api.ast.AspectJBlock;

public final class AspectJBlockComperator implements Comparator<AspectJBlock> {

    public static final AspectJBlockComperator INSTANCE = new AspectJBlockComperator();

    private AspectJBlockComperator() { }

    @Override
    public int compare(final AspectJBlock o1, final AspectJBlock o2) {
        return o2.getStartPosition() - o1.getStartPosition();
    }


}
