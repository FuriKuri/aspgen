package de.hbrs.aspgen.generator.dao;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import de.hbrs.aspgen.api.registry.GeneratorRegistry;


public class DaoGeneratorActivator implements BundleActivator {
    private final SaveableDaoGenerator saveableDaoGenerator = new SaveableDaoGenerator();
    private final LoadableDaoGenerator loadableDaoGenerator = new LoadableDaoGenerator();
    private final DeleteableDaoGenerator deleteableDaoGenerator = new DeleteableDaoGenerator();

    @Override
    public void start(final BundleContext context) throws Exception {
        final ServiceReference ref3 = context.getServiceReference(GeneratorRegistry.class.getName());
        final GeneratorRegistry registry = (GeneratorRegistry) context.getService(ref3);
        registry.registerGenerator(saveableDaoGenerator);
        registry.registerGenerator(loadableDaoGenerator);
        registry.registerGenerator(deleteableDaoGenerator);

    }

    @Override
    public void stop(final BundleContext context) throws Exception {
        final ServiceReference ref3 = context.getServiceReference(GeneratorRegistry.class.getName());
        final GeneratorRegistry registry = (GeneratorRegistry) context.getService(ref3);
        registry.unregisterGenerator(saveableDaoGenerator);
        registry.unregisterGenerator(loadableDaoGenerator);
        registry.unregisterGenerator(deleteableDaoGenerator);
    }

}
