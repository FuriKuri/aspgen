package de.hbrs.aspgen.merger.diff;

import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.api.diff.AspectJMethodDiff;

public class AspectJMethodDiffImpl implements AspectJMethodDiff {
    private JavaMethod method;
    private AnnotationData annotationData;

    public AnnotationData getAnnotationData() {
        return annotationData;
    }

    public void setAnnotationData(final AnnotationData annotationData) {
        this.annotationData = annotationData;
    }

    public JavaMethod getMethod() {
        return method;
    }

    public void setMethod(final JavaMethod method) {
        this.method = method;
    }
}
