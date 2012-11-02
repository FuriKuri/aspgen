package de.hbrs.aspgen.logging;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Activator implements BundleActivator {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void start(final BundleContext context) throws Exception {
        final ServiceReference ref = context.getServiceReference(LogReaderService.class.getName());
        final LogReaderService reader = (LogReaderService) context.getService(ref);

        final Injector injector = Guice.createInjector(new LoggingModule());
        reader.addLogListener(injector.getInstance(LogListener.class));

        final ServiceReference loggingServiceReference = context.getServiceReference(LogService.class.getName());
        final LogService logService = (LogService) context.getService(loggingServiceReference);
        logService.log(LogService.LOG_DEBUG, "Hello");
    }

    @Override
    public void stop(final BundleContext context) throws Exception {

    }

}
