package de.hbrs.aspgen.merger.merge;

import java.io.IOException;

import org.junit.Test;

import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;


public class AspectJMergerForConstructorsTest extends AbstractMergerTest {
    @Test
    public void mergeFileWithOneNewConstructor() throws IOException {
        mergeFiles("src/test/resources/constructors/oldFileWithTwoConstructors.aj",
                "src/test/resources/constructors/newFileWithThreeConstructors.aj",
                "src/test/resources/constructors/mergedFileWithThreeConstructors.aj",
                new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean"));
    }

    @Test
    public void mergeFileWithChangedConstructor() throws IOException {
        mergeFiles("src/test/resources/constructors/oldFileWithTwoConstructors.aj",
                "src/test/resources/constructors/newFileWithTwoConstructors.aj",
                "src/test/resources/constructors/mergedFileWithTwoConstructors.aj",
                new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean"));
    }

    @Test
    public void mergeFileWithChangedAndRemovedConstructor() throws IOException {
        mergeFiles("src/test/resources/constructors/oldFileWithTwoConstructors.aj",
                "src/test/resources/constructors/newFileWithOneConstructors.aj",
                "src/test/resources/constructors/mergedFileWithOneConstructors.aj",
                new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean"));
    }
}
