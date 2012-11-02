package de.hbrs.aspgen.merger.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJITDMethod;
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

public class AspectJMethodMerger implements Merger {
    private final AspectJParser ajParser;
    private final GeneratorManager generatorManager;
    private final JavaFactory javaFactory;

    @Inject
    public AspectJMethodMerger(final AspectJParser ajParser, final GeneratorManager generatorManager, final JavaFactory javaFactory) {
        this.ajParser = ajParser;
        this.generatorManager = generatorManager;
        this.javaFactory = javaFactory;
    }

    @Override
    public String mergeContent(final AspectJUnit oldUnit, final String oldContent, final AspectJUnit newUnit,
            final GeneratorData data) {
        final List<AspectJITDMethod> methodsToUpdate = getMethodsToUpdate(data, oldUnit);
        Collections.sort(methodsToUpdate, AspectJBlockComperator.INSTANCE);
        final StringBuffer newUnitContent = new StringBuffer(oldContent);
        for (final AspectJITDMethod methodToUpdate : methodsToUpdate) {
            final AspectJITDMethod newMethod = getMethodFromUnit(methodToUpdate, newUnit);
            if (propertiesEqualsForMethod(methodToUpdate, newMethod)) {
                final String newMethodContent = mergeMethods(methodToUpdate, newMethod, oldContent, data);

                newUnitContent.delete(methodToUpdate.getStartPosition(), methodToUpdate.getEndPosition());
                newUnitContent.insert(methodToUpdate.getStartPosition(), newMethodContent);
            }
        }
        return newUnitContent.toString();
    }

    private String mergeMethods(final AspectJITDMethod methodsToUpdate,
            final AspectJITDMethod newMethod, final String oldContent, final GeneratorData data) {
        if (methodsToUpdate.getAnnotationId().equals("1")) {
            final JavaClass javaClass = javaFactory.buildJavaClass(methodsToUpdate, data);


            final List<DynamicPartsInBlocks> dymanicParts = generatorManager.getDynamicParts(javaClass, data.getAnnotation());
            final DynamicPartsInBlocks blocksForClassId = getBlockForClass(dymanicParts);
            final String blockContent = oldContent.substring(methodsToUpdate.getStartPosition(), methodsToUpdate.getEndPosition());
            final List<JavaField> excludedJavaField = getExcludedJavaFields(blocksForClassId, blockContent, methodsToUpdate.getAnnotationName());
            final JavaClass javaClassWithExcludedFields = javaFactory.buildJavaClass(methodsToUpdate, data, excludedJavaField);
            final String orginalContentWithExcludedFields = generatorManager.generateContentForGenerator(javaClassWithExcludedFields, data.getAnnotation());

            final AspectJUnit orginalUnit = ajParser.parse(orginalContentWithExcludedFields);
            final AspectJITDMethod orginalMethod = getMethodFromUnit(methodsToUpdate, orginalUnit);

            final String oldMethodContent = oldContent.substring(methodsToUpdate.getStartPosition(), methodsToUpdate.getEndPosition());
            final StringBuffer newMethodContent = new StringBuffer(oldMethodContent);

            final MethodDiffImpl diff = makeDiff(orginalMethod, methodsToUpdate);
            if (!diff.isBlock()) {
                final int offset = -1 * methodsToUpdate.getStartPosition();
                newMethodContent.delete(methodsToUpdate.getBlock().getStart() + offset,
                        methodsToUpdate.getBlock().getEnd() + offset);
                newMethodContent.insert(methodsToUpdate.getBlock().getStart() + offset, newMethod.getBlock().getContent());
            }

            updateAnnotationData(newMethodContent, methodsToUpdate, newMethod);


            return newMethodContent.toString();
        } else {
            final JavaClass javaClass = javaFactory.buildJavaClassForField(methodsToUpdate, data);
            final String orginalContent = generatorManager.generateContentForGenerator(javaClass, data.getAnnotation());
            final AspectJUnit orginalUnit = ajParser.parse(orginalContent);
            final AspectJITDMethod orginalMethod = getMethodFromUnit(methodsToUpdate, orginalUnit);

            final String oldMethodContent = oldContent.substring(methodsToUpdate.getStartPosition(), methodsToUpdate.getEndPosition());
            final StringBuffer newMethodContent = new StringBuffer(oldMethodContent);

            final MethodDiffImpl diff = makeDiff(orginalMethod, methodsToUpdate);
            if (!diff.isBlock() && !diff.isParameter() && !diff.isType()) {
                final int offset = -1 * methodsToUpdate.getStartPosition();
                newMethodContent.delete(methodsToUpdate.getBlock().getStart() + offset,
                        methodsToUpdate.getBlock().getEnd() + offset);
                newMethodContent.insert(methodsToUpdate.getBlock().getStart() + offset, newMethod.getBlock().getContent());
            }
            if (!diff.isBlock() && !diff.isParameter() && !diff.isType()) {
                final int offset = -1 * methodsToUpdate.getStartPosition();
                newMethodContent.delete(methodsToUpdate.getParameters().getStart() + offset,
                        methodsToUpdate.getParameters().getEnd() + offset);
                newMethodContent.insert(methodsToUpdate.getParameters().getStart() + offset, newMethod.getParameters().getContent());
            }
            if (!diff.isName()) {
                final int offset = -1 * methodsToUpdate.getStartPosition();
                newMethodContent.delete(methodsToUpdate.getOnType().getStart() + offset,
                        methodsToUpdate.getOnType().getEnd() + offset);
                newMethodContent.insert(methodsToUpdate.getOnType().getStart() + offset, newMethod.getOnType().getContent());
            }
            if (!diff.isBlock() && !diff.isParameter() && !diff.isType()) {
                final int offset = -1 * methodsToUpdate.getStartPosition();
                newMethodContent.delete(methodsToUpdate.getType().getStart() + offset,
                        methodsToUpdate.getType().getEnd() + offset);
                newMethodContent.insert(methodsToUpdate.getType().getStart() + offset, newMethod.getType().getContent());
            }

            updateAnnotationData(newMethodContent, methodsToUpdate, newMethod);

            return newMethodContent.toString();
        }

    }

    private List<AspectJITDMethod> getMethodsToUpdate(final GeneratorData data,
            final AspectJUnit oldUnit) {
        final List<AspectJITDMethod> methodsToUpdate = new LinkedList<>();

        for (final AspectJITDMethod method : oldUnit.getItdMethods()) {
            if (data.containsIdWithChangedName(method.getAnnotationId(), method.getAnnotationName())) {
                methodsToUpdate.add(method);
            }
        }

        return methodsToUpdate;
    }

    private AspectJITDMethod getMethodFromUnit(final AspectJITDMethod methodToUpdate, final AspectJUnit newUnit) {
        for (final AspectJITDMethod methodFromNewUnit : newUnit.getItdMethods()) {
            if (methodFromNewUnit.getAnnotationId().equals(methodToUpdate.getAnnotationId())
                    && methodFromNewUnit.getAnnotationName().equals(methodToUpdate.getAnnotationName())) {
                return methodFromNewUnit;
            }
        }
        return null;
    }

    private boolean propertiesEqualsForMethod(final AspectJITDMethod methodToUpdate,
            final AspectJITDMethod newMethod) {
        return propertiesEquals(methodToUpdate, newMethod, 1);
    }

    private boolean propertiesEquals(final AspectJBlock adviceToUpdate,
            final AspectJBlock newAdvice, final int position) {
        if (adviceToUpdate.getAnnotationData().split(";").length > position && newAdvice.getAnnotationData().split(";").length > position) {
            return adviceToUpdate.getAnnotationData().split(";")[position].equals(newAdvice.getAnnotationData().split(";")[position]);
        } else {
            return true;
        }
    }

    private DynamicPartsInBlocks getBlockForClass(final List<DynamicPartsInBlocks> dymanicParts) {
        for (final DynamicPartsInBlocks dymanicPart : dymanicParts) {
            if (dymanicPart.getId().equals("1")) {
                return dymanicPart;
            }
        }
        return null;
    }

    private void updateAnnotationData(final StringBuffer content, final AspectJBlock blockToUpdate, final AspectJBlock newBlock) {
        final String data = blockToUpdate.getAnnotationData().replace("\"", "\\\"");
        final int annoData = content.indexOf(data);

        content.replace(annoData, annoData + data.length(), newBlock.getAnnotationData().replace("\"", "\\\""));
    }

    private MethodDiffImpl makeDiff(final AspectJITDMethod orginalMethod, final AspectJITDMethod methodsToUpdate) {
        final MethodDiffImpl diff = new MethodDiffImpl();
        if (!orginalMethod.getBlock().getContent().equals(methodsToUpdate.getBlock().getContent())) {
            diff.setBlock(true);
        }
        if (!orginalMethod.getType().getContent().equals(methodsToUpdate.getType().getContent())) {
            diff.setType(true);
        }
        if (!orginalMethod.getParameters().getContent().equals(methodsToUpdate.getParameters().getContent())) {
            diff.setParameter(true);
        }
        if (!orginalMethod.getOnType().getContent().equals(methodsToUpdate.getOnType().getContent())) {
            diff.setName(true);
        }
        return diff;
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
}
