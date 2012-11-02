package de.hbrs.aspgen.merger.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJITDField;
import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.merge.GeneratorData;
import de.hbrs.aspgen.api.parser.AspectJParser;
import de.hbrs.aspgen.merger.diff.FieldDiffImpl;

public class AspectJFieldMerger implements Merger {
    private final AspectJParser ajParser;
    private final GeneratorManager generatorManager;
    private final JavaFactory javaFactory;

    @Inject
    public AspectJFieldMerger(final AspectJParser ajParser, final GeneratorManager generatorManager, final JavaFactory javaFactory) {
        this.ajParser = ajParser;
        this.generatorManager = generatorManager;
        this.javaFactory = javaFactory;
    }

    @Override
    public String mergeContent(final AspectJUnit updatedUnit,
            final String updatedFieldContent, final AspectJUnit newUnit, final GeneratorData data) {
        final List<AspectJITDField> fieldsToUpdate = getFieldsToUpdate(data, updatedUnit);
        Collections.sort(fieldsToUpdate, AspectJBlockComperator.INSTANCE);
        final StringBuffer newUnitContent = new StringBuffer(updatedFieldContent);
        for (final AspectJITDField fieldToUpdate : fieldsToUpdate) {
            final AspectJITDField newField = getFieldFromUnit(fieldToUpdate, newUnit);
            if (propertiesEqualsForField(fieldToUpdate, newField)) {
                final String newMethodContent = mergeFields(fieldToUpdate, newField, updatedFieldContent, data);

                newUnitContent.delete(fieldToUpdate.getStartPosition(), fieldToUpdate.getEndPosition());
                newUnitContent.insert(fieldToUpdate.getStartPosition(), newMethodContent);
            }
        }
        return newUnitContent.toString();
    }


    private AspectJITDField getFieldFromUnit(final AspectJITDField fieldToUpdate,
            final AspectJUnit newUnit) {
        for (final AspectJITDField field : newUnit.getItdFields()) {
            if (field.getAnnotationId().equals(fieldToUpdate.getAnnotationId())
                    && field.getAnnotationName().equals(fieldToUpdate.getAnnotationName())) {
                return field;
            }
        }
        return null;
    }

    private boolean propertiesEqualsForField(final AspectJITDField fieldToUpdate,
            final AspectJITDField newField) {
        return propertiesEquals(fieldToUpdate, newField, 2);
    }

    private boolean propertiesEquals(final AspectJBlock adviceToUpdate,
            final AspectJBlock newAdvice, final int position) {
        if (adviceToUpdate.getAnnotationData().split(";").length > position && newAdvice.getAnnotationData().split(";").length > position) {
            return adviceToUpdate.getAnnotationData().split(";")[position].equals(newAdvice.getAnnotationData().split(";")[position]);
        } else {
            return true;
        }
    }

    private String mergeFields(final AspectJITDField fieldToUpdate,
            final AspectJITDField newField, final String oldContent,
            final GeneratorData data) {
        final JavaClass javaClass = javaFactory.buildJavaClassForMethod(fieldToUpdate, data);
        final String orginalContent = generatorManager.generateContentForGenerator(javaClass, data.getAnnotation());
        final AspectJUnit orginalUnit = ajParser.parse(orginalContent);
        final AspectJITDField orginalMethod = getFieldFromUnit(fieldToUpdate, orginalUnit);

        final String oldMethodContent = oldContent.substring(fieldToUpdate.getStartPosition(), fieldToUpdate.getEndPosition());
        final StringBuffer newMethodContent = new StringBuffer(oldMethodContent);

        final int offset = -1 * fieldToUpdate.getStartPosition();
        final FieldDiffImpl diff = makeDiff(orginalMethod, fieldToUpdate);
        if (!diff.isBlock() && !diff.isType()) {
            newMethodContent.delete(fieldToUpdate.getBlock().getStart() + offset,
                    fieldToUpdate.getBlock().getEnd() + offset);
            newMethodContent.insert(fieldToUpdate.getBlock().getStart() + offset, newField.getBlock().getContent());
        }

        if (!diff.isOnType()) {
            newMethodContent.delete(fieldToUpdate.getOnType().getStart() + offset,
                    fieldToUpdate.getOnType().getEnd() + offset);
            newMethodContent.insert(fieldToUpdate.getOnType().getStart() + offset, newField.getOnType().getContent());
        }

        if (!diff.isBlock() && !diff.isType()) {
            newMethodContent.delete(fieldToUpdate.getType().getStart() + offset,
                    fieldToUpdate.getType().getEnd() + offset);
            newMethodContent.insert(fieldToUpdate.getType().getStart() + offset, newField.getType().getContent());
        }

        updateAnnotationData(newMethodContent, fieldToUpdate, newField);


        return newMethodContent.toString();
    }

    private FieldDiffImpl makeDiff(final AspectJITDField orginalAdvice,
            final AspectJITDField adviceToUpdate) {
        final FieldDiffImpl diff = new FieldDiffImpl();
        if (!orginalAdvice.getBlock().getContent().equals(adviceToUpdate.getBlock().getContent())) {
            diff.setBlock(true);
        }
        if (!orginalAdvice.getType().getContent().equals(adviceToUpdate.getType().getContent())) {
            diff.setType(true);
        }
        return diff;
    }

    private void updateAnnotationData(final StringBuffer content, final AspectJBlock blockToUpdate, final AspectJBlock newBlock) {
        final String data = blockToUpdate.getAnnotationData().replace("\"", "\\\"");
        final int annoData = content.indexOf(data);

        content.replace(annoData, annoData + data.length(), newBlock.getAnnotationData().replace("\"", "\\\""));
    }

    private List<AspectJITDField> getFieldsToUpdate(final GeneratorData data,
            final AspectJUnit updatedUnit) {
        final List<AspectJITDField> fieldsToUpdate = new LinkedList<>();
        for (final AspectJITDField field : updatedUnit.getItdFields()) {
            if (!field.getAnnotationId().equals("1") && data.containsIdWithChangedName(field.getAnnotationId(), field.getAnnotationName())) {
                fieldsToUpdate.add(field);
            }
        }
        return fieldsToUpdate;
    }
}
