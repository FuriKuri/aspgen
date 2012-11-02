package de.hbrs.aspgen.merger.diff;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import de.hbrs.aspgen.ajparser.AspectJ6Parser;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.parser.AspectJParser;
import de.hbrs.aspgen.api.parser.JavaParser;
import de.hbrs.aspgen.jparser.Java6Parser;
import de.hbrs.aspgen.jparser.factory.Java7Factory;

public class AspectJDiffManagerDeletedJavaBeanTest {
    private AspectJDiffCreator aspectJDiffManager;
    private GeneratorManager generatorManager;

    @Before
    public void init() {
        generatorManager = mock(GeneratorManager.class);
        final AspectJParser ajParser = new AspectJ6Parser();
        final JavaParser javaParser = new Java6Parser();
        final JavaFactory javaFactory = new Java7Factory();
        aspectJDiffManager = new AspectJDiffCreator(
                ajParser, javaParser, generatorManager, javaFactory,
                new MethodDiffManager(ajParser, generatorManager, javaFactory),
                new ConstructorDiffManager(ajParser, generatorManager, javaFactory),
                new AdviceDiffManager(ajParser, generatorManager, javaFactory),
                new FieldDiffManager(ajParser, generatorManager, javaFactory),
                new DeclareDiffManager(ajParser, generatorManager, javaFactory));    }

    @Test
    public void noChanges() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/deleted/javabean/Person_JavaBean.aj"));
        final String generatedFromJavaFile = FileUtils.readFileToString(new File("src/test/resources/ajdiff/deleted/javabean/Person_JavaBean_Original.aj"));
        final String generatedFromJavaFile2 = FileUtils.readFileToString(new File("src/test/resources/ajdiff/deleted/javabean/Person_JavaBean.aj"));

        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(generatedFromJavaFile, generatedFromJavaFile2);
        assertEquals(0, aspectJDiffManager.createDiff(fileContent, "").getAspectJFieldDiffs().size());
    }

    @Test
    public void changedBlock() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/deleted/javabean/Person_JavaBean_Deleted_Blocks.aj"));
        final String generatedFromJavaFile = FileUtils.readFileToString(new File("src/test/resources/ajdiff/deleted/javabean/Person_JavaBean_Original.aj"));
        final String generatedFromJavaFile2 = FileUtils.readFileToString(new File("src/test/resources/ajdiff/deleted/javabean/Person_JavaBean.aj"));

        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(generatedFromJavaFile, generatedFromJavaFile2);

        final AspectJDiffImpl result = aspectJDiffManager.createDiff(fileContent, "");
        assertEquals(2, result.getAspectJFieldDiffs().size());
        assertEquals("age", result.getAspectJFieldDiffs().get(0).getField().getName());
        assertEquals("Setter", result.getAspectJFieldDiffs().get(0).getAnnotationData().getDeleted().get(0));

        assertEquals("name", result.getAspectJFieldDiffs().get(1).getField().getName());
        assertEquals("Getter", result.getAspectJFieldDiffs().get(1).getAnnotationData().getDeleted().get(0));
    }
}
