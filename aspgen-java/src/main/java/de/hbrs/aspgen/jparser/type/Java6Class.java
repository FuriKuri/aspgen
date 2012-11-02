package de.hbrs.aspgen.jparser.type;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.hbrs.aspgen.api.ast.JavaAnnotation;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;

public class Java6Class implements JavaClass {
    private String className;
    private String packageName;
    private final List<String> imports = new LinkedList<>();
    private final List<String> staticImports = new LinkedList<>();
    private final List<JavaField> fields = new LinkedList<>();
    private final List<JavaMethod> methods = new LinkedList<>();
    private final List<JavaAnnotation> annotations = new LinkedList<>();
    private boolean isInterface;
    private int startPosition;

    @Override
    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(final String packageName) {
        this.packageName = packageName;
    }

    @Override
    public List<String> getImports() {
        return imports;
    }

    @Override
    public List<String> getStaticImports() {
        return staticImports;
    }


    @Override
    public List<JavaField> getFields() {
        return fields;
    }

    public void addField(final JavaField field) {
        fields.add(field);
    }

    @Override
    public List<JavaMethod> getMethods() {
        return methods;
    }

    public void addMethod(final JavaMethod method) {
        methods.add(method);
    }

    @Override
    public List<JavaAnnotation> getAnnotations() {
        return annotations;
    }

    public void addAnnotation(final JavaAnnotation annotation) {
        annotations.add(annotation);
    }

    public void addImport(final String importName) {
        imports.add(importName);
    }

    public void addStaticImport(final String importName) {
        staticImports.add(importName);
    }

    @Override
    public List<JavaAnnotation> getAllUsedAnnotations() {
        final List<JavaAnnotation> usedAnnotations = new LinkedList<>();
        usedAnnotations.addAll(annotations);
        for (final JavaField javaField : fields) {
            usedAnnotations.addAll(javaField.getAnnotations());
        }
        for (final JavaMethod javaMethod : methods) {
            for (final JavaParameter javaParameter : javaMethod.getParameters()) {
                usedAnnotations.addAll(javaParameter.getAnnotations());
            }
            usedAnnotations.addAll(javaMethod.getAnnotations());
        }
        return usedAnnotations;
    }

    @Override
    public void replaceWithFullQualifiedAnnotations(final Set<String> fullQualifedAnnotations) {
        final List<JavaAnnotation> usedAnnotations = getAllUsedAnnotations();
        for (final String fullQualifedAnnotation : fullQualifedAnnotations) {
            for (final JavaAnnotation usedAnnotation : usedAnnotations) {
                final String usedAnnotationName = usedAnnotation.getName();
                if (isNotFullQualifedName(usedAnnotationName) && fullQualifedAnnotation.endsWith(usedAnnotationName)) {
                    if (annotationIsImportedByClass(fullQualifedAnnotation, usedAnnotationName)) {
                        usedAnnotation.updateFullQualifedName(fullQualifedAnnotation);
                    }
                }
            }
        }
    }

    private boolean isNotFullQualifedName(final String usedAnnotationName) {
        return !usedAnnotationName.contains(".");
    }

    private boolean annotationIsImportedByClass(
            final String fullQualifedAnnotation, final String usedAnnotationName) {
        final String packagePath =fullQualifedAnnotation.substring(0, fullQualifedAnnotation.length() - usedAnnotationName.length() - 1);
        return imports.contains(fullQualifedAnnotation) || imports.contains(packagePath + ".*");
    }

    @Override
    public boolean isInterface() {
        return isInterface;
    }

    public void setIsInterface(final boolean isInterface) {
        this.isInterface = isInterface;
    }

    @Override
    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(final int startPosition) {
        this.startPosition = startPosition;
    }

    @Override
    public void removeAnnotationAttribute(final String attribute) {
        for (final JavaAnnotation javaAnnotation : getAllUsedAnnotations()) {
            javaAnnotation.removeAttribute(attribute);
        }
    }
}
