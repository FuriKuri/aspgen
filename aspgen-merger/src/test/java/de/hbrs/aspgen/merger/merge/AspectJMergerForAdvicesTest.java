package de.hbrs.aspgen.merger.merge;

import java.io.IOException;

import org.junit.Test;

import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;


public class AspectJMergerForAdvicesTest extends AbstractMergerTest {
    @Test
    public void mergeFileWithNoChangedAdvicesAndOneNewAdvice() throws IOException {
        mergeFiles("src/test/resources/advices/oldFileWithTwoAdvices.aj",
                "src/test/resources/advices/newFileWithThreeAdvices.aj",
                "src/test/resources/advices/mergedFileWithThreeAdvices.aj",
                new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean"));
    }

    @Test
    public void mergeFileWithNoChangedAdvicesAndOneNewAdviceAndDeleted() throws IOException {
        mergeFiles("src/test/resources/advices/oldFileWithTwoAdvices.aj",
                "src/test/resources/advices/newFileWithTwoAdvices.aj",
                "src/test/resources/advices/mergedFileWithTwoAdvices.aj",
                new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean"));
    }

    @Test
    public void mergeFileWithChangedAdvices() throws IOException {
        mergeFiles("src/test/resources/advices/oldFileWithTwoAdvices.aj",
                "src/test/resources/advices/newFileWithTwoChangedAdvices.aj",
                "src/test/resources/advices/mergedFileWithTwoChangedAdvices.aj",
                new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean"));
    }
}
