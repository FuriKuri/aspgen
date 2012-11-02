package de.hbrs.aspgen.merger.diff;

import de.hbrs.aspgen.api.ast.AspectJAdvice;
import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJDeclare;
import de.hbrs.aspgen.api.ast.AspectJITDConstructor;
import de.hbrs.aspgen.api.ast.AspectJITDField;
import de.hbrs.aspgen.api.ast.AspectJITDMethod;
import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.diff.AnnotationData;

public class AspectJDiffFiller {
    public void addNewFieldDiff(final AspectJDiffImpl diff,
            final AspectJITDConstructor cons, final JavaField javaField) {
        if (diff.getAspectJFieldDiff(javaField) == null) {
            final AspectJFieldDiffImpl fieldDiff = new AspectJFieldDiffImpl();
            final AnnotationData annotationData = new AnnotationData(cons.getAnnotationId());
            fieldDiff.setAnnotationData(annotationData);
            fieldDiff.setField(javaField);
            diff.addAspectJFieldDiffs(fieldDiff);
        }
    }

    public void addNewMethodDiff(final AspectJDiffImpl diff,
            final AspectJDeclare field, final JavaMethod javaMethod) {
        if (diff.getAspectJMethodDiff(javaMethod) == null) {
            final AspectJMethodDiffImpl methodDiff = new AspectJMethodDiffImpl();
            final AnnotationData annotationData = new AnnotationData(field.getAnnotationId());
            methodDiff.setAnnotationData(annotationData);
            methodDiff.setMethod(javaMethod);
            diff.addAspectJMethodDiffs(methodDiff);
        }
        if (!diff.getAspectJMethodDiff(javaMethod).getAnnotationData().getId().matches("\\d+")) {
            if (field.getAnnotationId().matches("\\d+")) {
                diff.getAspectJMethodDiff(javaMethod).getAnnotationData().updateId(field.getAnnotationId());
            }
        }
    }

    public void addNewMethodDiff(final AspectJDiffImpl diff,
            final AspectJITDField field, final JavaMethod javaMethod) {
        if (diff.getAspectJMethodDiff(javaMethod) == null) {
            final AspectJMethodDiffImpl methodDiff = new AspectJMethodDiffImpl();
            final AnnotationData annotationData = new AnnotationData(field.getAnnotationId());
            methodDiff.setAnnotationData(annotationData);
            methodDiff.setMethod(javaMethod);
            diff.addAspectJMethodDiffs(methodDiff);
        }
        if (!diff.getAspectJMethodDiff(javaMethod).getAnnotationData().getId().matches("\\d+")) {
            if (field.getAnnotationId().matches("\\d+")) {
                diff.getAspectJMethodDiff(javaMethod).getAnnotationData().updateId(field.getAnnotationId());
            }
        }
    }

    public void addNewFieldDiff(final AspectJDiffImpl diff,
            final AspectJAdvice advice, final JavaField field) {
        if (diff.getAspectJFieldDiff(field) == null) {
            final AspectJFieldDiffImpl fieldDiff = new AspectJFieldDiffImpl();
            final AnnotationData annotationData = new AnnotationData(advice.getAnnotationId());
            fieldDiff.setAnnotationData(annotationData);
            fieldDiff.setField(field);
            diff.addAspectJFieldDiffs(fieldDiff);
        }
        if (!diff.getAspectJFieldDiff(field).getAnnotationData().getId().matches("\\d+")) {
            if (advice.getAnnotationId().matches("\\d+")) {
                diff.getAspectJFieldDiff(field).getAnnotationData().updateId(advice.getAnnotationId());
            }
        }
    }

    public void addNewMethodDiff(final AspectJDiffImpl diff,
            final AspectJAdvice advice, final JavaMethod javaMethod) {
        if (diff.getAspectJMethodDiff(javaMethod) == null) {
            final AspectJMethodDiffImpl methodDiff = new AspectJMethodDiffImpl();
            final AnnotationData annotationData = new AnnotationData(advice.getAnnotationId());
            methodDiff.setAnnotationData(annotationData);
            methodDiff.setMethod(javaMethod);
            diff.addAspectJMethodDiffs(methodDiff);
        }
        if (!diff.getAspectJMethodDiff(javaMethod).getAnnotationData().getId().matches("\\d+")) {
            if (advice.getAnnotationId().matches("\\d+")) {
                diff.getAspectJMethodDiff(javaMethod).getAnnotationData().updateId(advice.getAnnotationId());
            }
        }
    }

    public void addNewParameterDiff(final AspectJDiffImpl diff,
            final AspectJAdvice advice, final JavaMethod javaMethod,
            final JavaParameter javaParameter) {
        if (diff.getAspectJParameterDiff(javaParameter, javaMethod) == null) {
            final AspectJParameterDiffImpl parameterDiff = new AspectJParameterDiffImpl();
            final AnnotationData annotationData = new AnnotationData(advice.getAnnotationId());
            parameterDiff.setAnnotationData(annotationData);
            parameterDiff.setMethod(javaMethod);
            parameterDiff.setParameter(javaParameter);
            diff.addAspectJParameterDiffs(parameterDiff);
        }
//        if (!diff.getAspectJParameterDiff(javaParameter, javaMethod).getAnnotationData().getId().matches("\\d+")) {
//            if (advice.getAnnotationId().matches("\\d+")) {
//                diff.getAspectJParameterDiff(javaParameter, javaMethod).getAnnotationData().updateId(advice.getAnnotationId());
//            }
//        }
    }

    public void addNewClassDiff(final AspectJDiffImpl diff,
            final AspectJBlock cons) {
        if (diff.getAspectJClassDiff() == null) {
            final AspectJClassDiffImpl classDiff = new AspectJClassDiffImpl();
            final AnnotationData annotationData = new AnnotationData(cons.getAnnotationId());
            classDiff.setAnnotationData(annotationData);
            diff.setAspectJClassDiff(classDiff);
        }
        if (!diff.getAspectJClassDiff().getAnnotationData().getId().matches("\\d+")) {
            if (cons.getAnnotationId().matches("\\d+")) {
                diff.getAspectJClassDiff().getAnnotationData().updateId(cons.getAnnotationId());
            }
        }
    }

    public void addNewFieldDiff(final AspectJDiffImpl diff,
            final AspectJITDMethod method, final JavaField field) {
        if (diff.getAspectJFieldDiff(field) == null) {
            final AspectJFieldDiffImpl fieldDiff = new AspectJFieldDiffImpl();
            final AnnotationData annotationData = new AnnotationData(method.getAnnotationId());
            fieldDiff.setAnnotationData(annotationData);
            fieldDiff.setField(field);
            diff.addAspectJFieldDiffs(fieldDiff);
        }

        if (!diff.getAspectJFieldDiff(field).getAnnotationData().getId().matches("\\d+")) {
            if (method.getAnnotationId().matches("\\d+")) {
                diff.getAspectJFieldDiff(field).getAnnotationData().updateId(method.getAnnotationId());
            }
        }
    }

    public void addNewClassDiff(final AspectJDiffImpl diff,
            final AspectJITDMethod method) {
        if (diff.getAspectJClassDiff() == null) {
            final AspectJClassDiffImpl classDiff = new AspectJClassDiffImpl();
            final AnnotationData annotationData = new AnnotationData(method.getAnnotationId());
            classDiff.setAnnotationData(annotationData);
            diff.setAspectJClassDiff(classDiff);
        }
        if (!diff.getAspectJClassDiff().getAnnotationData().getId().matches("\\d+")) {
            if (method.getAnnotationId().matches("\\d+")) {
                diff.getAspectJClassDiff().getAnnotationData().updateId(method.getAnnotationId());
            }
        }
    }
}
