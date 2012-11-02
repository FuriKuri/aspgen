package de.hbrs.aspgen.generator.builder.extender;

import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.generator.FieldForMethodGenerator;
import de.hbrs.aspgen.generator.builder.FieldForMethodBuilder;

public class FieldForMethodExtender extends AbstractExtender implements Extender {

    @Override
    public void extend(final ExtendDataContainer dataContainer) {
        if (annotationsContainsGeneratorAnnotation(dataContainer.getJavaClass().getAnnotations(), dataContainer.getGenerator())) {
            for (final JavaMethod javaMethod : dataContainer.getJavaClass().getMethods()) {
                final String id = dataContainer.getIdForBlock(javaMethod);
                final FieldForMethodBuilder builder = new FieldForMethodBuilder(dataContainer.getJavaClass(), javaMethod);
                ((FieldForMethodGenerator) dataContainer.getGenerator()).extendJavaClass(builder, annotationsProperties(dataContainer.getJavaClass().getAnnotations(), dataContainer.getGenerator()));
                dataContainer.appendJavaContent(builder.createContent(id, annotationsProperties(dataContainer.getJavaClass().getAnnotations(), dataContainer.getGenerator()), getDeletedBlockNames(javaMethod.getAnnotations(), dataContainer.getGenerator())));
                dataContainer.appendJavaContent("\n\n");
                dataContainer.addImports(builder.getAllImports());
            }
        } else {
            for (final JavaMethod javaMethod : dataContainer.getJavaClass().getMethods()) {
                if (annotationsContainsGeneratorAnnotation(javaMethod.getAnnotations(), dataContainer.getGenerator())) {
                    final String id = dataContainer.getIdForBlock(javaMethod);
                    final FieldForMethodBuilder builder = new FieldForMethodBuilder(dataContainer.getJavaClass(), javaMethod);
                    ((FieldForMethodGenerator) dataContainer.getGenerator()).extendJavaClass(builder, annotationsProperties(javaMethod.getAnnotations(), dataContainer.getGenerator()));
                    dataContainer.appendJavaContent(builder.createContent(id, annotationsProperties(javaMethod.getAnnotations(), dataContainer.getGenerator()), getDeletedBlockNames(javaMethod.getAnnotations(), dataContainer.getGenerator())));
                    dataContainer.appendJavaContent("\n\n");
                    dataContainer.addImports(builder.getAllImports());
                }
            }
        }
    }

}
