package de.hbrs.aspgen.merger.diff;

import java.util.LinkedList;
import java.util.List;

import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.generator.Block;
import de.hbrs.aspgen.api.generator.DynamicPart;
import de.hbrs.aspgen.api.generator.DynamicPartsInBlocks;


public abstract class AbstracteDiffManager implements DiffManager {

    protected DynamicPartsInBlocks getBlockForClass(final List<DynamicPartsInBlocks> dymanicParts) {
        for (final DynamicPartsInBlocks dymanicPart : dymanicParts) {
            if (dymanicPart.getId().equals("1")) {
                return dymanicPart;
            }
        }
        return null;
    }

    protected List<JavaField> getExcludedJavaFields(final DynamicPartsInBlocks blocksForClassId, final String orginalContent, final String blockName) {
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

    protected AspectJBlock getBlockFromUnit(final AspectJBlock methodToUpdate, final AspectJUnit newUnit) {
        for (final AspectJBlock methodFromNewUnit : newUnit.getAllBlocks()) {
            if (methodFromNewUnit.getAnnotationId().equals(methodToUpdate.getAnnotationId())
                    && methodFromNewUnit.getAnnotationName().equals(methodToUpdate.getAnnotationName())) {
                return methodFromNewUnit;
            }
        }
        return null;
    }

    protected DynamicPartsInBlocks getBlockForMethod(
            final List<DynamicPartsInBlocks> dymanicParts, final String id) {
        for (final DynamicPartsInBlocks dymanicPart : dymanicParts) {
            if (dymanicPart.getId().equals(id)) {
                return dymanicPart;
            }
        }
        return null;
    }

    protected List<JavaParameter> getExcludedJavaParameters(
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
