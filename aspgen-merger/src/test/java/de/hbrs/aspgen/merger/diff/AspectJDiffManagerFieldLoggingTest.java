package de.hbrs.aspgen.merger.diff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import de.hbrs.aspgen.merger.diff.AdviceDiffManager;
import de.hbrs.aspgen.merger.diff.AspectJDiffCreator;
import de.hbrs.aspgen.merger.diff.ConstructorDiffManager;
import de.hbrs.aspgen.merger.diff.DeclareDiffManager;
import de.hbrs.aspgen.merger.diff.FieldDiffManager;
import de.hbrs.aspgen.merger.diff.MethodDiffManager;

public class AspectJDiffManagerFieldLoggingTest {
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
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/logfield/Person_Logging.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        assertNull(aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff());
    }

    @Test
    public void changedBlock() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/logfield/Person_Logging_Changed_Block.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        assertEquals("1", aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff().getAnnotationData().getId());
        assertEquals(1, aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff().getAnnotationData().getModified().size());
        assertEquals("Logger", aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff().getAnnotationData().getModified().get(0));    }

    public String orginalGeneratedContent() {
        return "public privileged aspect Person_NotNull {\n"
                + "    @Generated(id = 1, name = \"Logger\", data = \";\")\n"
                + "    private String Person.logger = \"Logger.get(Person.class)\";\n"
                + "}";
    }
}
