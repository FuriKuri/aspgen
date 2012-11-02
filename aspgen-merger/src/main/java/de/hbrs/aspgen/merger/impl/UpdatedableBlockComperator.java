package de.hbrs.aspgen.merger.impl;

import java.util.Comparator;

import de.hbrs.aspgen.api.ast.UpdatableBlockForAnnotation;

public final class UpdatedableBlockComperator implements Comparator<UpdatableBlockForAnnotation> {

    public static final UpdatedableBlockComperator INSTANCE = new UpdatedableBlockComperator();

    private UpdatedableBlockComperator() { }

    @Override
    public int compare(final UpdatableBlockForAnnotation o1, final UpdatableBlockForAnnotation o2) {
        return o2.getStartPosition() - o1.getStartPosition();
    }


}
