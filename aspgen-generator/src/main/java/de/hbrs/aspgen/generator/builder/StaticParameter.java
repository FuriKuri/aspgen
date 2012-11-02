package de.hbrs.aspgen.generator.builder;

import java.util.Arrays;
import java.util.List;




public class StaticParameter implements ContentPart {

    private final String staticPart;

    public StaticParameter(final String staticPart) {
        this.staticPart = staticPart;
    }

    @Override
    public String getContent() {
        return staticPart;
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
