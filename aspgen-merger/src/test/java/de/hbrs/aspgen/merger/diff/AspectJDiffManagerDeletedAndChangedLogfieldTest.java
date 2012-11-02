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

public class AspectJDiffManagerDeletedAndChangedLogfieldTest {
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
    public void changedAndDeletedBlock() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/changedAndDeleted/logfield/Person_Logging_Deleted_Block.aj"));
        final String generatedFromJavaFile = FileUtils.readFileToString(new File("src/test/resources/ajdiff/changedAndDeleted/logfield/Person_Logging_Original.aj"));
        final String generatedFromJavaFile2 = FileUtils.readFileToString(new File("src/test/resources/ajdiff/changedAndDeleted/logfield/Person_Logging.aj"));

        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(generatedFromJavaFile, generatedFromJavaFile2);

        final AspectJDiffImpl result = aspectJDiffManager.createDiff(fileContent, "");

        assertEquals("1", result.getAspectJClassDiff().getAnnotationData().getId());
        assertEquals(1, result.getAspectJClassDiff().getAnnotationData().getDeleted().size());
        assertEquals("Logger2", result.getAspectJClassDiff().getAnnotationData().getDeleted().get(0));

        assertEquals(1, result.getAspectJClassDiff().getAnnotationData().getModified().size());
        assertEquals("Logger", result.getAspectJClassDiff().getAnnotationData().getModified().get(0));
    }
}
