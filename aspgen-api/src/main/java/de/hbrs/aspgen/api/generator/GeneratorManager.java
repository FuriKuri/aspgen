package de.hbrs.aspgen.api.generator;

import java.util.List;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.file.GeneratedContent;

public interface GeneratorManager {
    List<GeneratedContent> gererateFiles(JavaClass javaClass);

    List<String> getAllGeneratorIds();

    String generateContentForGenerator(JavaClass java6Class, String annotation);

    List<DynamicPartsInBlocks> getDynamicParts(JavaClass javaClass, String annotation);

    String getFullGeneratorName(String name);

    List<DynamicPartsInBlocks> getDynamicParts(JavaClass javaClass,
            String annotation, String annotationId);
}
