package de.hbrs.aspgen.generator.builder;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.generator.DynamicPartsInBlocks;
import de.hbrs.aspgen.api.generator.ExtendClassWithMethods;
import de.hbrs.aspgen.api.generator.MethodForClass;
import de.hbrs.aspgen.generator.container.MethodForClassContainer;
import de.hbrs.aspgen.generator.process.BlockImpl;
import de.hbrs.aspgen.generator.process.DynamicPartImpl;
import de.hbrs.aspgen.generator.process.DynamicPartInBlockImpl;

public class MethodForClassBuilder extends AbstractBuilder implements ExtendClassWithMethods {

    private final JavaClass javaClass;
    private final String annotationName;

    public MethodForClassBuilder(final JavaClass javaClass, final String annotationName) {
        this.javaClass = javaClass;
        this.annotationName = annotationName;
    }

    @Override
    public MethodForClass appendNewMethod(final String name) {
        final MethodForClassContainer container = new MethodForClassContainer();
        container.setOnType(javaClass.getClassName());
        container.setJavaFields(javaClass.getFields());
        container.setNotExcludedFields(filterNotExcludedFieldsForName(javaClass.getFields(), name, annotationName));
        container.setName(name);
        blocks.add(container);
        return container;
    }

    public DynamicPartsInBlocks getDynamicParts(final String id) {
        final DynamicPartInBlockImpl dynamicPartInBlock = new DynamicPartInBlockImpl(id);
        for (final AspectJBlock block : blocks) {
            final MethodForClassContainer container = (MethodForClassContainer) block;
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
