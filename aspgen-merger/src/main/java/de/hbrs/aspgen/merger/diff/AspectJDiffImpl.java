package de.hbrs.aspgen.merger.diff;

import java.util.LinkedList;
import java.util.List;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.diff.AspectJDiff;
import de.hbrs.aspgen.api.merge.GeneratorData;


public class AspectJDiffImpl implements AspectJDiff {
    private GeneratorData data;
    private AspectJClassDiffImpl aspectJClassDiff;
    private final List<AspectJFieldDiffImpl> aspectJFieldDiffs = new LinkedList<>();
    private final List<AspectJMethodDiffImpl> aspectJMethodDiffs = new LinkedList<>();
    private final List<AspectJParameterDiffImpl> aspectJParameterDiffs = new LinkedList<>();

    public AspectJClassDiffImpl getAspectJClassDiff() {
        return aspectJClassDiff;
    }

    public void setAspectJClassDiff(final AspectJClassDiffImpl aspectJClassDiff) {
        this.aspectJClassDiff = aspectJClassDiff;
    }

    public List<AspectJFieldDiffImpl> getAspectJFieldDiffs() {
        return aspectJFieldDiffs;
    }

    public void addAspectJFieldDiffs(final AspectJFieldDiffImpl aspectJFieldDiff) {
        aspectJFieldDiffs.add(aspectJFieldDiff);
    }

    public AspectJFieldDiffImpl getAspectJFieldDiff(final JavaField javaField) {
        for (final AspectJFieldDiffImpl fieldDiff : aspectJFieldDiffs) {
            final JavaField field = fieldDiff.getField();
            if (field.getName().equals(javaField.getName())
                    && field.getType().equals(javaField.getType())) {
                return fieldDiff;
            }
        }
        return null;
    }

    public AspectJMethodDiffImpl getAspectJMethodDiff(final JavaMethod javaMethod) {
        for (final AspectJMethodDiffImpl methodDiff : aspectJMethodDiffs) {
            final JavaMethod method = methodDiff.getMethod();
            if (method.getMethodSignature().equals(javaMethod.getMethodSignature())) {
                return methodDiff;
            }
        }
        return null;
    }

    public AspectJParameterDiffImpl getAspectJParameterDiff(final JavaParameter javaParameter, final JavaMethod javaMethod) {
        for (final AspectJParameterDiffImpl parameterDiff : aspectJParameterDiffs) {
            final JavaParameter parameter = parameterDiff.getParameter();
            final JavaMethod method = parameterDiff.getMethod();
            if (method.getMethodSignature().equals(javaMethod.getMethodSignature())
                    && parameter.getName().equals(javaParameter.getName())
                    && parameter.getType().equals(javaParameter.getType())) {
                return parameterDiff;
            }
        }
        return null;
    }

    public List<AspectJMethodDiffImpl> getAspectJMethodDiffs() {
        return aspectJMethodDiffs;
    }

    public void addAspectJMethodDiffs(final AspectJMethodDiffImpl aspectJMethodDiff) {
        aspectJMethodDiffs.add(aspectJMethodDiff);
    }

    public List<AspectJParameterDiffImpl> getAspectJParameterDiffs() {
        return aspectJParameterDiffs;
    }

    public void addAspectJParameterDiffs(final AspectJParameterDiffImpl aspectJParameterDiff) {
        aspectJParameterDiffs.add(aspectJParameterDiff);
    }

    public GeneratorData getData() {
        return data;
    }

    public void setData(final GeneratorData data) {
        this.data = data;
    }
}
