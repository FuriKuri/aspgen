package de.hbrs.aspgen.generator.condition;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import de.hbrs.aspgen.api.registry.GeneratorRegistry;
import de.hbrs.aspgen.generator.condition.pre.PreconditionGenerator;


public class ConditionGeneratorActivator implements BundleActivator {

    private final PreconditionGenerator preconditionGenerator = new PreconditionGenerator();

    @Override
    public void start(final BundleContext context) throws Exception {
        final ServiceReference ref3 = context.getServiceReference(GeneratorRegistry.class.getName());
        final GeneratorRegistry registry = (GeneratorRegistry) context.getService(ref3);
        registry.registerGenerator(preconditionGenerator);
    }

    @Override
    public void stop(final BundleContext context) throws Exception {
        final ServiceReference ref3 = context.getServiceReference(GeneratorRegistry.class.getName());
        final GeneratorRegistry registry = (GeneratorRegistry) context.getService(ref3);
        registry.unregisterGenerator(preconditionGenerator);
    }

}
