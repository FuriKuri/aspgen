package de.hbrs.aspgen.merger.diff;

import de.hbrs.aspgen.api.diff.AdviceDiff;

public class AdviceDiffImpl implements AdviceDiff {
    private  boolean advice = false;
    private  boolean block = false;
    public boolean isAdvice() {
        return advice;
    }
    public void setAdvice(final boolean advice) {
        this.advice = advice;
    }
    public boolean isBlock() {
        return block;
    }
    public void setBlock(final boolean block) {
        this.block = block;
    }
}
