package de.hbrs.aspgen.merger.diff;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.api.diff.AspectJFieldDiff;

public class AspectJFieldDiffImpl implements AspectJFieldDiff {
    private JavaField field;
    private AnnotationData annotationData;

    public AnnotationData getAnnotationData() {
        return annotationData;
    }

    public void setAnnotationData(final AnnotationData annotationData) {
        this.annotationData = annotationData;
    }

    public JavaField getField() {
        return field;
    }

    public void setField(final JavaField field) {
        this.field = field;
    }
}
