package de.hbrs.aspgen.notification;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

import de.hbrs.aspgen.api.notification.PluginNotifierService;
import de.hbrs.aspgen.notification.server.NotificationServer;

public class Activator implements BundleActivator {

    @Override
    public void start(final BundleContext context) throws Exception {
        final ServiceReference loggingServiceReference = context.getServiceReference(LogService.class.getName());
        final LogService logService = (LogService) context.getService(loggingServiceReference);

        final NotificationServer server = new NotificationServer(logService);
        server.startServer();
        context.registerService(PluginNotifierService.class, server, null);
    }

    @Override
    public void stop(final BundleContext context) throws Exception {

    }
}
