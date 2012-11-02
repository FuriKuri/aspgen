package de.hbrs.aspgen.merger.diff;

import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.api.diff.AspectJParameterDiff;

public class AspectJParameterDiffImpl implements AspectJParameterDiff {
    private JavaParameter parameter;
    private JavaMethod method;
    private AnnotationData annotationData;

    public AnnotationData getAnnotationData() {
        return annotationData;
    }

    public void setAnnotationData(final AnnotationData annotationData) {
        this.annotationData = annotationData;
    }

    public JavaParameter getParameter() {
        return parameter;
    }

    public void setParameter(final JavaParameter parameter) {
        this.parameter = parameter;
    }

    public JavaMethod getMethod() {
        return method;
    }

    public void setMethod(final JavaMethod method) {
        this.method = method;
    }

}
