package de.hbrs.aspgen.generator.builder.extender;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.generator.MethodForFieldGenerator;
import de.hbrs.aspgen.generator.builder.MethodForFieldBuilder;

public class MethodForFieldExtender extends AbstractExtender implements Extender {

    @Override
    public void extend(final ExtendDataContainer dataContainer) {
        if (annotationsContainsGeneratorAnnotation(dataContainer.getJavaClass().getAnnotations(), dataContainer.getGenerator())) {
            for (final JavaField javaField : dataContainer.getJavaClass().getFields()) {
                final String id = dataContainer.getIdForBlock(javaField);
                final MethodForFieldBuilder builder = new MethodForFieldBuilder(dataContainer.getJavaClass(), javaField);
                ((MethodForFieldGenerator) dataContainer.getGenerator()).extendJavaClass(builder, annotationsProperties(dataContainer.getJavaClass().getAnnotations(), dataContainer.getGenerator()));
                dataContainer.appendJavaContent(builder.createContent(id, annotationsProperties(dataContainer.getJavaClass().getAnnotations(), dataContainer.getGenerator()), getDeletedBlockNames(javaField.getAnnotations(), dataContainer.getGenerator())));
                dataContainer.appendJavaContent("\n\n");
                dataContainer.addImports(builder.getAllImports());
            }
        } else {
            for (final JavaField javaField : dataContainer.getJavaClass().getFields()) {
                if (annotationsContainsGeneratorAnnotation(javaField.getAnnotations(), dataContainer.getGenerator())) {
                    final String id = dataContainer.getIdForBlock(javaField);
                    final MethodForFieldBuilder builder = new MethodForFieldBuilder(dataContainer.getJavaClass(), javaField);
                    ((MethodForFieldGenerator) dataContainer.getGenerator()).extendJavaClass(builder, annotationsProperties(javaField.getAnnotations(), dataContainer.getGenerator()));
                    dataContainer.appendJavaContent(builder.createContent(id, annotationsProperties(javaField.getAnnotations(), dataContainer.getGenerator()), getDeletedBlockNames(javaField.getAnnotations(), dataContainer.getGenerator())));
                    dataContainer.appendJavaContent("\n\n");
                    dataContainer.addImports(builder.getAllImports());
                }
            }
        }
    }

}
