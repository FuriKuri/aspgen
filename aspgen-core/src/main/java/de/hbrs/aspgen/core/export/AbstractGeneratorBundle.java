package de.hbrs.aspgen.core.export;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import de.hbrs.aspgen.api.generator.Generator;
import de.hbrs.aspgen.api.registry.GeneratorRegistry;

public abstract class AbstractGeneratorBundle implements BundleActivator, Generator {

    @Override
    public void start(final BundleContext context) throws Exception {
        final ServiceReference ref3 = context.getServiceReference(GeneratorRegistry.class.getName());
        final GeneratorRegistry registry = (GeneratorRegistry) context.getService(ref3);
        registry.registerGenerator(this);
    }

    @Override
    public void stop(final BundleContext context) throws Exception {
        final ServiceReference ref3 = context.getServiceReference(GeneratorRegistry.class.getName());
        final GeneratorRegistry registry = (GeneratorRegistry) context.getService(ref3);
        registry.unregisterGenerator(this);
    }

}
