package de.hbrs.aspgen.merger.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.JavaAnnotation;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.ast.UpdatableBlockForAnnotation;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.api.diff.AspectJDiff;
import de.hbrs.aspgen.api.diff.AspectJFieldDiff;
import de.hbrs.aspgen.api.diff.AspectJMethodDiff;
import de.hbrs.aspgen.api.diff.AspectJParameterDiff;
import de.hbrs.aspgen.api.diff.JavaUpdater;
import de.hbrs.aspgen.api.parser.JavaParser;

public class JavaAnnotationUpdater implements JavaUpdater {
    private final JavaParser javaParser;

    @Inject
    public JavaAnnotationUpdater(final JavaParser javaParser) {
        this.javaParser = javaParser;
    }

    @Override
    public String updateJavaContent(final String javaContent, final AspectJDiff aspectJDiff) {
        final StringBuffer newJavaContent = new StringBuffer(javaContent);
        final String annotationFullName = aspectJDiff.getData().getAnnotation();
        final String[] annotationNames = annotationFullName.split("\\.");
        final String annotationSimpleName = annotationNames[annotationNames.length - 1];
        JavaClass javaClass = javaParser.parse(javaContent);
        for (final JavaAnnotation annotation : javaClass.getAnnotations()) {
            if (annotation.getName().equals(annotationFullName) || annotation.getName().equals(annotationSimpleName)) {
                if (aspectJDiff.getAspectJClassDiff() != null) {
                    final AnnotationData dataForClass = aspectJDiff.getAspectJClassDiff().getAnnotationData();
                    final String annotationString = annotation.getAnnotationAsString();
                    final String newStrign = setAnnoationDatas(annotationString, dataForClass);
                    newJavaContent.replace(annotation.getStartPosition(), annotation.getEndPosition(), newStrign);
                }
            }
        }
        // refresh positions
        javaClass = javaParser.parse(newJavaContent.toString());
        Collections.sort(javaClass.getFields(), UpdatedableBlockComperator.INSTANCE);
        for (final JavaField javaField : javaClass.getFields()) {
            final AspectJFieldDiff fieldDiff = aspectJDiff.getAspectJFieldDiff(javaField);
            if (fieldDiff != null) {
                updateAnnotation(newJavaContent, annotationFullName, annotationSimpleName,
                        javaField, fieldDiff.getAnnotationData());
            }
        }

        javaClass = javaParser.parse(newJavaContent.toString());
        Collections.sort(javaClass.getMethods(), UpdatedableBlockComperator.INSTANCE);
        for (final JavaMethod javaMethod : javaClass.getMethods()) {
            final AspectJMethodDiff methodDiff = aspectJDiff.getAspectJMethodDiff(javaMethod);
            if (methodDiff != null) {
                updateAnnotation(newJavaContent, annotationFullName, annotationSimpleName,
                        javaMethod, methodDiff.getAnnotationData());
            }
        }

        javaClass = javaParser.parse(newJavaContent.toString());
        Collections.sort(javaClass.getMethods(), UpdatedableBlockComperator.INSTANCE);
        for (final JavaMethod javaMethod : javaClass.getMethods()) {
            final List<JavaParameter> saveCopyOfParameters = new LinkedList<>(javaMethod.getParameters());
            Collections.copy(saveCopyOfParameters, javaMethod.getParameters());
            Collections.sort(saveCopyOfParameters, UpdatedableBlockComperator.INSTANCE);
            for (final JavaParameter javaParameter : saveCopyOfParameters) {
                final AspectJParameterDiff parameterDiff = aspectJDiff.getAspectJParameterDiff(javaParameter, javaMethod);
                if (parameterDiff != null) {
                    updateParameterAnnotation(newJavaContent, annotationFullName, annotationSimpleName,
                            javaParameter, parameterDiff.getAnnotationData());
                }
            }
        }

        return newJavaContent.toString();
    }

    private void updateParameterAnnotation(final StringBuffer newJavaContent,
            final String annotationFullName, final String annotationSimpleName,
            final UpdatableBlockForAnnotation javaField, final AnnotationData annotationData) {
        boolean annotationExist = false;
        for (final JavaAnnotation annotation : javaField.getAnnotations()) {
            if (annotation.getName().equals(annotationFullName) || annotation.getName().equals(annotationSimpleName)) {
                annotationExist = true;
                updateAnnotation(newJavaContent, javaField, annotationData, annotation);
            }
        }
        if (!annotationExist) {
            addAnnotationToMethodSignatur(newJavaContent, javaField, annotationData, annotationSimpleName);
        }
    }

    private void addAnnotationToMethodSignatur(final StringBuffer newJavaContent,
            final UpdatableBlockForAnnotation javaField, final AnnotationData dataForField,
            final String annotationSimpleName) {
        final String annotation = createAnnotation(dataForField, annotationSimpleName);
        final int startPosition = javaField.getStartPosition();
        newJavaContent.insert(startPosition, annotation + " ");
    }

    private void updateAnnotation(final StringBuffer newJavaContent,
            final String annotationFullName, final String annotationSimpleName,
            final UpdatableBlockForAnnotation javaField, final AnnotationData annotationData) {
        boolean annotationExist = false;
        for (final JavaAnnotation annotation : javaField.getAnnotations()) {
            if (annotation.getName().equals(annotationFullName) || annotation.getName().equals(annotationSimpleName)) {
                annotationExist = true;
                updateAnnotation(newJavaContent, javaField, annotationData, annotation);
            }
        }
        if (!annotationExist) {
            addAnnotationToBlock(newJavaContent, javaField, annotationData, annotationSimpleName);
        }
    }

    private void updateAnnotation(final StringBuffer newJavaContent,
            final UpdatableBlockForAnnotation javaField, final AnnotationData dataForField, final JavaAnnotation annotation) {
        final String newAnnotation = setAnnoationDatas(annotation.getAnnotationAsString(), dataForField);
        newJavaContent.replace(annotation.getStartPosition(), annotation.getEndPosition(), newAnnotation);
    }

    private void addAnnotationToBlock(final StringBuffer newJavaContent,
            final UpdatableBlockForAnnotation javaField, final AnnotationData dataForField,
            final String annotationSimpleName) {
        final String annotation = createAnnotation(dataForField, annotationSimpleName);
        final int startPosition = javaField.getStartPosition();
        newJavaContent.insert(startPosition, annotation + "\n    ");
    }

    private String createAnnotation(final AnnotationData dataForField, final String annotationSimpleName) {
        final String annotation = "@" + annotationSimpleName + "()";
        return setAnnoationDatas(annotation, dataForField);
    }

    private String setAnnoationDatas(final String annotationValue, final AnnotationData annotationData) {
        String newAnnotationValue = removeAllGeneratorAttributes(annotationValue);
        final String idForAnnotation = getNewAttribut(getId(annotationData), newAnnotationValue);
        newAnnotationValue = addNewAttribute(newAnnotationValue, idForAnnotation);
        newAnnotationValue = addAttribut(newAnnotationValue, annotationData.getModified(), newAnnotationValue, "modified");
        newAnnotationValue = addAttribut(newAnnotationValue, annotationData.getDeleted(), newAnnotationValue, "deleted");
        newAnnotationValue = addAttribut(newAnnotationValue, annotationData.getExcluded(), newAnnotationValue, "exclude");
        return newAnnotationValue;
    }

    private String removeAllGeneratorAttributes(final String annotationValue) {
        return annotationValue.replaceAll("(,\\s*)?id\\s*=\\s*\\d+", "")
                .replaceAll("(,\\s*)?deleted\\s*=\\s*\"[^\"]*\"", "")
                .replaceAll("(,\\s*)?modified\\s*=\\s*\"[^\"]*\"", "")
                .replaceAll("(,\\s*)?exclude\\s*=\\s*\"[^\"]*\"", "");
    }

    private String addAttribut(final String annotationValue, final List<String> values, String newAnnotationValue, final String attributType) {
        final String modifedForAnnotation = getNewAttribut(getAttribut(values, attributType), newAnnotationValue);
        newAnnotationValue = addNewAttribute(newAnnotationValue, modifedForAnnotation);
        return newAnnotationValue;
    }



    private String getNewAttribut(final String newAttribut, final String annotationValue) {
        if (newAttribut.isEmpty()) {
            return "";
        } else {
            if (annotationHasAttribut(annotationValue)) {
                return ", " + newAttribut;
            } else {
                return newAttribut;
            }
        }
    }

    private boolean annotationHasAttribut(final String annotationValue) {
        return annotationValue.contains("(") && !annotationValue.matches("@\\w+\\(\\s*\\)");
    }

    private String addNewAttribute(final String annotationValue,
            final String deleteForAnnotation) {
        String newAnnotationValue;
        String newString;
        if (!annotationValue.endsWith(")")) {
            newString = annotationValue + "()";
        } else {
            newString = annotationValue;
        }
        newAnnotationValue = addAttribut(newString, deleteForAnnotation);
        return newAnnotationValue;
    }

    private String addAttribut(final String newString, final String deleteForAnnotation) {
        return newString.substring(0, newString.length() - 1) + deleteForAnnotation + ")";
    }

    private String getAttribut(final List<String> items, final String attribute) {
        if (items.size() > 0) {
            final StringBuffer deletedAnnoString = new StringBuffer(attribute + " = \"");
            for (final String item : items) {
                deletedAnnoString.append(item);
                deletedAnnoString.append(", ");
            }
            deletedAnnoString.deleteCharAt(deletedAnnoString.length() - 1);
            deletedAnnoString.deleteCharAt(deletedAnnoString.length() - 1);
            deletedAnnoString.append("\"");
            return deletedAnnoString.toString();
        }
        return "";
    }

    private String getId(final AnnotationData annotationData) {
        if (annotationData.getId().matches("\\d+") && annotationData.getModified().size() > 0) {
            return "id = " + annotationData.getId();
        }
        return "";
    }

    @Override
    public String removeAnnotations(final String javaContent, final String aspectJName) {
        // FEATURE support full name
        final StringBuilder builder = new StringBuilder(javaContent);
        final JavaClass javaClass = javaParser.parse(javaContent);
        final List<JavaAnnotation> annotations = javaClass.getAllUsedAnnotations();
        Collections.sort(annotations, AnnotationComperator.INSTANCE);
        for (final JavaAnnotation annotation : annotations) {
            if (annotation.getName().equals(aspectJName)) {
                builder.delete(annotation.getStartPosition(), annotation.getEndPosition());
                removeSpacesBeforeAndNewLines(builder, annotation.getStartPosition());
            }
        }
        return builder.toString();
    }

    //TODO refactor
    private void removeSpacesBeforeAndNewLines(final StringBuilder newContent, final int position) {
        int i = 1;
        while (newContent.charAt(position - i) == ' ') {
            newContent.deleteCharAt(position - i);
            i++;
        }
        if (newContent.charAt(position - i) == '\r' && newContent.charAt(position - i - 1) == '\n') {
            newContent.deleteCharAt(position - i);
            newContent.deleteCharAt(position - i);
        } else if (newContent.charAt(position - i) == '\n' && newContent.charAt(position - i - 1) == '\r') {
            newContent.deleteCharAt(position - i);
            newContent.deleteCharAt(position - i);
        } else if (newContent.charAt(position - i) == '\n' || newContent.charAt(position - i) == '\r') {
            newContent.deleteCharAt(position - i);
        }
    }

}
