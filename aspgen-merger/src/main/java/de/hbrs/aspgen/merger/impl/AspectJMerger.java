package de.hbrs.aspgen.merger.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.diff.ContentMerger;
import de.hbrs.aspgen.api.file.GeneratedContent;
import de.hbrs.aspgen.api.merge.AdviceMerger;
import de.hbrs.aspgen.api.merge.ConstructorMerger;
import de.hbrs.aspgen.api.merge.DeclareMerger;
import de.hbrs.aspgen.api.merge.FieldMerger;
import de.hbrs.aspgen.api.merge.GeneratorData;
import de.hbrs.aspgen.api.merge.MethodMerger;
import de.hbrs.aspgen.api.parser.AspectJParser;


public class AspectJMerger implements ContentMerger {
    private static final String CLASS_ID = "1";
    private static final String CLASS_ID_PLACEHOLDER = "{newIdClass}";

    private final AspectJParser ajParser;
    private final Merger aspectJMethodMerger;
    private final Merger aspectJConstructorMerger;
    private final Merger aspectJAdviceMerger;
    private final Merger aspectJFieldMerger;
    private final Merger aspectJDeclareMerger;
    private final AspectJImportMerger aspectJImportMerger;

    @Inject
    public AspectJMerger(final AspectJParser ajParser,
            @MethodMerger final Merger aspectJMethodMerger,
            @ConstructorMerger final Merger aspectJConstructorMerger,
            @AdviceMerger final Merger aspectJAdviceMerger,
            @FieldMerger final Merger aspectJFieldMerger,
            @DeclareMerger final Merger aspectJDeclareMerger) {
        this.ajParser = ajParser;

        this.aspectJMethodMerger = aspectJMethodMerger;
        this.aspectJConstructorMerger = aspectJConstructorMerger;
        this.aspectJAdviceMerger = aspectJAdviceMerger;
        this.aspectJFieldMerger = aspectJFieldMerger;
        this.aspectJDeclareMerger = aspectJDeclareMerger;
        aspectJImportMerger = new AspectJImportMerger();
    }

    @Override
    public String mergeFiles(final String oldContent, final String newContent, final GeneratorData data) {
        final AspectJUnit oldUnit = ajParser.parse(oldContent);
        final AspectJUnit newUnit = ajParser.parse(newContent);
        final String updatedMethodContent = aspectJMethodMerger.mergeContent(oldUnit, oldContent, newUnit, data);
        final String updatedConsContent = aspectJConstructorMerger.mergeContent(ajParser.parse(updatedMethodContent), updatedMethodContent, newUnit, data);
        final String updatedAdviceContent = aspectJAdviceMerger.mergeContent(ajParser.parse(updatedConsContent), updatedConsContent, newUnit, data);
        final String updatedFieldContent = aspectJFieldMerger.mergeContent(ajParser.parse(updatedAdviceContent), updatedAdviceContent, newUnit, data);
        final String updatedDeclareContent = aspectJDeclareMerger.mergeContent(ajParser.parse(updatedFieldContent), updatedFieldContent, newUnit, data);

        final String refreshedBlocks = refreshBlocks(ajParser.parse(updatedDeclareContent), updatedDeclareContent, newUnit, newContent, data);

        final AspectJUnit updatedOldUnit = ajParser.parse(refreshedBlocks);
        final List<AspectJBlock> methodsToRemove = getRemovedBlocks(updatedOldUnit, newUnit, data);
        final List<AspectJBlock> methodsToAdd = getNewBlocks(updatedOldUnit, newUnit, data);
        final String addMethods = addBlocks(refreshedBlocks, newContent, methodsToAdd);
        final String removedMethods = removeMissingBlocks(addMethods, methodsToRemove);
        final String updatedImports = aspectJImportMerger.updateImports(removedMethods, ajParser.parse(removedMethods), newUnit);

        return setIds(updatedImports);
    }

    private String refreshBlocks(final AspectJUnit oldUnit, final String oldContent,
            final AspectJUnit newUnit, final String newContent, final GeneratorData data) {
        final StringBuffer refreshedContent = new StringBuffer(oldContent);
        final List<AspectJBlock> allBlock = oldUnit.getAllBlocks();
        Collections.sort(allBlock, AspectJBlockComperator.INSTANCE);
        for (final AspectJBlock blockInOldUnit : allBlock) {
            if (data.containsIdWithChangedName(blockInOldUnit.getAnnotationId(), blockInOldUnit.getAnnotationName())) {
                continue;
            }

            final String oldAnnotationData = blockInOldUnit.getAnnotationData();
            final String oldAnnotationName = blockInOldUnit.getAnnotationName();

            for (final AspectJBlock blockInNewUnit : newUnit.getAllBlocks()) {
                final String newAnnotationData = blockInNewUnit.getAnnotationData();
                final String newAnnotationName = blockInNewUnit.getAnnotationName();

                if (oldAnnotationData.equals(newAnnotationData)
                        && oldAnnotationName.equals(newAnnotationName)) {
                    final String oldBlock = oldContent.substring(blockInOldUnit.getStartPosition(), blockInOldUnit.getEndPosition()).replaceAll("id\\s*=\\s*\\d+", "");
                    final String newBlock = newContent.substring(blockInNewUnit.getStartPosition(), blockInNewUnit.getEndPosition()).replaceAll("id\\s*=\\s*(\\d+|\\{\\w+\\d*\\})", "");
                    if (!oldBlock.equals(newBlock)) {
                        final String blockId = "id = " + blockInOldUnit.getAnnotationId();
                        final String newBlockContent = newContent.substring(blockInNewUnit.getStartPosition(), blockInNewUnit.getEndPosition()).replaceAll("id\\s*=\\s*\\{\\w+\\d*\\}", blockId);
                        refreshedContent.delete(blockInOldUnit.getStartPosition(), blockInOldUnit.getEndPosition());
                        refreshedContent.insert(blockInOldUnit.getStartPosition(), newBlockContent);
                    }
                }
            }
        }
        return refreshedContent.toString();
    }

    private String addBlocks(final String content, final String generatedFullContent, final List<? extends AspectJBlock> blocksToAdd) {
        final StringBuilder newContent = new StringBuilder();
        newContent.append(content);
        Collections.sort(blocksToAdd, AspectJBlockComperator.INSTANCE);
        final int lastGeneratedBlockEndPosition = ajParser.parse(content).getNextBlockPositionToWrite();
        for (final AspectJBlock block : blocksToAdd) {
            final String methodBlock = generatedFullContent.substring(block.getStartPosition(), block.getEndPosition());
            newContent.insert(lastGeneratedBlockEndPosition, "    " + methodBlock);
            newContent.insert(lastGeneratedBlockEndPosition, "\n\n");
        }
        return newContent.toString();
    }

    private String removeMissingBlocks(final String oldContent, final List<? extends AspectJBlock> blocksToRemove) {
        final StringBuilder newContent = new StringBuilder();
        newContent.append(oldContent);
        Collections.sort(blocksToRemove, AspectJBlockComperator.INSTANCE);
        for (final AspectJBlock aspectJITDMethod : blocksToRemove) {
            newContent.delete(aspectJITDMethod.getStartPosition(), aspectJITDMethod.getEndPosition());
            removeNextNewLines(newContent, aspectJITDMethod.getStartPosition());
            removeSpacesBefore(newContent, aspectJITDMethod.getStartPosition());
        }

        return newContent.toString();
    }

    private void removeSpacesBefore(final StringBuilder newContent, final int position) {
        int i = 1;
        while (newContent.charAt(position - i) == ' ') {
            newContent.deleteCharAt(position - i);
            i++;
        }
    }

    private void removeNextNewLines(final StringBuilder newContent, final int position) {
        while (newContent.charAt(position) == '\n') {
            newContent.deleteCharAt(position);
        }
    }

    private List<AspectJBlock> getRemovedBlocks(final AspectJUnit oldUnit, final AspectJUnit newUnit, final GeneratorData data) {
        final List<AspectJBlock> blocksToRemove = new LinkedList<>();
        for (final AspectJBlock blockInOldUnit : oldUnit.getAllBlocks()) {
            if (data.containsIdWithChangedName(blockInOldUnit.getAnnotationId(), blockInOldUnit.getAnnotationName())) {
                continue;
            }
            boolean blockIsMissingInNewUnit = true;

            final String oldAnnotationData = blockInOldUnit.getAnnotationData();
            final String oldAnnotationName = blockInOldUnit.getAnnotationName();

            for (final AspectJBlock blockInNewUnit : newUnit.getAllBlocks()) {
                final String newAnnotationData = blockInNewUnit.getAnnotationData();
                final String newAnnotationName = blockInNewUnit.getAnnotationName();

                if (oldAnnotationData.equals(newAnnotationData)
                        && oldAnnotationName.equals(newAnnotationName)) {
                    blockIsMissingInNewUnit = false;
                }
            }

            if (blockIsMissingInNewUnit) {
                blocksToRemove.add(blockInOldUnit);
            }
        }
        return blocksToRemove;
    }

    private List<AspectJBlock> getNewBlocks(final AspectJUnit oldUnit, final AspectJUnit newUnit, final GeneratorData data) {
        final List<AspectJBlock> blocksToAdd = new LinkedList<>();
        for (final AspectJBlock blockInNewUnit : newUnit.getAllBlocks()) {
            if (data.containsIdWithDeletedName(blockInNewUnit.getAnnotationId(), blockInNewUnit.getAnnotationName())
                    || data.containsIdWithChangedName(blockInNewUnit.getAnnotationId(), blockInNewUnit.getAnnotationName())) {
                continue;
            }

            boolean blockIsMissingInOldUnit = true;

            final String newAnnotationData = blockInNewUnit.getAnnotationData();
            final String newAnnotationName = blockInNewUnit.getAnnotationName();

            for (final AspectJBlock blockInOldUnit : oldUnit.getAllBlocks()) {

                final String oldAnnotationData = blockInOldUnit.getAnnotationData();
                final String oldAnnotationName = blockInOldUnit.getAnnotationName();

                if (oldAnnotationData.equals(newAnnotationData) && oldAnnotationName.equals(newAnnotationName)) {
                    blockIsMissingInOldUnit = false;
                }
            }

            if (blockIsMissingInOldUnit) {
                blocksToAdd.add(blockInNewUnit);
            }
        }
        return blocksToAdd;
    }

    @Override
    public GeneratedContent setIds(final GeneratedContent generatedContent) {
        final String classIdRepladed = generatedContent.getContent().replace(CLASS_ID_PLACEHOLDER, CLASS_ID);
        String replacedGroupIds = classIdRepladed;
        int idCounter = 1;
        String nextIdPlaceHolder = "{newid" + idCounter + "}";
        while (replacedGroupIds.contains(nextIdPlaceHolder)) {
            idCounter++;
            replacedGroupIds = replacedGroupIds.replace(nextIdPlaceHolder, String.valueOf(idCounter));
            nextIdPlaceHolder = "{newid" + idCounter + "}";
        }
        return new GeneratedContent(generatedContent.getFilename(), replacedGroupIds, generatedContent.getGeneratorName());
    }

    public String setIds(final String content) {
        final AspectJUnit unit = ajParser.parse(content);

        final String classIdRepladed = content.replace(CLASS_ID_PLACEHOLDER, CLASS_ID);
        String replacedGroupIds = classIdRepladed;
        String nextIdPlaceHolder = getNextNewId(replacedGroupIds);
        while (nextIdPlaceHolder != null && replacedGroupIds.contains(nextIdPlaceHolder)) {
            final Integer oldId = getOldId(nextIdPlaceHolder, unit);
            final int idCounter;
            if (oldId == null) {
                idCounter = getNextFreeId(replacedGroupIds);
            } else {
                idCounter = oldId;
            }
            replacedGroupIds = replacedGroupIds.replace(nextIdPlaceHolder, String.valueOf(idCounter));
            nextIdPlaceHolder = getNextNewId(replacedGroupIds);
        }
        return  replacedGroupIds;
    }

    private Integer getOldId(final String nextIdPlaceHolder, final AspectJUnit unit) {
        AspectJBlock block = null;
        for (final AspectJBlock blockTmp : unit.getAllBlocks()) {
            if (nextIdPlaceHolder.equals(blockTmp.getAnnotationId())) {
                block = blockTmp;
            }
        }
        if (block != null) {
            for (final AspectJBlock blockTmp : unit.getAllBlocks()) {
                if (blockTmp.getAnnotationData().equals(block.getAnnotationData())
                        && !nextIdPlaceHolder.equals(blockTmp.getAnnotationId())) {
                    return Integer.valueOf(blockTmp.getAnnotationId());
                }
            }
        }
        return null;
    }

    private int getNextFreeId(final String content) {
        int idCounter = 2;
        while (content.contains("@Generated(id = " + idCounter + "")) {
            idCounter++;
        }
        return idCounter;
    }

    private String getNextNewId(final String content) {
        final Pattern p = Pattern.compile("\\{newid(\\d+)\\}");
        final Matcher m = p.matcher(content);
        if (m.find()) {
            return m.group();
        } else {
            return null;
        }
    }

}
