package de.hbrs.aspgen.merger.merge;

import java.io.IOException;

import org.junit.Test;

import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;


public class AspectJMergerForRefreshTest extends AbstractMergerTest {


    @Test
    public void mergeFileWithNoChangedMethodAndOneNewMethod() throws IOException {
        mergeFiles("src/test/resources/refresh/oldFileWithtwoMethods.aj",
                "src/test/resources/refresh/newFileWithTwoMethods.aj",
                "src/test/resources/refresh/mergedFileWithTwoMethods.aj",
                new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean"));
    }
}
