package de.hbrs.aspgen.merger.merge;

import java.io.IOException;

import org.junit.Test;

import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;


public class AspectJMergerForFieldsTest extends AbstractMergerTest {

    @Test
    public void mergeFileWithNoChangedFieldsAndTwoNewField() throws IOException {
        mergeFiles("src/test/resources/fields/oldFileWithThreeFields.aj",
                "src/test/resources/fields/newFileWithFiveFields.aj",
                "src/test/resources/fields/mergedFileWithFiveFields.aj",
                new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean"));
    }

    @Test
    public void mergeFileWithNoChangedFieldAndOneDeletedField() throws IOException {
        mergeFiles("src/test/resources/fields/oldFileWithThreeFields.aj",
                "src/test/resources/fields/newFileWithTwoFields.aj",
                "src/test/resources/fields/mergedFileWithTwoFields.aj",
                new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean"));
    }

    @Test
    public void mergeFileWithTwoChangedFields() throws IOException {
        mergeFiles("src/test/resources/fields/oldFileWithThreeFields.aj",
                "src/test/resources/fields/newFileWithThreeFields.aj",
                "src/test/resources/fields/mergedFileWithThreeFields.aj",
                new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean"));
    }
}
