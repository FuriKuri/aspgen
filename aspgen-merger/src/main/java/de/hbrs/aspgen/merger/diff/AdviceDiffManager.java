package de.hbrs.aspgen.merger.diff;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.AspectJAdvice;
import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.api.generator.DynamicPartsInBlocks;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.merge.GeneratorData;
import de.hbrs.aspgen.api.parser.AspectJParser;

public class AdviceDiffManager extends AbstracteDiffManager {
    private final AspectJParser ajParser;
    private final GeneratorManager generatorManager;
    private final JavaFactory javaFactory;
    private final AspectJDiffFiller aspectJDiffFiller;

    @Inject
    public AdviceDiffManager(final AspectJParser ajParser, final GeneratorManager generatorManager,
            final JavaFactory javaFactory) {
        this.ajParser = ajParser;
        this.generatorManager = generatorManager;
        this.javaFactory = javaFactory;
        aspectJDiffFiller = new AspectJDiffFiller();
    }

    @Override
    public void updateDiff(final AspectJUnit aspectJUnit, final String content,
            final AspectJDiffImpl diff, final GeneratorData data) {
        for (final AspectJAdvice advice : aspectJUnit.getAdvices()) {
            final Pattern p = Pattern.compile("\\w+:\\w+;\\w+:\\w+:\\w+;(\\w+:\\w+(,\\w+:\\w+)*)?;.*");
            final Matcher m = p.matcher(advice.getAnnotationData());

            final Pattern p2 = Pattern.compile("\\w+:\\w+:\\w+;(\\w+:\\w+(,\\w+:\\w+)*)?;.*");
            final Matcher m2 = p2.matcher(advice.getAnnotationData());

            final Pattern p3 = Pattern.compile("\\w+:\\w+;.*");
            final Matcher m3 = p3.matcher(advice.getAnnotationData());

            if (m.find()) {
                final JavaClass javaClass = javaFactory.buildJavaClasFromAdviceForParameter(advice, data);
                final String orginalContent = generatorManager.generateContentForGenerator(javaClass, data.getAnnotation());

                final AspectJUnit orginalUnit = ajParser.parse(orginalContent);
                final AspectJBlock orginalAdvice = getBlockFromUnit(advice, orginalUnit);
                final String actualAdviceBlock = content.substring(advice.getStartPosition(), advice.getEndPosition())
                        .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");
                final String orginalAdviceBlock = orginalContent.substring(orginalAdvice.getStartPosition(), orginalAdvice.getEndPosition())
                        .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");
                if (!actualAdviceBlock.equals(orginalAdviceBlock)) {
                    final JavaMethod javaMethod = javaClass.getMethods().get(0);
                    final JavaParameter javaParameter = javaFactory.buildParameter(advice.getAnnotationData());

                    if (diff.getAspectJParameterDiff(javaParameter, javaMethod) == null) {
                        final AspectJParameterDiffImpl parameterDiff = new AspectJParameterDiffImpl();
                        final AnnotationData annotationData = new AnnotationData(advice.getAnnotationId());
                        parameterDiff.setAnnotationData(annotationData);
                        parameterDiff.setMethod(javaMethod);
                        parameterDiff.setParameter(javaParameter);
                        diff.addAspectJParameterDiffs(parameterDiff);
                    }
                    if (!diff.getAspectJParameterDiff(javaParameter, javaMethod).getAnnotationData().getId().matches("\\d+")) {
                        if (advice.getAnnotationId().matches("\\d+")) {
                            diff.getAspectJParameterDiff(javaParameter, javaMethod).getAnnotationData().updateId(advice.getAnnotationId());
                        }
                    }
                    diff.getAspectJParameterDiff(javaParameter, javaMethod).getAnnotationData().addModified(advice.getAnnotationName());
                }
            } else if (m2.find()) {
                final JavaClass javaClass = javaFactory.buildJavaClassFromAdviceForMethod(advice, data);
                final List<DynamicPartsInBlocks> dymanicParts = generatorManager.getDynamicParts(javaClass, data.getAnnotation(), advice.getAnnotationId());
                final DynamicPartsInBlocks blocksForClassId = getBlockForMethod(dymanicParts, advice.getAnnotationId());
                final String blockContent = content.substring(advice.getStartPosition(), advice.getEndPosition());
                final List<JavaParameter> excludedParameters = getExcludedJavaParameters(blocksForClassId, blockContent, advice.getAnnotationName());
                final JavaClass javaClassWithExcludedParameters = javaFactory.buildJavaClassFromAdviceForMethod(advice, data, excludedParameters);
                final String orginalContent = generatorManager.generateContentForGenerator(javaClassWithExcludedParameters, data.getAnnotation());
                final AspectJUnit orginalUnit = ajParser.parse(orginalContent);
                final AspectJBlock orginalAdvice = getBlockFromUnit(advice, orginalUnit);

                final String actualAdviceBlock = content.substring(advice.getStartPosition(), advice.getEndPosition())
                        .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");
                final String orginalAdviceBlock = orginalContent.substring(orginalAdvice.getStartPosition(), orginalAdvice.getEndPosition())
                        .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");

                if (!actualAdviceBlock.equals(orginalAdviceBlock)) {
                    final JavaMethod javaMethod = javaClass.getMethods().get(0);
                    aspectJDiffFiller.addNewMethodDiff(diff, advice, javaMethod);
                    diff.getAspectJMethodDiff(javaMethod).getAnnotationData().addModified(advice.getAnnotationName());

                } else if (actualAdviceBlock.equals(orginalAdviceBlock) && excludedParameters.size() > 0) {
                    final JavaMethod javaMethod = javaClass.getMethods().get(0);
                    for (final JavaParameter javaParameter : excludedParameters) {
                        aspectJDiffFiller.addNewParameterDiff(diff, advice, javaMethod,
                                javaParameter);
                        diff.getAspectJParameterDiff(javaParameter, javaMethod).getAnnotationData().addExcluded(advice.getAnnotationName());
                    }
                }
            } else if (m3.find()) {
                final JavaClass javaClass = javaFactory.buildJavaClasFromAdviceForField(advice, data);
                final String orginalContent = generatorManager.generateContentForGenerator(javaClass, data.getAnnotation());

                final AspectJUnit orginalUnit = ajParser.parse(orginalContent);
                final AspectJBlock orginalAdvice = getBlockFromUnit(advice, orginalUnit);
                final String actualAdviceBlock = content.substring(advice.getStartPosition(), advice.getEndPosition())
                        .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");
                final String orginalAdviceBlock = orginalContent.substring(orginalAdvice.getStartPosition(), orginalAdvice.getEndPosition())
                        .replace("\n", "").replace("\r", "").replaceAll("\\s+", " ");
                if (!actualAdviceBlock.equals(orginalAdviceBlock)) {
                    final JavaField field = javaClass.getFields().get(0);
                    aspectJDiffFiller.addNewFieldDiff(diff, advice, field);
                    diff.getAspectJFieldDiff(field).getAnnotationData().addModified(advice.getAnnotationName());
                }
            } else {
                throw new RuntimeException("Error while match to advice type");
            }
        }
    }
}
