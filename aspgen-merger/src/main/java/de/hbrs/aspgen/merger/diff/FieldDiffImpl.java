package de.hbrs.aspgen.merger.diff;

import de.hbrs.aspgen.api.diff.FieldDiff;

public class FieldDiffImpl implements FieldDiff {
    private boolean block;
    private boolean type;
    private boolean onType;

    public boolean isBlock() {
        return block;
    }

    public void setBlock(final boolean block) {
        this.block = block;
    }

    public boolean isType() {
        return type;
    }

    public void setType(final boolean type) {
        this.type = type;
    }

    public boolean isOnType() {
        return onType;
    }

    public void setOnType(final boolean onType) {
        this.onType = onType;
    }
}
