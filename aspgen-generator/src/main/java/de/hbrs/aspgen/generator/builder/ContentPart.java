package de.hbrs.aspgen.generator.builder;

import java.util.List;


public interface ContentPart {

    String getContent();

    boolean isForEachPlaceHolder();

    List<String> getContentWithoutNewLinesAndTabs();
}
