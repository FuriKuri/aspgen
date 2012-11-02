package de.hbrs.aspgen.merger.merge;

import java.io.IOException;

import org.junit.Test;

import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;


public class MergeFilesWithChangedProperties extends AbstractMergerTest {

    @Test
    public void changePropertyInAdvice() throws IOException {
        mergeFiles("src/test/resources/changedProperties/oldFileWithTwoAdvices.aj",
                "src/test/resources/changedProperties/newFileWithTwoAdvices.aj",
                "src/test/resources/changedProperties/mergedFileWithTwoAdvices.aj",
                getGeneratorDatasForLogging());
    }

    public GeneratorDataImpl getGeneratorDatasForLogging() {
        final AnnotationData data = new AnnotationData("3");
        data.addModified("Logging");
        final GeneratorDataImpl dataList = new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.logging.Logging");
        dataList.addGeneratorData(data);
        return dataList;
    }

    @Test
    public void changePropertyInMethod() throws IOException {
        mergeFiles("src/test/resources/changedProperties/oldFileWithtwoMethods.aj",
                "src/test/resources/changedProperties/newFileWithTwoMethods.aj",
                "src/test/resources/changedProperties/mergedFileWithTwoMethods.aj",
                getGeneratorDatasForJavaBean());
    }

    public GeneratorDataImpl getGeneratorDatasForJavaBean() {
        final AnnotationData data = new AnnotationData("2");
        data.addModified("Setter");
        final GeneratorDataImpl dataList = new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean");
        dataList.addGeneratorData(data);
        return dataList;
    }

    @Test
    public void changePropertyInField() throws IOException {
        mergeFiles("src/test/resources/changedProperties/oldFileWithThreeFields.aj",
                "src/test/resources/changedProperties/newFileWithThreeFields.aj",
                "src/test/resources/changedProperties/mergedFileWithThreeFields.aj",
                getGeneratorDatasForField());
    }

    public GeneratorDataImpl getGeneratorDatasForField() {
        final AnnotationData data = new AnnotationData("3");
        data.addModified("AgeField");
        final AnnotationData data2 = new AnnotationData("1");
        data2.addModified("Logger");
        final GeneratorDataImpl dataList = new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.field.Field");
        dataList.addGeneratorData(data);
        dataList.addGeneratorData(data2);
        return dataList;
    }

    @Test
    public void changePropertyInCons() throws IOException {
        mergeFiles("src/test/resources/changedProperties/oldFileWithTwoConstructors.aj",
                "src/test/resources/changedProperties/newFileWithTwoConstructors.aj",
                "src/test/resources/changedProperties/mergedFileWithTwoConstructors.aj",
                getGeneratorDatasForCons());
    }

    public GeneratorDataImpl getGeneratorDatasForCons() {
        final AnnotationData data = new AnnotationData("1");
        data.addModified("Cons1");
        final GeneratorDataImpl dataList = new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.cons.Cons");
        dataList.addGeneratorData(data);
        return dataList;
    }

}
