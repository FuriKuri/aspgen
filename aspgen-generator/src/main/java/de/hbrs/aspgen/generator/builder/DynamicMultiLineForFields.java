package de.hbrs.aspgen.generator.builder;

import java.util.LinkedList;
import java.util.List;

import de.hbrs.aspgen.generator.container.AspectJContainer;


public class DynamicMultiLineForFields implements ContentPart {

    private final String[] dynamicParts;

    public DynamicMultiLineForFields(final String... dynamicParts) {
        this.dynamicParts = dynamicParts;
    }

    @Override
    public String getContent() {
        final StringBuilder sb = new StringBuilder();
        for (final String dynamicPart : dynamicParts) {
            sb.append(AspectJContainer.NEWLINE
                    + AspectJContainer.TAB
                    + AspectJContainer.TAB
                    + dynamicPart);
        }
        return sb.toString();
    }

    @Override
    public boolean isForEachPlaceHolder() {
        return true;
    }

    @Override
    public List<String> getContentWithoutNewLinesAndTabs() {
        final List<String> lines = new LinkedList<>();
        for (final String dynamicPart : dynamicParts) {
            lines.add(dynamicPart);
        }
        return lines;
    }
}
