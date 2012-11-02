package de.hbrs.aspgen.jparser;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.parser.JavaParser;
import de.hbrs.aspgen.jparser.factory.Java7Factory;

public class Activator implements BundleActivator {

    @Override
    public void start(final BundleContext context) throws Exception {
        context.registerService(JavaParser.class.getName(), new Java6Parser(), null);
        context.registerService(JavaFactory.class.getName(), new Java7Factory(), null);
    }

    @Override
    public void stop(final BundleContext context) throws Exception {

    }

}
