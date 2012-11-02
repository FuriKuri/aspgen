package de.hbrs.aspgen.merger.merge;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.jparser.Java6Parser;
import de.hbrs.aspgen.jparser.type.Java6Field;
import de.hbrs.aspgen.jparser.type.Java6Method;
import de.hbrs.aspgen.jparser.type.Java6Parameter;
import de.hbrs.aspgen.merger.diff.AspectJClassDiffImpl;
import de.hbrs.aspgen.merger.diff.AspectJDiffImpl;
import de.hbrs.aspgen.merger.diff.AspectJFieldDiffImpl;
import de.hbrs.aspgen.merger.diff.AspectJMethodDiffImpl;
import de.hbrs.aspgen.merger.diff.AspectJParameterDiffImpl;
import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;
import de.hbrs.aspgen.merger.impl.JavaAnnotationUpdater;

public class JavaAnnotationUpdaterTest {
    private JavaAnnotationUpdater javaUpdater;

    @Before
    public void init() {
        javaUpdater = new JavaAnnotationUpdater(new Java6Parser());
    }

    @Test
    public void addJavaClassAnnotationForDelete() throws IOException {
        final String javaFile = FileUtils.readFileToString(new File("src/test/resources/java/ToString.java"));
        final String updatedJavaFile = FileUtils.readFileToString(new File("src/test/resources/java/ToStringAddedDeleted.java"));
        final AspectJDiffImpl aspectJDiff = new AspectJDiffImpl();
        final AspectJClassDiffImpl classDiff = new AspectJClassDiffImpl();
        final AnnotationData annotationData = new AnnotationData("newIdClass");
        annotationData.addDeleted("ToString3");
        classDiff.setAnnotationData(annotationData);
        aspectJDiff.setData(new GeneratorDataImpl("de.hbrs.ToString", "Person"));
        aspectJDiff.setAspectJClassDiff(classDiff);
        final String result = javaUpdater.updateJavaContent(javaFile, aspectJDiff);
        assertEquals(updatedJavaFile, result);
    }

    @Test
    public void addJavaClassAnnotationForModified() throws IOException {
        final String javaFile = FileUtils.readFileToString(new File("src/test/resources/java/ToString.java"));
        final String updatedJavaFile = FileUtils.readFileToString(new File("src/test/resources/java/ToStringAddedModifed.java"));
        final AspectJDiffImpl aspectJDiff = new AspectJDiffImpl();
        final AspectJClassDiffImpl classDiff = new AspectJClassDiffImpl();
        final AnnotationData annotationData = new AnnotationData("1");
        annotationData.addModified("ToString");
        annotationData.addModified("ToString2");
        classDiff.setAnnotationData(annotationData);
        aspectJDiff.setData(new GeneratorDataImpl("de.hbrs.ToString", "Person"));
        aspectJDiff.setAspectJClassDiff(classDiff);
        final String result = javaUpdater.updateJavaContent(javaFile, aspectJDiff);
        assertEquals(updatedJavaFile, result);
    }

    @Test
    public void addJavaClassAnnotationForModifiedAndDeleted() throws IOException {
        final String javaFile = FileUtils.readFileToString(new File("src/test/resources/java/ToString.java"));
        final String updatedJavaFile = FileUtils.readFileToString(new File("src/test/resources/java/ToStringAddedModifedAndDeleted.java"));
        final AspectJDiffImpl aspectJDiff = new AspectJDiffImpl();
        final AspectJClassDiffImpl classDiff = new AspectJClassDiffImpl();
        final AnnotationData annotationData = new AnnotationData("1");
        annotationData.addModified("ToString");
        annotationData.addModified("ToString2");
        annotationData.addDeleted("ToString3");

        classDiff.setAnnotationData(annotationData);
        aspectJDiff.setData(new GeneratorDataImpl("de.hbrs.ToString", "Person"));
        aspectJDiff.setAspectJClassDiff(classDiff);
        final String result = javaUpdater.updateJavaContent(javaFile, aspectJDiff);
        assertEquals(updatedJavaFile, result);
    }

    @Test
    public void addJavaClassAnnotationRemovedDeleted() throws IOException {
        final String javaFile = FileUtils.readFileToString(new File("src/test/resources/java/ToStringAddedModifedAndDeleted.java"));
        final String updatedJavaFile = FileUtils.readFileToString(new File("src/test/resources/java/ToStringAddedModifed.java"));
        final AspectJDiffImpl aspectJDiff = new AspectJDiffImpl();
        final AspectJClassDiffImpl classDiff = new AspectJClassDiffImpl();
        final AnnotationData annotationData = new AnnotationData("1");
        annotationData.addModified("ToString");
        annotationData.addModified("ToString2");

        classDiff.setAnnotationData(annotationData);
        aspectJDiff.setData(new GeneratorDataImpl("de.hbrs.ToString", "Person"));
        aspectJDiff.setAspectJClassDiff(classDiff);
        final String result = javaUpdater.updateJavaContent(javaFile, aspectJDiff);
        assertEquals(updatedJavaFile, result);
    }

    @Test
    public void addJavaClassAnnotationRemovedDeletedAndOwnAnnotation() throws IOException {
        final String javaFile = FileUtils.readFileToString(new File("src/test/resources/java/ToStringAddedDeletedAndOwn.java"));
        final String updatedJavaFile = FileUtils.readFileToString(new File("src/test/resources/java/ToStringAddedModifedAndOwn.java"));
        final AspectJDiffImpl aspectJDiff = new AspectJDiffImpl();
        final AspectJClassDiffImpl classDiff = new AspectJClassDiffImpl();
        final AnnotationData annotationData = new AnnotationData("1");
        annotationData.addModified("ToString");
        annotationData.addModified("ToString2");

        classDiff.setAnnotationData(annotationData);
        aspectJDiff.setData(new GeneratorDataImpl("de.hbrs.ToString", "Person"));
        aspectJDiff.setAspectJClassDiff(classDiff);
        final String result = javaUpdater.updateJavaContent(javaFile, aspectJDiff);
        assertEquals(updatedJavaFile, result);
    }


    @Test
    public void addFieldAnnotationAddModified() throws IOException {
        final String javaFile = FileUtils.readFileToString(new File("src/test/resources/java/JavaBean.java"));
        final String updatedJavaFile = FileUtils.readFileToString(new File("src/test/resources/java/JavaBeanAndModified.java"));
        final AspectJDiffImpl aspectJDiff = new AspectJDiffImpl();
        aspectJDiff.setData(new GeneratorDataImpl("de.hbrs.JavaBean", "Person"));

        final AspectJFieldDiffImpl fieldDiff = new AspectJFieldDiffImpl();
        final AnnotationData annotationData = new AnnotationData("2");
        annotationData.addModified("Getter");
        annotationData.addModified("Setter");
        fieldDiff.setAnnotationData(annotationData);
        aspectJDiff.addAspectJFieldDiffs(fieldDiff);
        final Java6Field field = new Java6Field();
        field.setName("asd");
        field.setType("String");
        fieldDiff.setField(field);

        final AspectJFieldDiffImpl fieldDiff2 = new AspectJFieldDiffImpl();
        final AnnotationData annotationData2 = new AnnotationData("3");
        annotationData2.addModified("Setter");
        fieldDiff2.setAnnotationData(annotationData2);
        aspectJDiff.addAspectJFieldDiffs(fieldDiff2);
        final Java6Field field2 = new Java6Field();
        field2.setName("age");
        field2.setType("int");
        fieldDiff2.setField(field2);

        final String result = javaUpdater.updateJavaContent(javaFile, aspectJDiff);
        assertEquals(updatedJavaFile, result);
    }

    @Test
    public void addFieldAnnotationAddModifiedAndDeleted() throws IOException {
        final String javaFile = FileUtils.readFileToString(new File("src/test/resources/java/JavaBean.java"));
        final String updatedJavaFile = FileUtils.readFileToString(new File("src/test/resources/java/JavaBeanAndModifiedAndDeleted.java"));
        final AspectJDiffImpl aspectJDiff = new AspectJDiffImpl();
        aspectJDiff.setData(new GeneratorDataImpl("de.hbrs.JavaBean", "Person"));

        final AspectJFieldDiffImpl fieldDiff = new AspectJFieldDiffImpl();
        final AnnotationData annotationData = new AnnotationData("2");
        annotationData.addModified("Getter");
        annotationData.addModified("Setter");
        annotationData.addDeleted("Getter2");
        fieldDiff.setAnnotationData(annotationData);
        aspectJDiff.addAspectJFieldDiffs(fieldDiff);
        final Java6Field field = new Java6Field();
        field.setName("asd");
        field.setType("String");
        fieldDiff.setField(field);

        final AspectJFieldDiffImpl fieldDiff2 = new AspectJFieldDiffImpl();
        final AnnotationData annotationData2 = new AnnotationData("3");
        annotationData2.addModified("Setter");
        fieldDiff2.setAnnotationData(annotationData2);
        aspectJDiff.addAspectJFieldDiffs(fieldDiff2);
        final Java6Field field2 = new Java6Field();
        field2.setName("age");
        field2.setType("int");
        fieldDiff2.setField(field2);

        final AspectJFieldDiffImpl fieldDiff3 = new AspectJFieldDiffImpl();
        final AnnotationData annotationData3 = new AnnotationData("{newid}");
        annotationData3.addDeleted("Getter2");
        fieldDiff3.setAnnotationData(annotationData3);
        aspectJDiff.addAspectJFieldDiffs(fieldDiff3);
        final Java6Field field3 = new Java6Field();
        field3.setName("counter");
        field3.setType("int");
        fieldDiff3.setField(field3);

        final String result = javaUpdater.updateJavaContent(javaFile, aspectJDiff);
        assertEquals(updatedJavaFile, result);
    }

    @Test
    public void addFieldAnnotationExistModifiedAndAddDeleted() throws IOException {
        final String javaFile = FileUtils.readFileToString(new File("src/test/resources/java/JavaBeanAndModified.java"));
        final String updatedJavaFile = FileUtils.readFileToString(new File("src/test/resources/java/JavaBeanAndModifiedAndDeleted.java"));
        final AspectJDiffImpl aspectJDiff = new AspectJDiffImpl();
        aspectJDiff.setData(new GeneratorDataImpl("de.hbrs.JavaBean", "Person"));

        final AspectJFieldDiffImpl fieldDiff = new AspectJFieldDiffImpl();
        final AnnotationData annotationData = new AnnotationData("2");
        annotationData.addModified("Getter");
        annotationData.addModified("Setter");
        annotationData.addDeleted("Getter2");
        fieldDiff.setAnnotationData(annotationData);
        aspectJDiff.addAspectJFieldDiffs(fieldDiff);
        final Java6Field field = new Java6Field();
        field.setName("asd");
        field.setType("String");
        fieldDiff.setField(field);

        final AspectJFieldDiffImpl fieldDiff2 = new AspectJFieldDiffImpl();
        final AnnotationData annotationData2 = new AnnotationData("3");
        annotationData2.addModified("Setter");
        fieldDiff2.setAnnotationData(annotationData2);
        aspectJDiff.addAspectJFieldDiffs(fieldDiff2);
        final Java6Field field2 = new Java6Field();
        field2.setName("age");
        field2.setType("int");
        fieldDiff2.setField(field2);

        final AspectJFieldDiffImpl fieldDiff3 = new AspectJFieldDiffImpl();
        final AnnotationData annotationData3 = new AnnotationData("{newid}");
        annotationData3.addDeleted("Getter2");
        fieldDiff3.setAnnotationData(annotationData3);
        aspectJDiff.addAspectJFieldDiffs(fieldDiff3);
        final Java6Field field3 = new Java6Field();
        field3.setName("counter");
        field3.setType("int");
        fieldDiff3.setField(field3);

        final String result = javaUpdater.updateJavaContent(javaFile, aspectJDiff);
        assertEquals(updatedJavaFile, result);
    }

    @Test
    public void addMethodAnnotationForModified() throws IOException {
        final String javaFile = FileUtils.readFileToString(new File("src/test/resources/java/Logging.java"));
        final String updatedJavaFile = FileUtils.readFileToString(new File("src/test/resources/java/LoggingAndModified.java"));
        final AspectJDiffImpl aspectJDiff = new AspectJDiffImpl();
        aspectJDiff.setData(new GeneratorDataImpl("de.hbrs.Logging", "Person"));

        final AspectJMethodDiffImpl methodDiff = new AspectJMethodDiffImpl();
        final AnnotationData annotationData = new AnnotationData("2");
        annotationData.addModified("Log");
        methodDiff.setAnnotationData(annotationData);
        aspectJDiff.addAspectJMethodDiffs(methodDiff);
        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType("public");
        java6Method.setName("method");
        java6Method.setType("String");
        java6Method.addParameter(createParameter("String", "a"));
        java6Method.addParameter(createParameter("Integer", "b"));
        methodDiff.setMethod(java6Method);

        final AspectJMethodDiffImpl methodDiff2 = new AspectJMethodDiffImpl();
        final AnnotationData annotationData2 = new AnnotationData("3");
        annotationData2.addModified("Log2");
        methodDiff2.setAnnotationData(annotationData2);
        aspectJDiff.addAspectJMethodDiffs(methodDiff2);
        final Java6Method java6Method2 = new Java6Method();
        java6Method2.setAccessType("public");
        java6Method2.setName("method2");
        java6Method2.setType("String");
        java6Method2.addParameter(createParameter("String", "a"));
        java6Method2.addParameter(createParameter("Integer", "b"));
        methodDiff2.setMethod(java6Method2);

        final String result = javaUpdater.updateJavaContent(javaFile, aspectJDiff);
        assertEquals(updatedJavaFile, result);
    }

    @Test
    public void addMethodAnnotationForModifiedAndDeleted() throws IOException {
        final String javaFile = FileUtils.readFileToString(new File("src/test/resources/java/LoggingAndModified.java"));
        final String updatedJavaFile = FileUtils.readFileToString(new File("src/test/resources/java/LoggingAndModifiedAndDeleted.java"));
        final AspectJDiffImpl aspectJDiff = new AspectJDiffImpl();
        aspectJDiff.setData(new GeneratorDataImpl("de.hbrs.Logging", "Person"));

        final AspectJMethodDiffImpl methodDiff = new AspectJMethodDiffImpl();
        final AnnotationData annotationData = new AnnotationData("2");
        annotationData.addModified("Log");
        methodDiff.setAnnotationData(annotationData);
        aspectJDiff.addAspectJMethodDiffs(methodDiff);
        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType("public");
        java6Method.setName("method");
        java6Method.setType("String");
        java6Method.addParameter(createParameter("String", "a"));
        java6Method.addParameter(createParameter("Integer", "b"));
        methodDiff.setMethod(java6Method);

        final AspectJMethodDiffImpl methodDiff2 = new AspectJMethodDiffImpl();
        final AnnotationData annotationData2 = new AnnotationData("3");
        annotationData2.addDeleted("Log2");
        methodDiff2.setAnnotationData(annotationData2);
        aspectJDiff.addAspectJMethodDiffs(methodDiff2);
        final Java6Method java6Method2 = new Java6Method();
        java6Method2.setAccessType("public");
        java6Method2.setName("method2");
        java6Method2.setType("String");
        java6Method2.addParameter(createParameter("String", "a"));
        java6Method2.addParameter(createParameter("Integer", "b"));
        methodDiff2.setMethod(java6Method2);

        final String result = javaUpdater.updateJavaContent(javaFile, aspectJDiff);
        assertEquals(updatedJavaFile, result);
    }

    @Test
    public void addParameterAnnotationForModifiedAndDeleted() throws IOException {
        final String javaFile = FileUtils.readFileToString(new File("src/test/resources/java/NotNull.java"));
        final String updatedJavaFile = FileUtils.readFileToString(new File("src/test/resources/java/NotNullModifiedAndDeleted.java"));
        final AspectJDiffImpl aspectJDiff = new AspectJDiffImpl();
        aspectJDiff.setData(new GeneratorDataImpl("de.hbrs.NotNull", "Person"));

        final AspectJParameterDiffImpl parameterDiff = new AspectJParameterDiffImpl();
        final AnnotationData annotationData = new AnnotationData("2");
        annotationData.addModified("Sec");
        parameterDiff.setAnnotationData(annotationData);
        aspectJDiff.addAspectJParameterDiffs(parameterDiff);
        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType("public");
        java6Method.setName("method");
        java6Method.setType("String");
        java6Method.addParameter(createParameter("String", "a"));
        java6Method.addParameter(createParameter("Integer", "b"));
        parameterDiff.setMethod(java6Method);
        parameterDiff.setParameter(createParameter("String", "a"));

        final AspectJParameterDiffImpl parameterDiff2 = new AspectJParameterDiffImpl();
        final AnnotationData annotationData2 = new AnnotationData("newid");
        annotationData2.addDeleted("First");
        parameterDiff2.setAnnotationData(annotationData2);
        aspectJDiff.addAspectJParameterDiffs(parameterDiff2);
        final Java6Method java6Method2 = new Java6Method();
        java6Method2.setAccessType("public");
        java6Method2.setName("method");
        java6Method2.setType("String");
        java6Method2.addParameter(createParameter("String", "a"));
        java6Method2.addParameter(createParameter("Integer", "b"));
        parameterDiff2.setMethod(java6Method2);
        parameterDiff2.setParameter(createParameter("Integer", "b"));

        final AspectJParameterDiffImpl parameterDiff3 = new AspectJParameterDiffImpl();
        final AnnotationData annotationData3 = new AnnotationData("4");
        annotationData3.addModified("First");
        parameterDiff3.setAnnotationData(annotationData3);
        aspectJDiff.addAspectJParameterDiffs(parameterDiff3);
        final Java6Method java6Method3 = new Java6Method();
        java6Method3.setAccessType("public");
        java6Method3.setName("method2");
        java6Method3.setType("String");
        java6Method3.addParameter(createParameter("String", "a"));
        java6Method3.addParameter(createParameter("Integer", "b"));
        parameterDiff3.setMethod(java6Method3);
        parameterDiff3.setParameter(createParameter("String", "a"));


        final String result = javaUpdater.updateJavaContent(javaFile, aspectJDiff);
        assertEquals(updatedJavaFile, result);
    }

    private JavaParameter createParameter(final String type, final String name) {
        final Java6Parameter java6Parameter = new Java6Parameter();
        java6Parameter.setName(name);
        java6Parameter.setType(type);
        return java6Parameter;
    }

}
