package de.hbrs.aspgen.merger.merge;

import java.io.IOException;

import org.junit.Test;

import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;


public class AspectJMergerForImportsTest extends AbstractMergerTest {
    @Test
    public void mergeFileWithOneNotUsedImport() throws IOException {
        mergeFiles("src/test/resources/imports/oldFileWithTwoImports.aj",
                "src/test/resources/imports/newFileWithTwoImports.aj",
                "src/test/resources/imports/mergedFileWithOneImport.aj",
                new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean"));
    }

    @Test
    public void mergeFileWithOneNotUsedImportAndTwoNewImports() throws IOException {
        mergeFiles("src/test/resources/imports/oldFileWithTwoImports.aj",
                "src/test/resources/imports/newFileWithThreeImports.aj",
                "src/test/resources/imports/mergedFileWithThreeImports.aj",
                new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.javabean.JavaBean"));
    }
}
