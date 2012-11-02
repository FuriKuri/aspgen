package de.hbrs.aspgen.generator.builder.extender;

import java.util.Set;

import de.hbrs.aspgen.api.ast.JavaBlock;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.generator.Generator;
import de.hbrs.aspgen.generator.builder.IdCalculator;

public class ExtendDataContainer {
    private final StringBuffer result;
    private final Generator generator;
    private final JavaClass javaClass;
    private final Set<String> imports;
    private final IdCalculator idCalculator;

    public ExtendDataContainer(final StringBuffer result, final Generator generator,
            final JavaClass javaClass, final Set<String> imports, final IdCalculator idCalculator) {
        super();
        this.result = result;
        this.generator = generator;
        this.javaClass = javaClass;
        this.imports = imports;
        this.idCalculator = idCalculator;
    }

    public Generator getGenerator() {
        return generator;
    }

    public JavaClass getJavaClass() {
        return javaClass;
    }

    public void appendJavaContent(final String javaContent) {
        result.append(javaContent);
    }

    public void addImports(final Set<String> allImports) {
        imports.addAll(allImports);
    }

    public String getIdForBlock(final JavaBlock javaBlock) {
        return idCalculator.getNextIdForBlock(javaBlock);
    }

    public String getNextIdForClass(final JavaClass javaClass2) {
        return idCalculator.getNextIdForClass(javaClass2);
    }

}
