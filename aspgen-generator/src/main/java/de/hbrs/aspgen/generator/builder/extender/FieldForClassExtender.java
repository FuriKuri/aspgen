package de.hbrs.aspgen.generator.builder.extender;

import de.hbrs.aspgen.api.generator.FieldForClassGenerator;
import de.hbrs.aspgen.generator.builder.FieldForClassBuilder;

public class FieldForClassExtender extends AbstractExtender implements Extender {

    @Override
    public void extend(final ExtendDataContainer dataContainer) {
        if (annotationsContainsGeneratorAnnotation(dataContainer.getJavaClass().getAnnotations(), dataContainer.getGenerator())) {
            final String id = dataContainer.getNextIdForClass(dataContainer.getJavaClass());
            final FieldForClassBuilder builder = new FieldForClassBuilder(dataContainer.getJavaClass());
            ((FieldForClassGenerator) dataContainer.getGenerator()).extendJavaClass(builder, annotationsProperties(dataContainer.getJavaClass().getAnnotations(), dataContainer.getGenerator()));
            dataContainer.appendJavaContent(builder.createContent(id, annotationsProperties(dataContainer.getJavaClass().getAnnotations(), dataContainer.getGenerator()), getDeletedBlockNames(dataContainer.getJavaClass().getAnnotations(), dataContainer.getGenerator())));
            dataContainer.appendJavaContent("\n\n");
            dataContainer.addImports(builder.getAllImports());
        }
    }

}
