package de.hbrs.aspgen.merger.impl;

import java.util.Comparator;

import de.hbrs.aspgen.api.ast.PositionContent;

public final class PositionContentComperator implements Comparator<PositionContent> {

    public static final PositionContentComperator INSTANCE = new PositionContentComperator();

    private PositionContentComperator() { }

    @Override
    public int compare(final PositionContent o1, final PositionContent o2) {
        return o1.getStart() - o2.getStart();
    }

}
