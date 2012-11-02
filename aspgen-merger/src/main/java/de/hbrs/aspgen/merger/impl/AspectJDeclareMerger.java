package de.hbrs.aspgen.merger.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJDeclare;
import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.merge.GeneratorData;

public class AspectJDeclareMerger implements Merger {


    @Inject
    public AspectJDeclareMerger() {

    }

    @Override
    public String mergeContent(final AspectJUnit updatedUnit, final String updatedFieldContent, final AspectJUnit newUnit,
            final GeneratorData data) {
        final List<AspectJDeclare> advicesToUpdate = getDeclaresToUpdate(data, updatedUnit);
        Collections.sort(advicesToUpdate, AspectJBlockComperator.INSTANCE);
        final StringBuffer newUnitContent = new StringBuffer(updatedFieldContent);
        for (final AspectJDeclare adviceToUpdate : advicesToUpdate) {
            final AspectJDeclare newAdvice = getDeclareFromUnit(adviceToUpdate, newUnit);
            if (propertiesEquals(adviceToUpdate, newAdvice, 2)) {
                final String newMethodContent = mergeDeclares(adviceToUpdate, newAdvice, updatedFieldContent, data);
                newUnitContent.delete(adviceToUpdate.getStartPosition(), adviceToUpdate.getEndPosition());
                newUnitContent.insert(adviceToUpdate.getStartPosition(), newMethodContent);
            }
        }
        return newUnitContent.toString();
    }

    private boolean propertiesEquals(final AspectJBlock adviceToUpdate,
            final AspectJBlock newAdvice, final int position) {
        if (adviceToUpdate.getAnnotationData().split(";").length > position && newAdvice.getAnnotationData().split(";").length > position) {
            return adviceToUpdate.getAnnotationData().split(";")[position].equals(newAdvice.getAnnotationData().split(";")[position]);
        } else {
            return true;
        }
    }

    private List<AspectJDeclare> getDeclaresToUpdate(final GeneratorData data,
            final AspectJUnit updatedUnit) {
        final List<AspectJDeclare> advicesToUpdate = new LinkedList<>();
        for (final AspectJDeclare cons : updatedUnit.getDeclares()) {
            if (data.containsIdWithChangedName(cons.getAnnotationId(), cons.getAnnotationName())) {
                advicesToUpdate.add(cons);
            }
        }
        return advicesToUpdate;
    }

    private AspectJDeclare getDeclareFromUnit(final AspectJDeclare adviceToUpdate,
            final AspectJUnit newUnit) {
        for (final AspectJDeclare methodFromNewUnit : newUnit.getDeclares()) {
            if (methodFromNewUnit.getAnnotationId().equals(adviceToUpdate.getAnnotationId())
                    && methodFromNewUnit.getAnnotationName().equals(adviceToUpdate.getAnnotationName())) {
                return methodFromNewUnit;
            }
        }
        return null;
    }

    private String mergeDeclares(final AspectJDeclare adviceToUpdate,
            final AspectJDeclare newAdvice, final String updatedFieldContent,
            final GeneratorData data) {
        final String oldMethodContent = updatedFieldContent.substring(adviceToUpdate.getStartPosition(), adviceToUpdate.getEndPosition());
        final StringBuffer newMethodContent = new StringBuffer(oldMethodContent);

        final int offset = -1 * adviceToUpdate.getStartPosition();
        newMethodContent.delete(adviceToUpdate.getBlock().getStart() + offset,
                adviceToUpdate.getBlock().getEnd() + offset);
        newMethodContent.insert(adviceToUpdate.getBlock().getStart() + offset, newAdvice.getBlock().getContent());
        updateAnnotationData(newMethodContent, adviceToUpdate, newAdvice);

        return newMethodContent.toString();
    }

    private void updateAnnotationData(final StringBuffer content, final AspectJBlock blockToUpdate, final AspectJBlock newBlock) {
        final String data = blockToUpdate.getAnnotationData().replace("\"", "\\\"");
        final int annoData = content.indexOf(data);

        content.replace(annoData, annoData + data.length(), newBlock.getAnnotationData().replace("\"", "\\\""));
    }

}
