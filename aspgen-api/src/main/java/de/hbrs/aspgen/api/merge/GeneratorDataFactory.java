package de.hbrs.aspgen.api.merge;

public interface GeneratorDataFactory {
    GeneratorData createGeneratorData(String annotation, String classname);
}
