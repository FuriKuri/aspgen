package de.hbrs.aspgen.generator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.registry.GeneratorRegistry;

public class Activator implements BundleActivator {

    @Override
    public void start(final BundleContext context) throws Exception {
        final ServiceReference loggingServiceReference = context.getServiceReference(LogService.class.getName());
        final LogService logService = (LogService) context.getService(loggingServiceReference);
        final Injector injector = Guice.createInjector(new GeneratorModule(logService));

        context.registerService(GeneratorRegistry.class, injector.getInstance(GeneratorRegistry.class), null);
        context.registerService(GeneratorManager.class, injector.getInstance(GeneratorManager.class), null);
    }

    @Override
    public void stop(final BundleContext context) throws Exception {

    }

}
