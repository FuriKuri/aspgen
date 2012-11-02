package de.hbrs.aspgen.generator.builder;

import java.util.Map;

public interface AspectJBlock {
    String createBlockContent(String id, Map<String, String> properties);
    String getName();
}
