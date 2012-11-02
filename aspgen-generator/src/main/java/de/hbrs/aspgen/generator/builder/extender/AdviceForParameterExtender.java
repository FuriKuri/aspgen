package de.hbrs.aspgen.generator.builder.extender;

import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.generator.AdviceForParameterGenerator;
import de.hbrs.aspgen.generator.builder.AdviceForParameterBuilder;


public class AdviceForParameterExtender extends AbstractExtender implements Extender {
    @Override
    public void extend(final ExtendDataContainer dataContainer) {
        for (final JavaMethod javaMethod : dataContainer.getJavaClass().getMethods()) {
            if (annotationsContainsGeneratorAnnotation(javaMethod.getAnnotations(), dataContainer.getGenerator())) {
                for (final JavaParameter javaParameter : javaMethod.getParameters()) {
                    final String id = dataContainer.getIdForBlock(javaParameter);
                    final AdviceForParameterBuilder builder = new AdviceForParameterBuilder(dataContainer.getJavaClass(), javaMethod, javaParameter);
                    ((AdviceForParameterGenerator) dataContainer.getGenerator()).extendJavaClass(builder, annotationsProperties(javaMethod.getAnnotations(), dataContainer.getGenerator()));
                    dataContainer.appendJavaContent(builder.createContent(id, annotationsProperties(javaMethod.getAnnotations(), dataContainer.getGenerator()), getDeletedBlockNames(javaParameter.getAnnotations(), dataContainer.getGenerator())));
                    dataContainer.appendJavaContent("\n\n");
                    dataContainer.addImports(builder.getAllImports());
                }
            } else {
                for (final JavaParameter javaParameter : javaMethod.getParameters()) {
                    if (annotationsContainsGeneratorAnnotation(javaParameter.getAnnotations(), dataContainer.getGenerator())) {
                        final String id = dataContainer.getIdForBlock(javaParameter);
                        final AdviceForParameterBuilder builder = new AdviceForParameterBuilder(dataContainer.getJavaClass(), javaMethod, javaParameter);
                        ((AdviceForParameterGenerator) dataContainer.getGenerator()).extendJavaClass(builder, annotationsProperties(javaParameter.getAnnotations(), dataContainer.getGenerator()));
                        dataContainer.appendJavaContent(builder.createContent(id, annotationsProperties(javaParameter.getAnnotations(), dataContainer.getGenerator()), getDeletedBlockNames(javaParameter.getAnnotations(), dataContainer.getGenerator())));
                        dataContainer.appendJavaContent("\n\n");
                        dataContainer.addImports(builder.getAllImports());
                    }
                }
            }
        }
    }
}
