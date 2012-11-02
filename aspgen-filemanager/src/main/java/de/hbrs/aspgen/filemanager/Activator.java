package de.hbrs.aspgen.filemanager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.hbrs.aspgen.api.dir.GeneratedFileRelationshipService;
import de.hbrs.aspgen.api.dir.OberservableDirectoryMonitor;
import de.hbrs.aspgen.api.dir.ObservableProjectWriter;
import de.hbrs.aspgen.api.dir.ProjectDirManager;

public class Activator implements BundleActivator {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void start(final BundleContext context) throws Exception {
        final ServiceReference loggingServiceReference = context.getServiceReference(LogService.class.getName());
        final LogService logService = (LogService) context.getService(loggingServiceReference);
        final Injector injector = Guice.createInjector(new FileManagerModule(logService));

        context.registerService(OberservableDirectoryMonitor.class.getName(),
                injector.getInstance(OberservableDirectoryMonitor.class), null);

        context.registerService(ProjectDirManager.class.getName(),
                injector.getInstance(ProjectDirManager.class), null);

        context.registerService(GeneratedFileRelationshipService.class.getName(),
                injector.getInstance(GeneratedFileRelationshipService.class), null);
        context.registerService(ObservableProjectWriter.class.getName(),
                injector.getInstance(ObservableProjectWriter.class), null);
    }

    @Override
    public void stop(final BundleContext context) throws Exception {

    }

}
