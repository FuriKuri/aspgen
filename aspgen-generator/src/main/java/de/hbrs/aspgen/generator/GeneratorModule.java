package de.hbrs.aspgen.generator;

import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.registry.GeneratorRegistry;
import de.hbrs.aspgen.generator.process.AspectJGeneratorManager;
import de.hbrs.aspgen.generator.registry.UniqueMapGeneratorRegistry;

public class GeneratorModule extends AbstractModule {

    private final LogService logService;

    public GeneratorModule(final LogService logService) {
        this.logService = logService;
    }

    @Override
    protected void configure() {
        bind(LogService.class).toInstance(logService);
        bind(GeneratorRegistry.class).to(UniqueMapGeneratorRegistry.class).in(Scopes.SINGLETON);
        bind(GeneratorManager.class).to(AspectJGeneratorManager.class).in(Scopes.SINGLETON);
    }

}
