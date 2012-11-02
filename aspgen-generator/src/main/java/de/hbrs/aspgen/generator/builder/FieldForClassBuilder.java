package de.hbrs.aspgen.generator.builder;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.generator.ExtendClassWithFields;
import de.hbrs.aspgen.api.generator.FieldForClass;
import de.hbrs.aspgen.generator.container.FieldForClassContainer;

public class FieldForClassBuilder extends AbstractBuilder implements ExtendClassWithFields {
    private final JavaClass javaClass;

    public FieldForClassBuilder(final JavaClass javaClass) {
        this.javaClass = javaClass;
    }

    @Override
    public FieldForClass appendNewField(final String name) {
        final FieldForClassContainer container = new FieldForClassContainer();
        container.setOnType(javaClass.getClassName());
        container.setName(name);
        blocks.add(container);
        return container;
    }
}
