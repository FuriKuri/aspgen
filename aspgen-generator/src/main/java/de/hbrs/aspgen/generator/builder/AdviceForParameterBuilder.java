package de.hbrs.aspgen.generator.builder;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.generator.AdviceForParameter;
import de.hbrs.aspgen.api.generator.ExtendParameterWithAdvices;
import de.hbrs.aspgen.generator.container.AdviceForParameterContainer;
import de.hbrs.aspgen.generator.container.AfterAdviceForParameterContainer;
import de.hbrs.aspgen.generator.container.AroundAdviceForParameterContainer;
import de.hbrs.aspgen.generator.container.BeforeAdviceForParameterContainer;

public class AdviceForParameterBuilder extends AbstractBuilder implements ExtendParameterWithAdvices {

    private final JavaClass javaClass;
    private final JavaMethod javaMethod;
    private final JavaParameter javaParameter;


    public AdviceForParameterBuilder(final JavaClass javaClass, final JavaMethod javaMethod, final JavaParameter javaParameter) {
        this.javaClass = javaClass;
        this.javaMethod = javaMethod;
        this.javaParameter = javaParameter;
    }


    @Override
    public AdviceForParameter appendNewBeforeAdviceForParameter(final String name) {
        final BeforeAdviceForParameterContainer container = new BeforeAdviceForParameterContainer();
        return fillContainer(name, container);
    }


    private AdviceForParameter fillContainer(final String name,
            final AdviceForParameterContainer container) {
        container.setOnType(javaClass.getClassName());
        container.setMethod(javaMethod);
        container.setParameter(javaParameter);
        container.setName(name);
        if (javaClass.isInterface()) {
            container.isInterface();
        }
        blocks.add(container);
        return container;
    }


    @Override
    public AdviceForParameter appendNewAroundAdviceForParameter(final String name) {
        final AroundAdviceForParameterContainer container = new AroundAdviceForParameterContainer();
        return fillContainer(name, container);
    }


    @Override
    public AdviceForParameter appendNewAfterAdviceForParameter(final String name) {
        final AfterAdviceForParameterContainer container = new AfterAdviceForParameterContainer();
        return fillContainer(name, container);
    }
}
