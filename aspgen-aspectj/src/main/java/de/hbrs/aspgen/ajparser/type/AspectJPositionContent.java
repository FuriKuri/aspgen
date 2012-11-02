package de.hbrs.aspgen.ajparser.type;

import de.hbrs.aspgen.api.ast.PositionContent;

public class AspectJPositionContent implements PositionContent {
    public static final AspectJPositionContent EMTPY_CONTENT = new AspectJPositionContent(-1, -1, "");
    private final int start;
    private final int end;
    private final String content;

    public AspectJPositionContent(final int start, final int end, final String content) {
        this.start = start;
        this.end = end;
        this.content = content;
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getEnd() {
        return end;
    }

    @Override
    public String getContent() {
        return content;
    }

}
