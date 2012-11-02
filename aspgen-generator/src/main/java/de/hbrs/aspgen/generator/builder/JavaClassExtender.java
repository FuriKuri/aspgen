package de.hbrs.aspgen.generator.builder;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.hbrs.aspgen.api.ast.JavaAnnotation;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.generator.AdviceForFieldGenerator;
import de.hbrs.aspgen.api.generator.AdviceForMethodGenerator;
import de.hbrs.aspgen.api.generator.AdviceForParameterGenerator;
import de.hbrs.aspgen.api.generator.ConstructorForClassGenerator;
import de.hbrs.aspgen.api.generator.FieldForClassGenerator;
import de.hbrs.aspgen.api.generator.FieldForMethodGenerator;
import de.hbrs.aspgen.api.generator.Generator;
import de.hbrs.aspgen.api.generator.MethodForClassGenerator;
import de.hbrs.aspgen.api.generator.MethodForFieldGenerator;
import de.hbrs.aspgen.generator.builder.extender.AdviceForFieldExtender;
import de.hbrs.aspgen.generator.builder.extender.AdviceForMethodExtender;
import de.hbrs.aspgen.generator.builder.extender.AdviceForParameterExtender;
import de.hbrs.aspgen.generator.builder.extender.ConstructorForClassExtender;
import de.hbrs.aspgen.generator.builder.extender.ExtendDataContainer;
import de.hbrs.aspgen.generator.builder.extender.Extender;
import de.hbrs.aspgen.generator.builder.extender.FieldForClassExtender;
import de.hbrs.aspgen.generator.builder.extender.FieldForMethodExtender;
import de.hbrs.aspgen.generator.builder.extender.MethodForClassExtender;
import de.hbrs.aspgen.generator.builder.extender.MethodForFieldExtender;


public class JavaClassExtender {
    private final JavaClass javaClass;
    private final String generatorId;
    private final Generator generator;
    private final Set<String> imports = new HashSet<>();
    private final IdCalculator idCalculator;
    private final Map<Class<? extends Generator>, Extender> map = new LinkedHashMap<>();

    public JavaClassExtender(final JavaClass javaClass, final Generator generator) {
        this.javaClass = javaClass;
        this.generator = generator;
        generatorId = extractGeneratorId();
        idCalculator = new IdCalculator(generator);

        map.put(MethodForClassGenerator.class, new MethodForClassExtender());
        map.put(ConstructorForClassGenerator.class, new ConstructorForClassExtender());
        map.put(FieldForClassGenerator.class, new FieldForClassExtender());
        map.put(MethodForFieldGenerator.class, new MethodForFieldExtender());
        map.put(FieldForMethodGenerator.class, new FieldForMethodExtender());
        map.put(AdviceForMethodGenerator.class, new AdviceForMethodExtender());
        map.put(AdviceForParameterGenerator.class, new AdviceForParameterExtender());
        map.put(AdviceForFieldGenerator.class, new AdviceForFieldExtender());

    }

    private String extractGeneratorId() {
        final String[] splitedGeneratorPath = generator.getName().split("\\.");
        return splitedGeneratorPath[splitedGeneratorPath.length - 1];
    }

    public String createAspectJFileContent() {
        if (!annotationsContainsGeneratorAnnotation(javaClass.getAllUsedAnnotations())) {
            return "";
        }
        final StringBuffer result = new StringBuffer();
        result.append("public privileged aspect " + javaClass.getClassName() + "_" + generatorId + " perthis(this(" + javaClass.getClassName() + ")) {\n");
        for (final Entry<Class<? extends Generator>, Extender> entry : map.entrySet()) {
            final Class<? extends Generator> clazz = entry.getKey();
            if (clazz.isInstance(generator)) {
                final ExtendDataContainer dataContainer
                        = new ExtendDataContainer(result, generator, javaClass, imports, idCalculator);
                entry.getValue().extend(dataContainer);
            }
        }

        removeLastTwoNewLines(result);
        final String classHeader = createPackageAndJavaImports(result);
        return classHeader + result + "\n}";
    }

    private void removeLastTwoNewLines(final StringBuffer result) {
        if (result.toString().endsWith("\n\n")) {
            result.deleteCharAt(result.length() - 1).deleteCharAt(result.length() - 1);
        }
    }

    private String createPackageAndJavaImports(final StringBuffer result) {
        final StringBuffer classHeader = new StringBuffer();

        if (javaClass.getPackageName() != null && !javaClass.getPackageName().isEmpty()) {
            classHeader.append("package " + javaClass.getPackageName() + ";");
            classHeader.append("\n\n");
        }

        classHeader.append("import de.hbrs.aspgen.annotation.Generated;\n\n");
        if (javaClass.getImports().size() != 0 || javaClass.getStaticImports().size() != 0) {
            for (final String javaClassImport : javaClass.getImports()) {
                classHeader.append("import " + javaClassImport + ";");
                classHeader.append("\n");
            }

            for (final String javaClassImport : javaClass.getStaticImports()) {
                classHeader.append("import static " + javaClassImport + ";");
                classHeader.append("\n");
            }

            classHeader.append("\n");
        }

        if (imports.size() != 0) {
            for (final String generatorImport : imports) {
                classHeader.append(generatorImport);
                classHeader.append("\n");
            }

            classHeader.append("\n");
        }

        return classHeader.toString();
    }

    public JavaAnnotation getGeneratorAnnotation(final List<JavaAnnotation> annotations) {
        for (final JavaAnnotation javaAnnotation : annotations) {
            if (generator.getName().equals(javaAnnotation.getName())) {
                return javaAnnotation;
            }
        }
        return null;
    }

    public boolean annotationsContainsGeneratorAnnotation(final List<JavaAnnotation> annotations) {
        for (final JavaAnnotation javaAnnotation : annotations) {
            if (generator.getName().equals(javaAnnotation.getName())) {
                return true;
            }
        }
        return false;
    }

    public String getAspectJName() {
        return javaClass.getClassName() + "_" + generatorId + ".aj";
    }

    @Override
    public String toString() {
        return generatorId + " - " + javaClass.getClassName();
    }
}
