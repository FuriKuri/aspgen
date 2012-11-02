package de.hbrs.aspgen.merger.diff;

import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.api.diff.AspectJClassDiff;


public class AspectJClassDiffImpl implements AspectJClassDiff {
    private AnnotationData annotationData;

    public AnnotationData getAnnotationData() {
        return annotationData;
    }

    public void setAnnotationData(final AnnotationData annotationData) {
        this.annotationData = annotationData;
    }
}
