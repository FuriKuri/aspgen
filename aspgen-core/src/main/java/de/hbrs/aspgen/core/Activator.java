package de.hbrs.aspgen.core;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.hbrs.aspgen.api.dir.DirectoryObserver;
import de.hbrs.aspgen.api.dir.OberservableDirectoryMonitor;

public class Activator implements BundleActivator {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void start(final BundleContext context) throws Exception {
        final Injector injector = Guice.createInjector(new JavaGeneratorModule(context));

        final OberservableDirectoryMonitor directory = injector.getInstance(OberservableDirectoryMonitor.class);
        final DirectoryObserver directoryGenStarter = injector.getInstance(DirectoryObserver.class);
        directory.register(directoryGenStarter);
        final String projectDir = context.getProperty("project.dir");
        directory.startMonitor(projectDir);
    }

    @Override
    public void stop(final BundleContext context) throws Exception {

    }

}
