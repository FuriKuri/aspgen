package de.hbrs.aspgen.merger.impl;

import java.util.Comparator;

import de.hbrs.aspgen.api.ast.JavaAnnotation;

public final class AnnotationComperator implements Comparator<JavaAnnotation> {

    public static final AnnotationComperator INSTANCE = new AnnotationComperator();

    private AnnotationComperator() { }

    @Override
    public int compare(final JavaAnnotation o1, final JavaAnnotation o2) {
        return o2.getStartPosition() - o1.getStartPosition();
    }


}
