package de.hbrs.aspgen.generator.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.hbrs.aspgen.api.generator.Generator;
import de.hbrs.aspgen.api.registry.GeneratorRegistry;

public class UniqueMapGeneratorRegistry implements GeneratorRegistry {

    private final Map<String, Generator> registry = new HashMap<>();

    @Override
    public Set<String> getRegistredGeneratorNames() {
        return registry.keySet();
    }

    @Override
    public Generator getGenerator(final String string) {
        return registry.get(string);
    }

    // TODO check for existing id for example ToString
    @Override
    public void registerGenerator(final Generator generator) {
        registry.put(generator.getName(), generator);
    }

    @Override
    public void unregisterGenerator(final Generator generator) {
        registry.remove(generator.getName());
    }

}
