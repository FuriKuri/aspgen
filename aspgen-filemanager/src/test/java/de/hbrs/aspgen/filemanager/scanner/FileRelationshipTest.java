package de.hbrs.aspgen.filemanager.scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.hbrs.aspgen.testhelper.DummyLogService;

public class FileRelationshipTest {
    private final JavaToAspectJRelationshipService fileRelation = new JavaToAspectJRelationshipService(new DummyLogService());

    @Test
    public void getAspectJFilesForJavaFile() {
        final List<String> generatedFileNames = Arrays.asList("ToString", "JavaBean");
        final List<File> result = fileRelation.getAssociatedFiles(
                new File("src/test/resources/associated/Person.java"), generatedFileNames);
        assertEquals(2, result.size());
        assertTrue(result.contains(new File("src/test/resources/associated/Person_JavaBean.aj")));
        assertTrue(result.contains(new File("src/test/resources/associated/Person_ToString.aj")));
    }

    @Test
    public void getOnlyAspectJFilesForOneGenerator() {
        final List<String> generatedFileNames = Arrays.asList("ToString");
        assertExistingToStringFile(generatedFileNames);
    }

    @Test
    public void getOnlyExistingAspectJFiles() {
        final List<String> generatedFileNames = Arrays.asList("ToString", "Singleton");
        assertExistingToStringFile(generatedFileNames);
    }

    private void assertExistingToStringFile(
            final List<String> generatedFileNames) {
        final List<File> result = fileRelation.getAssociatedFiles(
                new File("src/test/resources/associated/Person.java"), generatedFileNames);
        assertEquals(1, result.size());
        assertEquals(new File("src/test/resources/associated/Person_ToString.aj"), result.get(0));
    }

    @Test
    public void getRootFileForAspectJToStringFile() {
        final File aspectJToString = new File("src/test/resources/associated/Person_ToString.aj");
        assertEquals(new File("src/test/resources/associated/Person.java"), fileRelation.getRootFile(aspectJToString));
    }

    @Test
    public void getRootFileForAspectJJavaBeanFile() {
        final File aspectJToString = new File("src/test/resources/associated/Home_JavaBean.aj");
        assertEquals(new File("src/test/resources/associated/Home.java"), fileRelation.getRootFile(aspectJToString));
    }

    public void noExistingRootFile() {
        assertNull(fileRelation.getRootFile(new File("src/test/resources/associated/Car_JavaBean.aj")));
    }
}
