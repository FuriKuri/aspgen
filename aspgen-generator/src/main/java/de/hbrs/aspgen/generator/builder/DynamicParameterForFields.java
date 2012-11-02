package de.hbrs.aspgen.generator.builder;

import java.util.Arrays;
import java.util.List;




public class DynamicParameterForFields implements ContentPart {

    private final String dynamicPart;

    public DynamicParameterForFields(final String dynamicPart) {
        this.dynamicPart = dynamicPart;
    }

    @Override
    public String getContent() {
        return dynamicPart;
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
