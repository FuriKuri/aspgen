package de.hbrs.aspgen.merger.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.AspectJAdvice;
import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.generator.Block;
import de.hbrs.aspgen.api.generator.DynamicPart;
import de.hbrs.aspgen.api.generator.DynamicPartsInBlocks;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.merge.GeneratorData;
import de.hbrs.aspgen.api.parser.AspectJParser;
import de.hbrs.aspgen.merger.diff.AdviceDiffImpl;

public class AspectJAdviceMerger implements Merger {
    private final AspectJParser ajParser;
    private final GeneratorManager generatorManager;
    private final JavaFactory javaFactory;

    @Inject
    public AspectJAdviceMerger(final AspectJParser ajParser, final GeneratorManager generatorManager, final JavaFactory javaFactory) {
        this.ajParser = ajParser;
        this.generatorManager = generatorManager;
        this.javaFactory = javaFactory;
    }

    @Override
    public String mergeContent(final AspectJUnit updatedUnit,
            final String updatedConsContent, final AspectJUnit newUnit, final GeneratorData data) {
        final List<AspectJAdvice> advicesToUpdate = getAdvicesToUpdate(data, updatedUnit);
        Collections.sort(advicesToUpdate, AspectJBlockComperator.INSTANCE);
        final StringBuffer newUnitContent = new StringBuffer(updatedConsContent);
        for (final AspectJAdvice adviceToUpdate : advicesToUpdate) {
            final AspectJAdvice newAdvice = getAdviceFromUnit(adviceToUpdate, newUnit);
            if (propertiesEqualsForAdvice(adviceToUpdate, newAdvice)) {
                final String newMethodContent = mergeAdvices(adviceToUpdate, newAdvice, updatedConsContent, data);
                newUnitContent.delete(adviceToUpdate.getStartPosition(), adviceToUpdate.getEndPosition());
                newUnitContent.insert(adviceToUpdate.getStartPosition(), newMethodContent);
            }
        }
        return newUnitContent.toString();
    }

    private AspectJAdvice getAdviceFromUnit(final AspectJAdvice adviceToUpdate,
            final AspectJUnit newUnit) {
        for (final AspectJAdvice methodFromNewUnit : newUnit.getAdvices()) {
            if (methodFromNewUnit.getAnnotationId().equals(adviceToUpdate.getAnnotationId())
                    && methodFromNewUnit.getAnnotationName().equals(adviceToUpdate.getAnnotationName())) {
                return methodFromNewUnit;
            }
        }
        return null;
    }

    private boolean propertiesEqualsForAdvice(final AspectJAdvice adviceToUpdate, final AspectJAdvice newAdvice) {
        final Pattern p = Pattern.compile("\\w+:\\w+;\\w+:\\w+:\\w+;(\\w+:\\w+(,\\w+:\\w+)*)?;.*");
        final Matcher m = p.matcher(adviceToUpdate.getAnnotationData());

        final Pattern p2 = Pattern.compile("\\w+:\\w+:\\w+;(\\w+:\\w+(,\\w+:\\w+)*)?;.*");
        final Matcher m2 = p2.matcher(adviceToUpdate.getAnnotationData());

        final Pattern p3 = Pattern.compile("\\w+:\\w+;.*");
        final Matcher m3 = p3.matcher(adviceToUpdate.getAnnotationData());
        if (m.find()) {
            return propertiesEquals(adviceToUpdate, newAdvice, 3);
        } else if (m2.find()) {
            return propertiesEquals(adviceToUpdate, newAdvice, 2);
        } else if (m3.find()) {
            return propertiesEquals(adviceToUpdate, newAdvice, 1);
        } else {
            throw new RuntimeException("Error while match to advice type");
        }
    }

    private boolean propertiesEquals(final AspectJBlock adviceToUpdate,
            final AspectJBlock newAdvice, final int position) {
        if (adviceToUpdate.getAnnotationData().split(";").length > position && newAdvice.getAnnotationData().split(";").length > position) {
            return adviceToUpdate.getAnnotationData().split(";")[position].equals(newAdvice.getAnnotationData().split(";")[position]);
        } else {
            return true;
        }
    }

    private List<AspectJAdvice> getAdvicesToUpdate(final GeneratorData data,
            final AspectJUnit updatedUnit) {
        final List<AspectJAdvice> advicesToUpdate = new LinkedList<>();
        for (final AspectJAdvice cons : updatedUnit.getAdvices()) {
            if (data.containsIdWithChangedName(cons.getAnnotationId(), cons.getAnnotationName())) {
                advicesToUpdate.add(cons);
            }
        }
        return advicesToUpdate;
    }

    private String mergeAdvices(final AspectJAdvice adviceToUpdate,
            final AspectJAdvice newAdvice, final String updatedConsContent,
            final GeneratorData data) {
        final Pattern p = Pattern.compile("\\w+:\\w+;\\w+:\\w+:\\w+;(\\w+:\\w+(,\\w+:\\w+)*)?;.*");
        final Matcher m = p.matcher(adviceToUpdate.getAnnotationData());

        final Pattern p2 = Pattern.compile("\\w+:\\w+:\\w+;(\\w+:\\w+(,\\w+:\\w+)*)?;.*");
        final Matcher m2 = p2.matcher(adviceToUpdate.getAnnotationData());

        final Pattern p3 = Pattern.compile("\\w+:\\w+;.*");
        final Matcher m3 = p3.matcher(adviceToUpdate.getAnnotationData());

        if (m.find()) {
            final JavaClass javaClass = javaFactory.buildJavaClasFromAdviceForParameter(adviceToUpdate, data);
            final String orginalContent = generatorManager.generateContentForGenerator(javaClass, data.getAnnotation());
            return mergeAdvice(adviceToUpdate, newAdvice, updatedConsContent,
                    orginalContent);
        } else if (m2.find()) {
            final JavaClass javaClass = javaFactory.buildJavaClassFromAdviceForMethod(adviceToUpdate, data);
            final List<DynamicPartsInBlocks> dymanicParts = generatorManager.getDynamicParts(javaClass, data.getAnnotation(), adviceToUpdate.getAnnotationId());
            final DynamicPartsInBlocks blocksForClassId = getBlockForMethod(dymanicParts, adviceToUpdate.getAnnotationId());
            final String blockContent = updatedConsContent.substring(adviceToUpdate.getStartPosition(), adviceToUpdate.getEndPosition());
            final List<JavaParameter> excludedParameters = getExcludedJavaParameters(blocksForClassId, blockContent, adviceToUpdate.getAnnotationName());
            final JavaClass javaClassWithExcludedParameters = javaFactory.buildJavaClassFromAdviceForMethod(adviceToUpdate, data, excludedParameters);
            final String orginalContentWithExcludedParameters = generatorManager.generateContentForGenerator(javaClassWithExcludedParameters, data.getAnnotation());

            return mergeAdvice(adviceToUpdate, newAdvice, updatedConsContent,
                    orginalContentWithExcludedParameters);
        } else if (m3.find()) {
            final JavaClass javaClass = javaFactory.buildJavaClasFromAdviceForField(adviceToUpdate, data);
            final String orginalContent = generatorManager.generateContentForGenerator(javaClass, data.getAnnotation());
            return mergeAdvice(adviceToUpdate, newAdvice, updatedConsContent,
                    orginalContent);
        } else {
            throw new RuntimeException("Error while match to advice type");
        }
    }

    private String mergeAdvice(final AspectJAdvice adviceToUpdate,
            final AspectJAdvice newAdvice, final String updatedConsContent,
            final String orginalContentWithExcludedParameters) {
        final AspectJUnit orginalUnit = ajParser.parse(orginalContentWithExcludedParameters);
        final AspectJAdvice orginalAdvice = getAdviceFromUnit(adviceToUpdate, orginalUnit);

        final String oldMethodContent = updatedConsContent.substring(adviceToUpdate.getStartPosition(), adviceToUpdate.getEndPosition());
        final StringBuffer newMethodContent = new StringBuffer(oldMethodContent);

        final AdviceDiffImpl diff = makeDiff(orginalAdvice, adviceToUpdate);

        final int offset = -1 * adviceToUpdate.getStartPosition();
        if (!diff.isBlock()) {
            newMethodContent.delete(adviceToUpdate.getBlock().getStart() + offset,
                    adviceToUpdate.getBlock().getEnd() + offset);
            newMethodContent.insert(adviceToUpdate.getBlock().getStart() + offset, newAdvice.getBlock().getContent());
        }

        newMethodContent.delete(adviceToUpdate.getPointcut().getStart() + offset,
                adviceToUpdate.getPointcut().getEnd() + offset);
        newMethodContent.insert(adviceToUpdate.getPointcut().getStart() + offset, newAdvice.getPointcut().getContent());

        newMethodContent.delete(adviceToUpdate.getAdviceHead().getStart() + offset,
                adviceToUpdate.getAdviceHead().getEnd() + offset);
        newMethodContent.insert(adviceToUpdate.getAdviceHead().getStart() + offset, newAdvice.getAdviceHead().getContent());
//
//        final String data = adviceToUpdate.getAnnotationData().replace("\"", "\\\"");
//        final int annoData = newMethodContent.indexOf(data);
//
//        newMethodContent.replace(annoData, annoData + data.length(), newAdvice.getAnnotationData().replace("\"", "\\\""));
        updateAnnotationData(newMethodContent, adviceToUpdate, newAdvice);
        return newMethodContent.toString();
    }

    private AdviceDiffImpl makeDiff(final AspectJAdvice orginalAdvice,
            final AspectJAdvice adviceToUpdate) {
        final AdviceDiffImpl diff = new AdviceDiffImpl();
        if (!orginalAdvice.getBlock().getContent().equals(adviceToUpdate.getBlock().getContent())) {
            diff.setBlock(true);
        }
//        if (!orginalAdvice.getAdviceHead().getContent().equals(adviceToUpdate.getAdviceHead().getContent())) {
//
//        }
        return diff;
    }

    private void updateAnnotationData(final StringBuffer content, final AspectJBlock blockToUpdate, final AspectJBlock newBlock) {
        final String data = blockToUpdate.getAnnotationData().replace("\"", "\\\"");
        final int annoData = content.indexOf(data);

        content.replace(annoData, annoData + data.length(), newBlock.getAnnotationData().replace("\"", "\\\""));
    }

    private DynamicPartsInBlocks getBlockForMethod(
            final List<DynamicPartsInBlocks> dymanicParts, final String id) {
        for (final DynamicPartsInBlocks dymanicPart : dymanicParts) {
            if (dymanicPart.getId().equals(id)) {
                return dymanicPart;
            }
        }
        return null;
    }

    private List<JavaParameter> getExcludedJavaParameters(
            final DynamicPartsInBlocks blocksForClassId, final String orginalContent, final String blockName) {
        final List<JavaParameter> excludedJavaParameters = new LinkedList<>();
        if (blocksForClassId == null) {
            return excludedJavaParameters;
        }
        final Block block = blocksForClassId.getBlock(blockName);
        for (final DynamicPart dynamicPart : block.getDynamicParts()) {
            boolean doContainAllLines = true;
            for (final String line : dynamicPart.getDynamicLines()) {
                if (!orginalContent.contains(line)) {
                    doContainAllLines = false;
                }
            }
            if (!doContainAllLines) {
                excludedJavaParameters.add(dynamicPart.getParameter());
            }
        }

        return excludedJavaParameters;
    }
}
