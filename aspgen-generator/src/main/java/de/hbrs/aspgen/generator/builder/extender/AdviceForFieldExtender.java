package de.hbrs.aspgen.generator.builder.extender;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.generator.AdviceForFieldGenerator;
import de.hbrs.aspgen.generator.builder.AdviceForFieldBuilder;

public class AdviceForFieldExtender extends AbstractExtender implements Extender {

    @Override
    public void extend(final ExtendDataContainer dataContainer) {
        for (final JavaField javaField : dataContainer.getJavaClass().getFields()) {
            if (annotationsContainsGeneratorAnnotation(javaField.getAnnotations(), dataContainer.getGenerator())) {
                final String id = dataContainer.getIdForBlock(javaField);
                final AdviceForFieldBuilder builder = new AdviceForFieldBuilder(dataContainer.getJavaClass(), javaField);
                ((AdviceForFieldGenerator) dataContainer.getGenerator()).extendJavaClass(
                        builder, annotationsProperties(javaField.getAnnotations(), dataContainer.getGenerator()));
                dataContainer.appendJavaContent(builder.createContent(
                        id,
                        annotationsProperties(javaField.getAnnotations(), dataContainer.getGenerator()),
                        getDeletedBlockNames(javaField.getAnnotations(), dataContainer.getGenerator())));
                dataContainer.appendJavaContent("\n\n");

                dataContainer.addImports(builder.getAllImports());
            }
        }
    }

}
