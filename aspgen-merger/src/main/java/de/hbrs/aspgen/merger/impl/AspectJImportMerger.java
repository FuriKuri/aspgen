package de.hbrs.aspgen.merger.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.ast.PositionContent;

public class AspectJImportMerger {
    public String updateImports(final String updatedContent, final AspectJUnit updatedUnit, final AspectJUnit newUnit) {
        final List<PositionContent> oldImports = updatedUnit.getImports();
        Collections.sort(oldImports, PositionContentComperator.INSTANCE);
        final List<PositionContent> newImports = newUnit.getImports();
        Collections.sort(newImports, PositionContentComperator.INSTANCE);
        final Set<String> usedTypesAndMethods = updatedUnit.getUsedTypesAndMethods();
        final List<String> updatedImports = new LinkedList<>();
        addImport(oldImports, usedTypesAndMethods, updatedImports);
        addImport(newImports, usedTypesAndMethods, updatedImports);
        final int firstPosition;
        final int lastPoistion;
        if (oldImports.size() > 0) {
            firstPosition = oldImports.get(0).getStart();
            lastPoistion = oldImports.get(oldImports.size() - 1).getEnd();
        } else {
            if (updatedUnit.getPackageName() != null) {
                firstPosition = updatedUnit.getPackageName().getEnd() + 1;
            } else {
                firstPosition = 0;
            }
            lastPoistion = firstPosition;
        }
        final StringBuffer importBlock = new StringBuffer();
        for (final String usedImport : updatedImports) {
            importBlock.append(usedImport + "\n");
        }

        if (importBlock.length() > 0) {
            importBlock.deleteCharAt(importBlock.length() - 1);
        }
        final StringBuffer result = new StringBuffer(updatedContent);
        result.delete(firstPosition, lastPoistion);
        result.insert(firstPosition, importBlock);
        return result.toString();
    }

    private void addImport(final List<PositionContent> newImports,
            final Set<String> usedTypesAndMethods,
            final List<String> updatedImports) {
        for (final PositionContent positionContent : newImports) {
            final String[] importParts = positionContent.getContent().split("\\.");
            final String simpleName = importParts[importParts.length - 1].replaceAll(";", "");
            if ((simpleName.equals("*") || usedTypesAndMethods.contains(simpleName)) && !updatedImports.contains(positionContent.getContent())) {
                updatedImports.add(positionContent.getContent());
            }
        }
    }
}
