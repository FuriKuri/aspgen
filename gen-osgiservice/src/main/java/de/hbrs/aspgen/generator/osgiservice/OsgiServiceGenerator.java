package de.hbrs.aspgen.generator.osgiservice;

import java.util.Map;

import de.hbrs.aspgen.api.generator.AdviceForField;
import de.hbrs.aspgen.api.generator.AdviceForFieldGenerator;
import de.hbrs.aspgen.api.generator.ExtendFieldWithAdvices;
import de.hbrs.aspgen.core.export.AbstractGeneratorBundle;

public class OsgiServiceGenerator extends AbstractGeneratorBundle implements AdviceForFieldGenerator {

    @Override
    public String getName() {
        return OsgiService.class.getName();
    }

    @Override
    public void extendJavaClass(final ExtendFieldWithAdvices builder,
            final Map<String, String> properties) {
        final AdviceForField adviceForField = builder.appendNewAdvice("Service Init");
        adviceForField.setAdviceDeclaration("before(BundleContext context, $classname$ activator) : execution(public void start(BundleContext)) && args(context) && this(activator)");
        adviceForField.addLine("activator.$fieldname$ = ($fieldtype$) context.getService(context.getServiceReference($fieldtype$.class.getName()));");
    }

}
