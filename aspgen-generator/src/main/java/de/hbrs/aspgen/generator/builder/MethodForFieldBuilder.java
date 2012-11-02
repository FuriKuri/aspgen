package de.hbrs.aspgen.generator.builder;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.generator.ExtendFieldWithMethods;
import de.hbrs.aspgen.api.generator.MethodForField;
import de.hbrs.aspgen.generator.container.MethodForFieldContainer;

public class MethodForFieldBuilder extends AbstractBuilder implements ExtendFieldWithMethods {

    private final JavaField javaField;
    private final JavaClass javaClass;

    public MethodForFieldBuilder(final JavaClass javaClass, final JavaField javaField) {
        this.javaClass = javaClass;
        this.javaField = javaField;
    }

    @Override
    public MethodForField appendNewMethod(final String name) {
        final MethodForFieldContainer container = new MethodForFieldContainer();
        container.setOnType(javaClass.getClassName());
        container.setJavaField(javaField);
        container.setName(name);
        blocks.add(container);
        return container;
    }
}
