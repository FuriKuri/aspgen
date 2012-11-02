package de.hbrs.aspgen.generator.builder;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.generator.ExtendMethodWithFields;
import de.hbrs.aspgen.api.generator.FieldForMethod;
import de.hbrs.aspgen.generator.container.FieldForMethodContainer;

public class FieldForMethodBuilder extends AbstractBuilder implements ExtendMethodWithFields {
    private final JavaClass javaClass;
    private final JavaMethod javaMethod;

    public FieldForMethodBuilder(final JavaClass javaClass, final JavaMethod javaMethod) {
        this.javaClass = javaClass;
        this.javaMethod = javaMethod;
    }

    @Override
    public FieldForMethod appendNewField(final String name) {
        final FieldForMethodContainer container = new FieldForMethodContainer();
        container.setOnType(javaClass.getClassName());
        container.setMethod(javaMethod);
        container.setName(name);
        blocks.add(container);
        return container;
    }
}
