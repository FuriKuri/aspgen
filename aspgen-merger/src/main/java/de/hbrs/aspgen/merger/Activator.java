package de.hbrs.aspgen.merger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.hbrs.aspgen.api.diff.ContentMerger;
import de.hbrs.aspgen.api.diff.ContentUpdater;
import de.hbrs.aspgen.api.diff.DiffCreator;
import de.hbrs.aspgen.api.diff.JavaUpdater;
import de.hbrs.aspgen.api.merge.GeneratorDataFactory;

public class Activator implements BundleActivator {

    @Override
    public void start(final BundleContext context) throws Exception {
        final Injector injector = Guice.createInjector(new MergerModule(context));
        context.registerService(ContentMerger.class, injector.getInstance(ContentMerger.class), null);
        context.registerService(DiffCreator.class, injector.getInstance(DiffCreator.class), null);
        context.registerService(JavaUpdater.class, injector.getInstance(JavaUpdater.class), null);
        context.registerService(ContentUpdater.class, injector.getInstance(ContentUpdater.class), null);
        context.registerService(GeneratorDataFactory.class, injector.getInstance(GeneratorDataFactory.class), null);

    }



    @Override
    public void stop(final BundleContext context) throws Exception {
        // TODO Auto-generated method stub

    }

}
