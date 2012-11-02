package de.hbrs.aspgen.generator.equalshashcode;

import java.util.Map;

import de.hbrs.aspgen.api.generator.ExtendClassWithMethods;
import de.hbrs.aspgen.api.generator.MethodForClass;
import de.hbrs.aspgen.api.generator.MethodForClassGenerator;
import de.hbrs.aspgen.core.export.AbstractGeneratorBundle;

public class EqualsAndHashcodeGenerator extends AbstractGeneratorBundle implements MethodForClassGenerator {

    @Override
    public String getName() {
        return EqualsAndHashcode.class.getName();
    }

    @Override
    public void extendJavaClass(final ExtendClassWithMethods methodBuilder,
            final Map<String, String> properties) {
        methodBuilder.addStaticImport("de.hbrs.aspgen.generator.equalshashcode.EqualsUtil.isEquals");

        final MethodForClass methodForClass = methodBuilder.appendNewMethod("Equals");
        methodForClass.setMethodDeclaration("public boolean equals(final Object obj)");
        methodForClass.addLine("if (this == obj) {");
        methodForClass.addLine("    return true;");
        methodForClass.addLine("}");
        methodForClass.addLine("if (obj == null) {");
        methodForClass.addLine("    return false;");
        methodForClass.addLine("}");
        methodForClass.addLine("if (getClass() != obj.getClass()) {");
        methodForClass.addLine("    return false;");
        methodForClass.addLine("}");
        methodForClass.addLine("final $classname$ other = ($classname$) obj;");
        methodForClass.addLineForeachField(
                "if (!isEquals($fieldname$, other.$fieldname$)) {",
                "    return false;",
                "}");
        methodForClass.addLine("return true;");

        methodBuilder.addImport("de.hbrs.aspgen.generator.equalshashcode.HashCodeUtil");
        methodBuilder.addStaticImport("de.hbrs.aspgen.generator.equalshashcode.HashCodeUtil.hash");

        final MethodForClass methodForClass2 = methodBuilder.appendNewMethod("HashCode");
        methodForClass2.setMethodDeclaration("public int hashCode()");
        methodForClass2.addLine("int result = HashCodeUtil.SEED;");
        methodForClass2.addLineForeachField("result = hash(result, $fieldname$);");
        methodForClass2.addLine("return result;");
    }


}
