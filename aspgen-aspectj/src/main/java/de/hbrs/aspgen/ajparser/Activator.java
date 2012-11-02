package de.hbrs.aspgen.ajparser;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.hbrs.aspgen.api.parser.AspectJParser;

public class Activator implements BundleActivator {

    @Override
    public void start(final BundleContext context) throws Exception {
        context.registerService(AspectJParser.class.getName(), new AspectJ6Parser(), null);
    }

    @Override
    public void stop(final BundleContext context) throws Exception {

    }

}
