package de.hbrs.aspgen.generator.tostring;

import java.util.Map;

import de.hbrs.aspgen.api.generator.ExtendClassWithMethods;
import de.hbrs.aspgen.api.generator.MethodForClass;
import de.hbrs.aspgen.api.generator.MethodForClassGenerator;
import de.hbrs.aspgen.core.export.AbstractGeneratorBundle;


public class ToStringGenerator extends AbstractGeneratorBundle implements MethodForClassGenerator {

    @Override
    public String getName() {
        return ToString.class.getName();
    }

    @Override
    public void extendJavaClass(final ExtendClassWithMethods extender,
            final Map<String, String> properties) {
        final MethodForClass methodForClass = extender.appendNewMethod("ToString");
        methodForClass.setMethodDeclaration("public String toString()");
        methodForClass.addLine("String result = \"\";");
        methodForClass.addLineForeachField("result += \"$fieldname$ = \" + $fieldname$ + \" \";");
        methodForClass.addLine("return result;");
    }
}
