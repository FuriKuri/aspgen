package de.hbrs.aspgen.api.ast;

import java.util.List;

import de.hbrs.aspgen.api.merge.GeneratorData;

public interface JavaFactory {

    JavaClass buildJavaClass(AspectJBlock methodsToUpdate, GeneratorData data);

    JavaClass buildJavaClass(AspectJBlock methodsToUpdate, GeneratorData data, List<JavaField> excludedFields);

    JavaClass buildJavaClassForMethod(AspectJITDField fieldToUpdate, GeneratorData data);

    JavaClass buildJavaClasFromAdviceForParameter(
            AspectJAdvice adviceToUpdate, GeneratorData data);

    JavaClass buildJavaClassFromAdviceForMethod(
            AspectJAdvice adviceToUpdate, GeneratorData data);

    JavaClass buildJavaClassFromDeclareForMethod(
            AspectJDeclare adviceToUpdate, GeneratorData data);

    JavaClass buildJavaClassFromAdviceForMethod(AspectJAdvice adviceToUpdate,
            GeneratorData data, List<JavaParameter> excludedParameters);

    JavaClass buildJavaClasFromAdviceForField(
            AspectJAdvice adviceToUpdate, GeneratorData data);

    JavaClass buildJavaClass(GeneratorData data);

    JavaField buildClassWithField(String fieldData);

    JavaMethod buildMethod(String methodData);

    JavaParameter buildParameter(String parameterData);

    JavaClass buildJavaClassForField(AspectJBlock methodsToUpdate,
            GeneratorData data);
}
