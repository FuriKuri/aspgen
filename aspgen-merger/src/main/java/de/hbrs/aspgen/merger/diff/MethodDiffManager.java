package de.hbrs.aspgen.merger.diff;

import java.util.List;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJITDMethod;
import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.generator.DynamicPartsInBlocks;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.merge.GeneratorData;
import de.hbrs.aspgen.api.parser.AspectJParser;

public class MethodDiffManager extends AbstracteDiffManager {
    private final AspectJParser ajParser;
    private final GeneratorManager generatorManager;
    private final JavaFactory javaFactory;
    private final AspectJDiffFiller aspectJDiffFiller;


    @Inject
    public MethodDiffManager(final AspectJParser ajParser, final GeneratorManager generatorManager,
            final JavaFactory javaFactory) {
        this.ajParser = ajParser;
        this.generatorManager = generatorManager;
        this.javaFactory = javaFactory;
        aspectJDiffFiller = new AspectJDiffFiller();
    }

    @Override
    public void updateDiff(final AspectJUnit actualUnit, final String actualContent, final AspectJDiffImpl diff, final GeneratorData data) {
        for (final AspectJITDMethod method : actualUnit.getItdMethods()) {
            if (method.getAnnotationId().equals("1")) {
                final JavaClass javaClass = javaFactory.buildJavaClass(method, data);
                final List<DynamicPartsInBlocks> dymanicParts = generatorManager.getDynamicParts(javaClass, data.getAnnotation());
                final DynamicPartsInBlocks blocksForClassId = getBlockForClass(dymanicParts);

                final AspectJBlock actualMethod = getBlockFromUnit(method, actualUnit);
                final String blockContent = actualContent.substring(actualMethod.getStartPosition(), actualMethod.getEndPosition());

                final List<JavaField> excludedJavaField = getExcludedJavaFields(blocksForClassId, blockContent, method.getAnnotationName());
                final JavaClass javaClassWithExcludedFields = javaFactory.buildJavaClass(method, data, excludedJavaField);
                final String orginalContentWithExcludedFields = generatorManager.generateContentForGenerator(javaClassWithExcludedFields, data.getAnnotation());

                final AspectJUnit actualUnitWithExcludedFields = ajParser.parse(orginalContentWithExcludedFields);
                final AspectJBlock orginalMethodWithExcludedFields = getBlockFromUnit(method, actualUnitWithExcludedFields);

                final String actualMethodBlock = actualContent.substring(method.getStartPosition(), method.getEndPosition())
                        .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");
                final String orginalMethodBlockWithExcludedFields = orginalContentWithExcludedFields.substring(orginalMethodWithExcludedFields.getStartPosition(), orginalMethodWithExcludedFields.getEndPosition())
                        .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");


                if (!actualMethodBlock.equals(orginalMethodBlockWithExcludedFields)) {
                    aspectJDiffFiller.addNewClassDiff(diff, method);
                    diff.getAspectJClassDiff().getAnnotationData().addModified(method.getAnnotationName());
                } else if (orginalMethodBlockWithExcludedFields.equals(actualMethodBlock) && excludedJavaField.size() > 0) {
                    for (final JavaField javaField : excludedJavaField) {
                        aspectJDiffFiller.addNewFieldDiff(diff, method, javaField);
                        diff.getAspectJFieldDiff(javaField).getAnnotationData().addExcluded(method.getAnnotationName());
                    }
                }
            } else {
                final JavaClass javaClass = javaFactory.buildJavaClassForField(method, data);
                final String orginalContent = generatorManager.generateContentForGenerator(javaClass, data.getAnnotation());
                final AspectJUnit actualUnitWithExcludedFields = ajParser.parse(orginalContent);
                final AspectJBlock orginalMethod = getBlockFromUnit(method, actualUnitWithExcludedFields);

                final String actualMethodBlock = actualContent.substring(method.getStartPosition(), method.getEndPosition())
                        .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");
                final String orginalMethodBlock = orginalContent.substring(orginalMethod.getStartPosition(), orginalMethod.getEndPosition())
                        .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");
                if (!actualMethodBlock.equals(orginalMethodBlock)) {
                    final JavaField field = javaClass.getFields().get(0);
                    aspectJDiffFiller.addNewFieldDiff(diff, method, field);
                    diff.getAspectJFieldDiff(field).getAnnotationData().addModified(method.getAnnotationName());
                }
            }
        }
    }
}
