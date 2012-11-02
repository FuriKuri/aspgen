package de.hbrs.aspgen.jparser.factory;

import java.util.List;

import de.hbrs.aspgen.api.ast.AspectJAdvice;
import de.hbrs.aspgen.api.ast.AspectJBlock;
import de.hbrs.aspgen.api.ast.AspectJDeclare;
import de.hbrs.aspgen.api.ast.AspectJITDField;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.merge.GeneratorData;
import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Field;
import de.hbrs.aspgen.jparser.type.Java6Method;
import de.hbrs.aspgen.jparser.type.Java6Parameter;

public class Java7Factory implements JavaFactory {

    @Override
    public JavaClass buildJavaClasFromAdviceForField(
            final AspectJAdvice adviceToUpdate, final GeneratorData data) {
        final String fieldType = adviceToUpdate.getAnnotationData().split(";")[0].split(":")[0];
        final String fieldName = adviceToUpdate.getAnnotationData().split(";")[0].split(":")[1];
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName(data.getClassname());

        final Java6Field field = new Java6Field();
        field.setName(fieldName);
        field.setType(fieldType);
        java6Class.addField(field);
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(data.getAnnotation());
        annotation.addAttribute("id", adviceToUpdate.getAnnotationId());
        field.addAnnotation(annotation);
        if (adviceToUpdate.getAnnotationData().split(";").length > 2) {
            addAnnotationProperty(annotation, adviceToUpdate.getAnnotationData().split(";")[2]);
        }
        return java6Class;
    }

    @Override
    public JavaClass buildJavaClassFromAdviceForMethod(final AspectJAdvice adviceToUpdate,
            final GeneratorData data, final List<JavaParameter> excludedParameters) {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName(data.getClassname());
        final String[] methodData = adviceToUpdate.getAnnotationData().split(";")[0].split(",");
        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType(methodData[0].split(":")[0]);
        java6Method.setType(methodData[0].split(":")[1]);
        java6Method.setName(methodData[0].split(":")[2]);
        final String[] parameters = adviceToUpdate.getAnnotationData().split(";")[1].split(",");
        for (final String parameter : parameters) {
            final Java6Parameter java6Parameter = new Java6Parameter();
            java6Parameter.setType(parameter.split(":")[0]);
            java6Parameter.setName(parameter.split(":")[1]);
            if (parameterInList(excludedParameters, java6Parameter)) {
                final Java6Annotation excludeAnnotation = new Java6Annotation();
                excludeAnnotation.setName(data.getAnnotation());
                excludeAnnotation.addAttribute("exclude", "\"" + adviceToUpdate.getAnnotationName() + "\"");
                java6Parameter.addAnnotation(excludeAnnotation);
            }
            java6Method.addParameter(java6Parameter);
        }

        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(data.getAnnotation());
        annotation.addAttribute("id", adviceToUpdate.getAnnotationId());
        java6Method.addAnnotation(annotation);
        java6Class.addMethod(java6Method);

        if (adviceToUpdate.getAnnotationData().split(";").length > 2) {
            addAnnotationProperty(annotation, adviceToUpdate.getAnnotationData().split(";")[2]);
        }

        return java6Class;
    }

    private boolean parameterInList(final List<JavaParameter> fields, final JavaParameter field) {
        for (final JavaParameter javaField : fields) {
            if (javaField.getName().equals(field.getName())
                    && javaField.getType().equals(field.getType())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public JavaClass buildJavaClassFromAdviceForMethod(
            final AspectJAdvice adviceToUpdate, final GeneratorData data) {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName(data.getClassname());
        final String[] methodData = adviceToUpdate.getAnnotationData().split(";")[0].split(",");
        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType(methodData[0].split(":")[0]);
        java6Method.setType(methodData[0].split(":")[1]);
        java6Method.setName(methodData[0].split(":")[2]);
        final String[] parameters = adviceToUpdate.getAnnotationData().split(";")[1].split(",");
        for (final String parameter : parameters) {
            final Java6Parameter java6Parameter = new Java6Parameter();
            java6Parameter.setType(parameter.split(":")[0]);
            java6Parameter.setName(parameter.split(":")[1]);
            java6Method.addParameter(java6Parameter);
        }

        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(data.getAnnotation());
        annotation.addAttribute("id", adviceToUpdate.getAnnotationId());
        java6Method.addAnnotation(annotation);
        java6Class.addMethod(java6Method);

        if (adviceToUpdate.getAnnotationData().split(";").length > 2) {
            addAnnotationProperty(annotation, adviceToUpdate.getAnnotationData().split(";")[2]);
        }

        return java6Class;
    }

    @Override
    public JavaClass buildJavaClasFromAdviceForParameter(
            final AspectJAdvice adviceToUpdate, final GeneratorData data) {
        final String parameterType = adviceToUpdate.getAnnotationData().split(";")[0].split(":")[0];
        final String parameterName = adviceToUpdate.getAnnotationData().split(";")[0].split(":")[1];


        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName(data.getClassname());
        final String methodData = adviceToUpdate.getAnnotationData().split(";")[1];
        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType(methodData.split(":")[0]);
        java6Method.setType(methodData.split(":")[1]);
        java6Method.setName(methodData.split(":")[2]);
        final String[] parameters = adviceToUpdate.getAnnotationData().split(";")[2].split(",");
        for (final String parameter : parameters) {
            final Java6Parameter java6Parameter = new Java6Parameter();
            java6Parameter.setType(parameter.split(":")[0]);
            java6Parameter.setName(parameter.split(":")[1]);
            if (java6Parameter.getName().equals(parameterName)
                    && java6Parameter.getType().equals(parameterType)) {
                final Java6Annotation annotation = new Java6Annotation();
                annotation.setName(data.getAnnotation());
                annotation.addAttribute("id", adviceToUpdate.getAnnotationId());
                java6Parameter.addAnnotation(annotation);

                if (adviceToUpdate.getAnnotationData().split(";").length > 3) {
                    addAnnotationProperty(annotation, adviceToUpdate.getAnnotationData().split(";")[3]);
                }
            }
            java6Method.addParameter(java6Parameter);
        }


        java6Class.addMethod(java6Method);
        return java6Class;
    }

    @Override
    public JavaClass buildJavaClassForMethod(final AspectJITDField fieldToUpdate,
            final GeneratorData data) {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName(data.getClassname());
        final String[] methodData = fieldToUpdate.getAnnotationData().split(";")[0].split(":");
        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType(methodData[0]);
        java6Method.setType(methodData[1]);
        java6Method.setName(methodData[2]);
        final String[] parameters = fieldToUpdate.getAnnotationData().split(";")[1].split(",");
        for (final String parameter : parameters) {
            final Java6Parameter java6Parameter = new Java6Parameter();
            java6Parameter.setType(parameter.split(":")[0]);
            java6Parameter.setName(parameter.split(":")[1]);
            java6Method.addParameter(java6Parameter);
        }

        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(data.getAnnotation());
        annotation.addAttribute("id", fieldToUpdate.getAnnotationId());
        java6Method.addAnnotation(annotation);
        java6Class.addMethod(java6Method);

        if (fieldToUpdate.getAnnotationData().split(";").length > 2) {
            addAnnotationProperty(annotation, fieldToUpdate.getAnnotationData().split(";")[2]);
        }

        return java6Class;
    }

    @Override
    public JavaClass buildJavaClass(final AspectJBlock methodsToUpdate,
            final GeneratorData data) {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName(data.getClassname());
        final String[] fields = methodsToUpdate.getAnnotationData().split(";")[0].split(",");
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(data.getAnnotation());
        annotation.addAttribute("id", methodsToUpdate.getAnnotationId());
        java6Class.addAnnotation(annotation);

        for (final String field : fields) {
            final Java6Field java6Field = new Java6Field();
            java6Field.setType(field.split(":")[0]);
            java6Field.setName(field.split(":")[1]);
            java6Class.addField(java6Field);
        }

        if (methodsToUpdate.getAnnotationData().split(";").length > 1) {
            addAnnotationProperty(annotation, methodsToUpdate.getAnnotationData().split(";")[1]);
        }

        return java6Class;
    }

    @Override
    public JavaClass buildJavaClassForField(final AspectJBlock methodsToUpdate,
            final GeneratorData data) {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName(data.getClassname());
        final String fieldData = methodsToUpdate.getAnnotationData().split(";")[0];
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(data.getAnnotation());
        annotation.addAttribute("id", methodsToUpdate.getAnnotationId());

        final Java6Field java6Field = new Java6Field();
        java6Field.setType(fieldData.split(":")[0]);
        java6Field.setName(fieldData.split(":")[1]);
        java6Field.addAnnotation(annotation);
        java6Class.addField(java6Field);

        if (methodsToUpdate.getAnnotationData().split(";").length > 1) {
            addAnnotationProperty(annotation, methodsToUpdate.getAnnotationData().split(";")[1]);
        }

        return java6Class;
    }

    @Override
    public JavaClass buildJavaClass(final AspectJBlock methodsToUpdate, final GeneratorData data, final List<JavaField> excludedFields) {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName(data.getClassname());
        final String[] fields = methodsToUpdate.getAnnotationData().split(";")[0].split(",");
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(data.getAnnotation());
        annotation.addAttribute("id", methodsToUpdate.getAnnotationId());
        java6Class.addAnnotation(annotation);

        for (final String field : fields) {
            final Java6Field java6Field = new Java6Field();
            java6Field.setType(field.split(":")[0]);
            java6Field.setName(field.split(":")[1]);
            if (fieldInList(excludedFields, java6Field)) {
                final Java6Annotation excludeAnnotation = new Java6Annotation();
                excludeAnnotation.setName(data.getAnnotation());
                excludeAnnotation.addAttribute("exclude", "\"" + methodsToUpdate.getAnnotationName() + "\"");
                java6Field.addAnnotation(excludeAnnotation);
            }
            java6Class.addField(java6Field);
        }

        if (methodsToUpdate.getAnnotationData().split(";").length > 1) {
            addAnnotationProperty(annotation, methodsToUpdate.getAnnotationData().split(";")[1]);
        }

        return java6Class;
    }


    private void addAnnotationProperty(final Java6Annotation annotation, final String annotationData) {
        final String[] properties = annotationData.split(",");
        for (final String property : properties) {
            annotation.addAttribute(property.split(":")[0], property.split(":")[1]);
        }
    }

    private boolean fieldInList(final List<JavaField> fields, final JavaField field) {
        for (final JavaField javaField : fields) {
            if (javaField.getName().equals(field.getName())
                    && javaField.getType().equals(field.getType())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public JavaClass buildJavaClass(final GeneratorData data) {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName(data.getClassname());

        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(data.getAnnotation());
        annotation.addAttribute("id", "1");
        java6Class.addAnnotation(annotation);
        return java6Class;
    }

    // TODO oben verwenden

    @Override
    public JavaField buildClassWithField(final String fieldData) {
        final String fieldType = fieldData.split(";")[0].split(":")[0];
        final String fieldName = fieldData.split(";")[0].split(":")[1];
        final Java6Field field = new Java6Field();
        field.setName(fieldName);
        field.setType(fieldType);
        return field;
    }

    @Override
    public JavaMethod buildMethod(final String methodData) {
        final String[] methodHeadData = methodData.split(";")[0].split(":");

        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType(methodHeadData[0]);
        java6Method.setType(methodHeadData[1]);
        java6Method.setName(methodHeadData[2]);
        final String[] parameters = methodData.split(";")[1].split(",");
        for (final String parameter : parameters) {
            final Java6Parameter java6Parameter = new Java6Parameter();
            java6Parameter.setType(parameter.split(":")[0]);
            java6Parameter.setName(parameter.split(":")[1]);
            java6Method.addParameter(java6Parameter);
        }
        return java6Method;
    }

    @Override
    public JavaParameter buildParameter(final String parameterData) {
        final String parameterType = parameterData.split(";")[0].split(":")[0];
        final String parameterName = parameterData.split(";")[0].split(":")[1];
        final Java6Parameter javaParameter = new Java6Parameter();
        javaParameter.setName(parameterName);
        javaParameter.setType(parameterType);
        return javaParameter;
    }

    @Override
    public JavaClass buildJavaClassFromDeclareForMethod(
            final AspectJDeclare adviceToUpdate, final GeneratorData data) {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName(data.getClassname());
        final String[] methodData = adviceToUpdate.getAnnotationData().split(";")[0].split(",");
        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType(methodData[0].split(":")[0]);
        java6Method.setType(methodData[0].split(":")[1]);
        java6Method.setName(methodData[0].split(":")[2]);
        final String[] parameters = adviceToUpdate.getAnnotationData().split(";")[1].split(",");
        for (final String parameter : parameters) {
            final Java6Parameter java6Parameter = new Java6Parameter();
            java6Parameter.setType(parameter.split(":")[0]);
            java6Parameter.setName(parameter.split(":")[1]);
            java6Method.addParameter(java6Parameter);
        }

        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(data.getAnnotation());
        annotation.addAttribute("id", adviceToUpdate.getAnnotationId());
        java6Method.addAnnotation(annotation);
        java6Class.addMethod(java6Method);

        if (adviceToUpdate.getAnnotationData().split(";").length > 2) {
            addAnnotationProperty(annotation, adviceToUpdate.getAnnotationData().split(";")[2]);
        }

        return java6Class;
    }
}
