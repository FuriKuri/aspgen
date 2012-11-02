package de.hbrs.aspgen.merger.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJITDConstructor;
import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.generator.Block;
import de.hbrs.aspgen.api.generator.DynamicPart;
import de.hbrs.aspgen.api.generator.DynamicPartsInBlocks;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.merge.GeneratorData;
import de.hbrs.aspgen.api.parser.AspectJParser;
import de.hbrs.aspgen.merger.diff.MethodDiffImpl;

public class AspectJConstructorMerger implements Merger {
    private final AspectJParser ajParser;
    private final GeneratorManager generatorManager;
    private final JavaFactory javaFactory;

    @Inject
    public AspectJConstructorMerger(final AspectJParser ajParser, final GeneratorManager generatorManager, final JavaFactory javaFactory) {
        this.ajParser = ajParser;
        this.generatorManager = generatorManager;
        this.javaFactory = javaFactory;
    }

    @Override
    public String mergeContent(final AspectJUnit updatedUnit,
            final String updatedMethodContent, final AspectJUnit newUnit, final GeneratorData data) {
        final List<AspectJITDConstructor> consToUpdate = getConsToUpdate(data, updatedUnit);
        Collections.sort(consToUpdate, AspectJBlockComperator.INSTANCE);
        final StringBuffer newUnitContent = new StringBuffer(updatedMethodContent);

        for (final AspectJITDConstructor conToUpdate : consToUpdate) {
            final AspectJITDConstructor newMethod = getConsFromUnit(conToUpdate, newUnit);
            if (propertiesEqualsForCons(conToUpdate, newMethod)) {
                final String newMethodContent = mergeCons(conToUpdate, newMethod, updatedMethodContent, data);

                newUnitContent.delete(conToUpdate.getStartPosition(), conToUpdate.getEndPosition());
                newUnitContent.insert(conToUpdate.getStartPosition(), newMethodContent);
            }
        }
        return newUnitContent.toString();
    }

    private List<AspectJITDConstructor> getConsToUpdate(final GeneratorData data, final AspectJUnit updatedUnit) {
        final List<AspectJITDConstructor> consToUpdate = new LinkedList<>();
        for (final AspectJITDConstructor cons : updatedUnit.getItdConstructors()) {
            if (data.containsIdWithChangedName(cons.getAnnotationId(), cons.getAnnotationName())) {
                consToUpdate.add(cons);
            }
        }
        return consToUpdate;
    }

    private AspectJITDConstructor getConsFromUnit(final AspectJITDConstructor conToUpdate,
            final AspectJUnit newUnit) {
        for (final AspectJITDConstructor methodFromNewUnit : newUnit.getItdConstructors()) {
            if (methodFromNewUnit.getAnnotationId().equals(conToUpdate.getAnnotationId())
                    && methodFromNewUnit.getAnnotationName().equals(conToUpdate.getAnnotationName())) {
                return methodFromNewUnit;
            }
        }
        return null;
    }

    private boolean propertiesEqualsForCons(final AspectJITDConstructor conToUpdate,
            final AspectJITDConstructor newCons) {
        return propertiesEquals(conToUpdate, newCons, 1);
    }

    private boolean propertiesEquals(final AspectJBlock adviceToUpdate,
            final AspectJBlock newAdvice, final int position) {
        if (adviceToUpdate.getAnnotationData().split(";").length > position && newAdvice.getAnnotationData().split(";").length > position) {
            return adviceToUpdate.getAnnotationData().split(";")[position].equals(newAdvice.getAnnotationData().split(";")[position]);
        } else {
            return true;
        }
    }

    private String mergeCons(final AspectJITDConstructor conToUpdate, final AspectJITDConstructor newMethod, final String updatedMethodContent,
            final GeneratorData data) {
        final JavaClass javaClass = javaFactory.buildJavaClass(conToUpdate, data);
        final List<DynamicPartsInBlocks> dymanicParts = generatorManager.getDynamicParts(javaClass, data.getAnnotation());
        final DynamicPartsInBlocks blocksForClassId = getBlockForClass(dymanicParts);
        final List<JavaField> excludedJavaField = getExcludedJavaFields(blocksForClassId, updatedMethodContent, conToUpdate.getAnnotationName());
        final JavaClass javaClassWithExcludedFields = javaFactory.buildJavaClass(conToUpdate, data, excludedJavaField);
        final String orginalContentWithExcludedFields = generatorManager.generateContentForGenerator(javaClassWithExcludedFields, data.getAnnotation());

//        final String orginalContent = generatorManager.generateContentForGenerator(javaClass, data.getAnnotation());
        final AspectJUnit orginalUnit = ajParser.parse(orginalContentWithExcludedFields);
        final AspectJITDConstructor orginalMethod = getConsFromUnit(conToUpdate, orginalUnit);
//
        final String oldMethodContent = updatedMethodContent.substring(conToUpdate.getStartPosition(), conToUpdate.getEndPosition());
        final StringBuffer newMethodContent = new StringBuffer(oldMethodContent);
//
        final MethodDiffImpl diff = makeDiff(orginalMethod, conToUpdate);
        if (!diff.isBlock() && !diff.isParameter()) {
            final int offset = -1 * conToUpdate.getStartPosition();
            newMethodContent.delete(conToUpdate.getBlock().getStart() + offset,
                    conToUpdate.getBlock().getEnd() + offset);
            newMethodContent.insert(conToUpdate.getBlock().getStart() + offset, newMethod.getBlock().getContent());
        }
        if (!diff.isBlock() && !diff.isParameter()) {
            final int offset = -1 * conToUpdate.getStartPosition();
            newMethodContent.delete(conToUpdate.getParameters().getStart() + offset,
                    conToUpdate.getParameters().getEnd() + offset);
            newMethodContent.insert(conToUpdate.getParameters().getStart() + offset, newMethod.getParameters().getContent());
        }
//
//        final int annoData = newMethodContent.indexOf(conToUpdate.getAnnotationData());
//        newMethodContent.replace(annoData, annoData + conToUpdate.getAnnotationData().length(), newMethod.getAnnotationData());
        updateAnnotationData(newMethodContent, conToUpdate, newMethod);

        return newMethodContent.toString();
    }

    private DynamicPartsInBlocks getBlockForClass(final List<DynamicPartsInBlocks> dymanicParts) {
        for (final DynamicPartsInBlocks dymanicPart : dymanicParts) {
            if (dymanicPart.getId().equals("1")) {
                return dymanicPart;
            }
        }
        return null;
    }

    private List<JavaField> getExcludedJavaFields(final DynamicPartsInBlocks blocksForClassId, final String orginalContent, final String blockName) {
        final List<JavaField> excludedJavaFields = new LinkedList<>();
        if (blocksForClassId == null) {
            return excludedJavaFields;
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
                excludedJavaFields.add(dynamicPart.getField());
            }
        }

        return excludedJavaFields;
    }

    private MethodDiffImpl makeDiff(final AspectJITDConstructor orginalMethod,
            final AspectJITDConstructor conToUpdate) {
        final MethodDiffImpl diff = new MethodDiffImpl();
        if (!orginalMethod.getBlock().getContent().equals(conToUpdate.getBlock().getContent())) {
            diff.setBlock(true);
        }
        if (!orginalMethod.getParameters().getContent().equals(conToUpdate.getParameters().getContent())) {
            diff.setParameter(true);
        }
        if (!orginalMethod.getOnType().getContent().equals(conToUpdate.getOnType().getContent())) {
            diff.setName(true);
        }
        return diff;
    }

    private void updateAnnotationData(final StringBuffer content, final AspectJBlock blockToUpdate, final AspectJBlock newBlock) {
        final String data = blockToUpdate.getAnnotationData().replace("\"", "\\\"");
        final int annoData = content.indexOf(data);

        content.replace(annoData, annoData + data.length(), newBlock.getAnnotationData().replace("\"", "\\\""));
    }
}
