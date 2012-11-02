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
import de.hbrs.aspgen.merger.diff.AdviceDiffManager;
import de.hbrs.aspgen.merger.diff.AspectJDiffCreator;
import de.hbrs.aspgen.merger.diff.ConstructorDiffManager;
import de.hbrs.aspgen.merger.diff.DeclareDiffManager;
import de.hbrs.aspgen.merger.diff.FieldDiffManager;
import de.hbrs.aspgen.merger.diff.MethodDiffManager;

public class AspectJDiffManagerCacheTest {
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
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/cache/Person_Cache.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        assertEquals(0, aspectJDiffManager.createDiff(fileContent, "").getAspectJFieldDiffs().size());
    }

    @Test
    public void changedBlock() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/cache/Person_Cache_Changed_Block.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        assertEquals("2", aspectJDiffManager.createDiff(fileContent, "").getAspectJMethodDiffs().get(0).getAnnotationData().getId());
        assertEquals(1, aspectJDiffManager.createDiff(fileContent, "").getAspectJMethodDiffs().get(0).getAnnotationData().getModified().size());
        assertEquals("Cache Field", aspectJDiffManager.createDiff(fileContent, "").getAspectJMethodDiffs().get(0).getAnnotationData().getModified().get(0));    }

    public String orginalGeneratedContent() {
        return "public privileged aspect Person_NotNull {\n"
                + "    @Generated(id = 2, name = \"Cache Field\", data = \"public:void:method;String:name,Integer:a;\")\n"
                + "    private Cache Person.methodStringInteger = new Cache();\n"
                + "}";
    }
}
