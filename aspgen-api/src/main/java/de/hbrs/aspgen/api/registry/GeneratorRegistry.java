package de.hbrs.aspgen.api.registry;

import java.util.Set;

import de.hbrs.aspgen.api.generator.Generator;

public interface GeneratorRegistry {
    Set<String> getRegistredGeneratorNames();
    Generator getGenerator(String string);
    void registerGenerator(Generator generator);
    void unregisterGenerator(Generator generator);
}
