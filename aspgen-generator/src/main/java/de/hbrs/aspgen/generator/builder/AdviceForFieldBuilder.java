package de.hbrs.aspgen.generator.builder;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.generator.AdviceForField;
import de.hbrs.aspgen.api.generator.ExtendFieldWithAdvices;
import de.hbrs.aspgen.generator.container.AdviceForFieldContainer;

public class AdviceForFieldBuilder extends AbstractBuilder implements ExtendFieldWithAdvices {
    private final JavaField javaField;
    private final JavaClass javaClass;

    public AdviceForFieldBuilder(final JavaClass javaClass, final JavaField javaField) {
        this.javaClass = javaClass;
        this.javaField = javaField;
    }

    @Override
    public AdviceForField appendNewAdvice(final String name) {
        final AdviceForFieldContainer container = new AdviceForFieldContainer();
        container.setOnType(javaClass.getClassName());
        container.setField(javaField);
        container.setName(name);
        blocks.add(container);
        return container;
    }
}
