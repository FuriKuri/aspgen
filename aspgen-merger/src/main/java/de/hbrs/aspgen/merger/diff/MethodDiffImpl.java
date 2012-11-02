package de.hbrs.aspgen.merger.diff;

import de.hbrs.aspgen.api.diff.MethodDiff;

public class MethodDiffImpl implements MethodDiff {
    private boolean modifer = false;
    private boolean name = false;
    private boolean parameter = false;
    private boolean block = false;
    private boolean javadoc = false;
    private boolean annotation = false;
    private boolean type = false;

    public boolean isModifer() {
        return modifer;
    }

    public void setModifer(final boolean modifer) {
        this.modifer = modifer;
    }

    public boolean isName() {
        return name;
    }

    public void setName(final boolean name) {
        this.name = name;
    }

    public boolean isParameter() {
        return parameter;
    }

    public void setParameter(final boolean parameter) {
        this.parameter = parameter;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(final boolean block) {
        this.block = block;
    }

    public boolean isJavadoc() {
        return javadoc;
    }

    public void setJavadoc(final boolean javadoc) {
        this.javadoc = javadoc;
    }

    public boolean isAnnotation() {
        return annotation;
    }

    public void setAnnotation(final boolean annotation) {
        this.annotation = annotation;
    }

    public boolean isType() {
        return type;
    }

    public void setType(final boolean type) {
        this.type = type;
    }
}
