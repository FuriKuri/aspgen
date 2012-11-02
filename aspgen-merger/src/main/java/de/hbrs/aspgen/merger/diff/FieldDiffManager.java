package de.hbrs.aspgen.merger.diff;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJITDField;
import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.merge.GeneratorData;
import de.hbrs.aspgen.api.parser.AspectJParser;

public class FieldDiffManager extends AbstracteDiffManager {
    private final AspectJParser ajParser;
    private final GeneratorManager generatorManager;
    private final JavaFactory javaFactory;
    private final AspectJDiffFiller aspectJDiffFiller;

    @Inject
    public FieldDiffManager(final AspectJParser ajParser, final GeneratorManager generatorManager,
            final JavaFactory javaFactory) {
        this.ajParser = ajParser;
        this.generatorManager = generatorManager;
        this.javaFactory = javaFactory;
        aspectJDiffFiller = new AspectJDiffFiller();
    }

    @Override
    public void updateDiff(final AspectJUnit aspectJUnit, final String actualContent,
            final AspectJDiffImpl diff, final GeneratorData data) {
        for (final AspectJITDField field : aspectJUnit.getItdFields()) {
            if (field.getAnnotationId().equals("1")) {
                final JavaClass javaClass = javaFactory.buildJavaClass(data);
                final String orginalContent = generatorManager.generateContentForGenerator(javaClass, data.getAnnotation());
                final AspectJUnit orginalUnit = ajParser.parse(orginalContent);
                final AspectJBlock orginalField = getBlockFromUnit(field, orginalUnit);
                final String actualFieldBlock = actualContent.substring(field.getStartPosition(), field.getEndPosition())
                        .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");
                final String orginalFieldBlock = orginalContent.substring(orginalField.getStartPosition(), orginalField.getEndPosition())
                        .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");

                if (!actualFieldBlock.equals(orginalFieldBlock)) {
                    if (diff.getAspectJClassDiff() == null) {
                        final AspectJClassDiffImpl classDiff = new AspectJClassDiffImpl();
                        final AnnotationData annotationData = new AnnotationData(field.getAnnotationId());
                        classDiff.setAnnotationData(annotationData);
                        diff.setAspectJClassDiff(classDiff);
                    }
                    if (!diff.getAspectJClassDiff().getAnnotationData().getId().matches("\\d+")) {
                        if (field.getAnnotationId().matches("\\d+")) {
                            diff.getAspectJClassDiff().getAnnotationData().updateId(field.getAnnotationId());
                        }
                    }
                    diff.getAspectJClassDiff().getAnnotationData().addModified(field.getAnnotationName());
                }
            } else {
                final JavaClass javaClass = javaFactory.buildJavaClassForMethod(field, data);
                final String orginalContent = generatorManager.generateContentForGenerator(javaClass, data.getAnnotation());
                final AspectJUnit orginalUnit = ajParser.parse(orginalContent);
                final AspectJBlock orginalField = getBlockFromUnit(field, orginalUnit);
                final String actualFieldBlock = actualContent.substring(field.getStartPosition(), field.getEndPosition())
                        .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");
                final String orginalFieldBlock = orginalContent.substring(orginalField.getStartPosition(), orginalField.getEndPosition())
                        .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");

                if (!actualFieldBlock.equals(orginalFieldBlock)) {
                    final JavaMethod javaMethod = javaClass.getMethods().get(0);
                    aspectJDiffFiller.addNewMethodDiff(diff, field, javaMethod);
                    diff.getAspectJMethodDiff(javaMethod).getAnnotationData().addModified(field.getAnnotationName());

                }
            }
        }
    }
}
