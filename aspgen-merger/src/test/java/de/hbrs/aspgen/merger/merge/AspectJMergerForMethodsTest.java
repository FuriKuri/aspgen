package de.hbrs.aspgen.merger.merge;

import java.io.IOException;

import org.junit.Test;

import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;


public class AspectJMergerForMethodsTest extends AbstractMergerTest {

    @Test
    public void mergeFileWithNoChangedMethodAndOneNewMethod() throws IOException {
        mergeFiles("src/test/resources/methods/oldFileWithtwoMethods.aj",
                "src/test/resources/methods/newFileWithThreeMethods.aj",
                "src/test/resources/methods/mergedFileWithThreeMethods.aj",
                new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean"));
    }

    @Test
    public void mergeFileWithNoChangedMethodAndOneDeletedMethod() throws IOException {
        mergeFiles("src/test/resources/methods/oldFileWithtwoMethods.aj",
                "src/test/resources/methods/newFileWithOneMethod.aj",
                "src/test/resources/methods/mergedFileWithOneMethod.aj",
                new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean"));
    }

    @Test
    public void mergeFileWithTwoChangedMethod() throws IOException {
        mergeFiles("src/test/resources/methods/oldFileWithtwoMethods.aj",
                "src/test/resources/methods/newFileWithTwoMethods.aj",
                "src/test/resources/methods/mergedFileWithTwoMethods.aj",
                new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean"));
    }
}
