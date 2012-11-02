package de.hbrs.aspgen.generator.builder;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.generator.ConstructorForClass;
import de.hbrs.aspgen.api.generator.DynamicPartsInBlocks;
import de.hbrs.aspgen.api.generator.ExtendClassWithConstructors;
import de.hbrs.aspgen.generator.container.ConstructorForClassContainer;
import de.hbrs.aspgen.generator.process.BlockImpl;
import de.hbrs.aspgen.generator.process.DynamicPartImpl;
import de.hbrs.aspgen.generator.process.DynamicPartInBlockImpl;

public class ConstructorForClassBuilder extends AbstractBuilder implements ExtendClassWithConstructors {

    private final JavaClass javaClass;
    private final String annotationName;

    public ConstructorForClassBuilder(final JavaClass javaClass, final String annotationName) {
        this.javaClass = javaClass;
        this.annotationName = annotationName;
    }

    @Override
    public ConstructorForClass appendNewConstructor(final String name) {
        final ConstructorForClassContainer container = new ConstructorForClassContainer();
        container.setOnType(javaClass.getClassName());
        container.setNotExcludedFields(filterNotExcludedFieldsForName(javaClass.getFields(), name, annotationName));
        container.setJavaFields(javaClass.getFields());
        container.setName(name);
        blocks.add(container);
        return container;
    }

    public DynamicPartsInBlocks getDynamicParts(final String id) {
        final DynamicPartInBlockImpl dynamicPartInBlock = new DynamicPartInBlockImpl(id);
        for (final AspectJBlock block : blocks) {
            final ConstructorForClassContainer container = (ConstructorForClassContainer) block;
            final BlockImpl block2 = new BlockImpl(container.getName());
            for (final JavaField field : javaClass.getFields()) {
                final DynamicPartImpl dynamicPartImpl = new DynamicPartImpl();
                dynamicPartImpl.setField(field);
                dynamicPartImpl.setDynamicLines(container.getDynamicLinesFor(field));
                block2.add(dynamicPartImpl);
            }
            dynamicPartInBlock.add(block2);
        }
        return dynamicPartInBlock;
    }
}
