package de.hbrs.aspgen.generator.builder;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.generator.AdviceForMethod;
import de.hbrs.aspgen.api.generator.DynamicPartsInBlocks;
import de.hbrs.aspgen.api.generator.ExtendMethodWithAdvices;
import de.hbrs.aspgen.generator.container.AdvicePerMethodContainer;
import de.hbrs.aspgen.generator.container.AfterAdvicePerMethodContainer;
import de.hbrs.aspgen.generator.container.AroundAdvicePerMethodContainer;
import de.hbrs.aspgen.generator.container.BeforeAdvicePerMethodContainer;
import de.hbrs.aspgen.generator.process.BlockImpl;
import de.hbrs.aspgen.generator.process.DynamicPartImpl;
import de.hbrs.aspgen.generator.process.DynamicPartInBlockImpl;

public class AdviceForMethodBuilder extends AbstractBuilder implements ExtendMethodWithAdvices {

    private final JavaClass javaClass;
    private final JavaMethod javaMethod;
    private final String annotationName;


    public AdviceForMethodBuilder(final JavaClass javaClass, final JavaMethod javaMethod, final String annotationName) {
        this.javaClass = javaClass;
        this.javaMethod = javaMethod;
        this.annotationName = annotationName;
    }

    @Override
    public AdviceForMethod appendNewBeforeAdvice(final String name) {
        final BeforeAdvicePerMethodContainer container = new BeforeAdvicePerMethodContainer();
        return fillContainer(name, container);
    }

    @Override
    public AdviceForMethod appendNewAfterAdvice(final String name) {
        final AfterAdvicePerMethodContainer container = new AfterAdvicePerMethodContainer();
        return fillContainer(name, container);
    }

    @Override
    public AdviceForMethod appendNewAroundAdvice(final String name) {
        final AroundAdvicePerMethodContainer container = new AroundAdvicePerMethodContainer();
        return fillContainer(name, container);
    }

    private AdviceForMethod fillContainer(final String name,
            final AdvicePerMethodContainer container) {
        container.setOnType(javaClass.getClassName());
        container.setMethod(javaMethod);
        if (javaClass.isInterface()) {
            container.isInterface();
        }
        container.setNotExcludedParameters(filterNotExcludedParameterForName(javaMethod.getParameters(), name, annotationName));
        container.setName(name);
        blocks.add(container);
        return container;
    }

    public DynamicPartsInBlocks getDynamicParts(final String id) {
        final DynamicPartInBlockImpl dynamicPartInBlock = new DynamicPartInBlockImpl(id);

        for (final AspectJBlock block : blocks) {
            final AdvicePerMethodContainer container = (AdvicePerMethodContainer) block;
            final BlockImpl block2 = new BlockImpl(container.getName());
            for (final JavaParameter parameter : javaMethod.getParameters()) {
                final DynamicPartImpl dynamicPartImpl = new DynamicPartImpl();
                dynamicPartImpl.setParameter(parameter);
                dynamicPartImpl.setDynamicLines(container.getDynamicLinesFor(parameter));
                block2.add(dynamicPartImpl);
            }
            dynamicPartInBlock.add(block2);
        }
        return dynamicPartInBlock;
    }
}
