package de.hbrs.aspgen.merger.impl;

import java.util.Collections;
import java.util.List;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.diff.AnnotationDataContainer;
import de.hbrs.aspgen.api.diff.ContentUpdater;
import de.hbrs.aspgen.api.parser.AspectJParser;

public class AspectJUpdater implements ContentUpdater {

    private final AspectJParser aspectJParser;

    @Inject
    public AspectJUpdater(final AspectJParser aspectJParser) {
        this.aspectJParser = aspectJParser;
    }

    @Override
    public String updatePackageName(final String content, final String packageName) {
        return content.replaceFirst("package\\s+\\w+(\\.\\w+)*\\s*;", "package " + packageName + ";");
    }

    @Override
    public String updateClassName(final String content, final String className) {
        final AspectJUnit aspectJUnit = aspectJParser.parse(content);
        final String name = aspectJUnit.getClassname();
        final String namePostfix = name.split("_")[1];

        final String updatedName = content.replaceFirst("aspect\\s+" + name,
                "aspect " + className + "_" + namePostfix);
        final String updatedPerThis = updatedName.replaceFirst("perthis\\(this\\(\\w+\\)\\)",
                "perthis(this(" + className + "))");
        return updatedPerThis;
    }

    @Override
    public String removeNotModifiedBlocks(final String content,
            final AnnotationDataContainer dataContainer) {
        final StringBuilder buffer = new StringBuilder(content);
        final AspectJUnit aspectJUnit = aspectJParser.parse(content);
        final List<AspectJBlock> allBlock = aspectJUnit.getAllBlocks();
        Collections.sort(allBlock, AspectJBlockComperator.INSTANCE);
        for (final AspectJBlock aspectJBlock : allBlock) {
            final String name = aspectJBlock.getAnnotationName();
            final String id = aspectJBlock.getAnnotationId();
            if (!dataContainer.containsIdWithChangedName(id, name)) {
                buffer.delete(aspectJBlock.getStartPosition(), aspectJBlock.getEndPosition());
                removeNextNewLines(buffer, aspectJBlock.getStartPosition());
                removeSpacesBefore(buffer, aspectJBlock.getStartPosition());
            }
        }
        return buffer.toString();
    }

    //TODO refactor
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

    @Override
    public String deleteGeneratedBlocks(final String content) {
        final AspectJUnit aspectJUnit = aspectJParser.parse(content);
        final List<AspectJBlock> blocks = aspectJUnit.getAllBlocks();
        Collections.sort(blocks, AspectJBlockComperator.INSTANCE);
        final StringBuilder buffer = new StringBuilder(content);
        for (final AspectJBlock aspectJBlock : blocks) {
            buffer.delete(aspectJBlock.getStartPosition(), aspectJBlock.getEndPosition());
            removeNextNewLines(buffer, aspectJBlock.getStartPosition());
            removeSpacesBefore(buffer, aspectJBlock.getStartPosition());
        }
        return buffer.toString();
    }

}
