package de.hbrs.aspgen.generator.builder;

import java.util.Arrays;
import java.util.List;

import de.hbrs.aspgen.generator.container.AspectJContainer;



public class StaticLine implements ContentPart {

    private final String staticPart;

    public StaticLine(final String staticPart) {
        this.staticPart = staticPart;
    }

    @Override
    public String getContent() {
        return AspectJContainer.NEWLINE
                + AspectJContainer.TAB
                + AspectJContainer.TAB
                + staticPart;
    }

    @Override
    public boolean isForEachPlaceHolder() {
        return false;
    }

    @Override
    public List<String> getContentWithoutNewLinesAndTabs() {
        return Arrays.asList(staticPart);
    }

}
