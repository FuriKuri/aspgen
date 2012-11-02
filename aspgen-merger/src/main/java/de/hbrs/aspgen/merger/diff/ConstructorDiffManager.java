package de.hbrs.aspgen.merger.diff;

import java.util.List;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJITDConstructor;
import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.generator.DynamicPartsInBlocks;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.merge.GeneratorData;
import de.hbrs.aspgen.api.parser.AspectJParser;

public class ConstructorDiffManager extends AbstracteDiffManager {
    private final AspectJParser ajParser;
    private final GeneratorManager generatorManager;
    private final JavaFactory javaFactory;
    private final AspectJDiffFiller aspectJDiffFiller;

    @Inject
    public ConstructorDiffManager(final AspectJParser ajParser, final GeneratorManager generatorManager,
            final JavaFactory javaFactory) {
        this.ajParser = ajParser;
        this.generatorManager = generatorManager;
        this.javaFactory = javaFactory;
        aspectJDiffFiller = new AspectJDiffFiller();
    }

    @Override
    public void updateDiff(final AspectJUnit aspectJUnit, final String actualContent,
            final AspectJDiffImpl diff, final GeneratorData data) {
        for (final AspectJITDConstructor cons : aspectJUnit.getItdConstructors()) {
            final JavaClass javaClass = javaFactory.buildJavaClass(cons, data);
            final List<DynamicPartsInBlocks> dymanicParts = generatorManager.getDynamicParts(javaClass, data.getAnnotation());
            final DynamicPartsInBlocks blocksForClassId = getBlockForClass(dymanicParts);

            final AspectJBlock actualCons = getBlockFromUnit(cons, aspectJUnit);
            final String blockContent = actualContent.substring(actualCons.getStartPosition(), actualCons.getEndPosition());

            final List<JavaField> excludedJavaField = getExcludedJavaFields(blocksForClassId, blockContent, cons.getAnnotationName());
            final JavaClass javaClassWithExcludedFields = javaFactory.buildJavaClass(cons, data, excludedJavaField);
            final String orginalContent = generatorManager.generateContentForGenerator(javaClassWithExcludedFields, data.getAnnotation());
            final AspectJUnit orginalUnit = ajParser.parse(orginalContent);
            final AspectJBlock orginalCons = getBlockFromUnit(cons, orginalUnit);
            final String actualConsBlock = actualContent.substring(cons.getStartPosition(), cons.getEndPosition())
                    .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");
            final String orginalConsBlock = orginalContent.substring(orginalCons.getStartPosition(), orginalCons.getEndPosition())
                    .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");

            if (!actualConsBlock.equals(orginalConsBlock)) {
                aspectJDiffFiller.addNewClassDiff(diff, cons);
                diff.getAspectJClassDiff().getAnnotationData().addModified(cons.getAnnotationName());
            } else if (orginalConsBlock.equals(actualConsBlock) && excludedJavaField.size() > 0) {
                for (final JavaField javaField : excludedJavaField) {
                    aspectJDiffFiller.addNewFieldDiff(diff, cons, javaField);
                    diff.getAspectJFieldDiff(javaField).getAnnotationData().addExcluded(cons.getAnnotationName());
                }
            }
        }
    }
}
