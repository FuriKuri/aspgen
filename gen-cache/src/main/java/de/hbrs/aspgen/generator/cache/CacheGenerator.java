package de.hbrs.aspgen.generator.cache;

import java.util.Map;

import de.hbrs.aspgen.api.generator.AdviceForMethod;
import de.hbrs.aspgen.api.generator.AdviceForMethodGenerator;
import de.hbrs.aspgen.api.generator.ExtendMethodWithAdvices;
import de.hbrs.aspgen.api.generator.ExtendMethodWithFields;
import de.hbrs.aspgen.api.generator.FieldForMethod;
import de.hbrs.aspgen.api.generator.FieldForMethodGenerator;
import de.hbrs.aspgen.core.export.AbstractGeneratorBundle;

public class CacheGenerator extends AbstractGeneratorBundle implements FieldForMethodGenerator, AdviceForMethodGenerator {

    @Override
    public String getName() {
        return Cache.class.getName();
    }

    @Override
    public void extendJavaClass(final ExtendMethodWithFields builder,
            final Map<String, String> properties) {
        builder.addImport("de.hbrs.aspgen.generator.cache.MethodCache");
        final FieldForMethod method = builder.appendNewField("Cache Field");
        method.setContent("private MethodCache<$methodtype$> $methodsignature$ = new MethodCache<$methodtype$>();");
    }

    @Override
    public void extendJavaClass(final ExtendMethodWithAdvices builder,
            final Map<String, String> properties) {
        builder.addImport("java.util.LinkedList");
        builder.addImport("java.util.List");

        final AdviceForMethod adviceForMethod = builder.appendNewAroundAdvice("Cache");
        adviceForMethod.addThisParameter();
        adviceForMethod.addLine("List<Object> parameters = new LinkedList<Object>();");
        adviceForMethod.addLineForeachParameter("parameters.add($parametername$);");
        adviceForMethod.addLine("if ($thisclassname$.$methodsignature$.getCachedValue(parameters) != null) {");
        adviceForMethod.addLine("    return $thisclassname$.$methodsignature$.getCachedValue(parameters);");
        adviceForMethod.addLine("} else {");
        adviceForMethod.addLine("    $methodtype$ result = $proceedcall$;");
        adviceForMethod.addLine("    $thisclassname$.$methodsignature$.putNewValue(parameters, result);");
        adviceForMethod.addLine("    return result;");
        adviceForMethod.addLine("}");
    }

}
