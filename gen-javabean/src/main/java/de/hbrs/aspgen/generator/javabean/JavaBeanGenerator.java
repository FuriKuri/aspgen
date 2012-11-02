package de.hbrs.aspgen.generator.javabean;

import java.util.Map;

import de.hbrs.aspgen.api.generator.ExtendFieldWithMethods;
import de.hbrs.aspgen.api.generator.MethodForField;
import de.hbrs.aspgen.api.generator.MethodForFieldGenerator;
import de.hbrs.aspgen.core.export.AbstractGeneratorBundle;

public class JavaBeanGenerator extends AbstractGeneratorBundle implements MethodForFieldGenerator {

    @Override
    public String getName() {
        return JavaBean.class.getName();
    }

    @Override
    public void extendJavaClass(final ExtendFieldWithMethods builder,
            final Map<String, String> properties) {
        final MethodForField setterMethod = builder.appendNewMethod("Setter");
        setterMethod.setMethodDeclaration("public void set$fieldname$(final $fieldtype$ $fieldname$)");
        setterMethod.addLine("this.$fieldname$ = $fieldname$;");

        final MethodForField getterMethod = builder.appendNewMethod("Getter");
        getterMethod.setMethodDeclaration("public $fieldtype$ get$fieldname$()");
        getterMethod.addLine("return $fieldname$;");
    }
}
