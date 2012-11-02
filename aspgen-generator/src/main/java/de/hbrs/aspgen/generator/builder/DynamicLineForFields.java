package de.hbrs.aspgen.generator.builder;

import java.util.Arrays;
import java.util.List;

import de.hbrs.aspgen.generator.container.AspectJContainer;


public class DynamicLineForFields implements ContentPart {

    private final String dynamicPart;

    public DynamicLineForFields(final String dynamicPart) {
        this.dynamicPart = dynamicPart;
    }

    @Override
    public String getContent() {
        return AspectJContainer.NEWLINE
                + AspectJContainer.TAB
                + AspectJContainer.TAB
                + dynamicPart;
    }

    @Override
    public boolean isForEachPlaceHolder() {
        return true;
    }

    @Override
    public List<String> getContentWithoutNewLinesAndTabs() {
        return Arrays.asList(dynamicPart);
    }

}
