package de.hbrs.aspgen.merger.diff;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import de.hbrs.aspgen.ajparser.AspectJ6Parser;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.generator.DynamicPart;
import de.hbrs.aspgen.api.generator.DynamicPartsInBlocks;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.parser.AspectJParser;
import de.hbrs.aspgen.api.parser.JavaParser;
import de.hbrs.aspgen.generator.process.BlockImpl;
import de.hbrs.aspgen.generator.process.DynamicPartImpl;
import de.hbrs.aspgen.generator.process.DynamicPartInBlockImpl;
import de.hbrs.aspgen.jparser.Java6Parser;
import de.hbrs.aspgen.jparser.factory.Java7Factory;
import de.hbrs.aspgen.jparser.type.Java6Parameter;
import de.hbrs.aspgen.merger.diff.AdviceDiffManager;
import de.hbrs.aspgen.merger.diff.AspectJDiffCreator;
import de.hbrs.aspgen.merger.diff.ConstructorDiffManager;
import de.hbrs.aspgen.merger.diff.DeclareDiffManager;
import de.hbrs.aspgen.merger.diff.FieldDiffManager;
import de.hbrs.aspgen.merger.diff.MethodDiffManager;

public class AspectJDiffManagerLoggingTest {
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
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/logging/Person_Logging.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        assertEquals(0, aspectJDiffManager.createDiff(fileContent, "").getAspectJMethodDiffs().size());
    }

    @Test
    public void changedBlock() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/logging/Person_Logging_Changed_Block.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        assertEquals("2", aspectJDiffManager.createDiff(fileContent, "").getAspectJMethodDiffs().get(0).getAnnotationData().getId());
        assertEquals(1, aspectJDiffManager.createDiff(fileContent, "").getAspectJMethodDiffs().get(0).getAnnotationData().getModified().size());
        assertEquals("Logging", aspectJDiffManager.createDiff(fileContent, "").getAspectJMethodDiffs().get(0).getAnnotationData().getModified().get(0));
    }

    @Test
    public void excludedParamter() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/logging/Person_Logging_Excluded_Parameter.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContentExcludedParameter());
        when(generatorManager.getDynamicParts(argument.capture(), Matchers.anyString(), Matchers.anyString())).thenReturn(createDynamicPartsInBlocksForLogging());

        assertEquals(0, aspectJDiffManager.createDiff(fileContent, "").getAspectJMethodDiffs().size());
        assertEquals("2", aspectJDiffManager.createDiff(fileContent, "").getAspectJParameterDiffs().get(0).getAnnotationData().getId());
        assertEquals(1, aspectJDiffManager.createDiff(fileContent, "").getAspectJParameterDiffs().get(0).getAnnotationData().getExcluded().size());
        assertEquals("Logging", aspectJDiffManager.createDiff(fileContent, "").getAspectJParameterDiffs().get(0).getAnnotationData().getExcluded().get(0));
    }

    public String orginalGeneratedContent() {
        return "public privileged aspect Person_ToString {\n"
                + "    @Generated(id = 2, name = \"Logging\", data = \"public:void:method;Integer:a,Integer:b,String:c;\")\n"
                + "    before(Integer a, Integer b, String c) : execution(public void Person.method(Integer, Integer, String)) && args(a, b, c) {\n"
                + "        System.out.println(a);\n"
                + "        System.out.println(b);\n"
                + "        System.out.println(c);\n"
                + "    }\n"
                + "}\n";
    }

    public String orginalGeneratedContentExcludedParameter() {
        return "public privileged aspect Person_ToString {\n"
                + "    @Generated(id = 2, name = \"Logging\", data = \"public:void:method;Integer:a,Integer:b,String:c;\")\n"
                + "    before(Integer a, Integer b, String c) : execution(public void Person.method(Integer, Integer, String)) && args(a, b, c) {\n"
                + "        System.out.println(a);\n"
                + "        System.out.println(c);\n"
                + "    }\n"
                + "}\n";
    }

    private List<DynamicPartsInBlocks> createDynamicPartsInBlocksForLogging() {
        final List<DynamicPart> parts = new LinkedList<>();

        final Java6Parameter field = new Java6Parameter();
        field.setName("a");
        field.setType("Integer");
        final DynamicPartImpl dynamicPart = new DynamicPartImpl();
        dynamicPart.setParameter(field);
        final List<String> line = new LinkedList<>();
        line.add("System.out.println(a);");
        dynamicPart.setDynamicLines(line);
        parts.add(dynamicPart);

        final Java6Parameter field3 = new Java6Parameter();
        field3.setName("b");
        field3.setType("Integer");
        final DynamicPartImpl dynamicPart3 = new DynamicPartImpl();
        dynamicPart3.setParameter(field3);
        final List<String> line3 = new LinkedList<>();
        line3.add("System.out.println(b);");
        dynamicPart3.setDynamicLines(line3);
        parts.add(dynamicPart3);

        final Java6Parameter field4 = new Java6Parameter();
        field4.setName("c");
        field4.setType("String");
        final DynamicPartImpl dynamicPart4 = new DynamicPartImpl();
        dynamicPart4.setParameter(field4);
        final List<String> line4 = new LinkedList<>();
        line4.add("System.out.println(c);");
        dynamicPart4.setDynamicLines(line4);
        parts.add(dynamicPart4);

        final BlockImpl block = new BlockImpl("Logging");
        block.setDynamicParts(parts);

        final DynamicPartInBlockImpl dynamicPartInBlock = new DynamicPartInBlockImpl("2");
        dynamicPartInBlock.add(block);

        final List<DynamicPartsInBlocks> dynamicPartInBlockImpls = new LinkedList<>();
        dynamicPartInBlockImpls.add(dynamicPartInBlock);
        return dynamicPartInBlockImpls;

    }
}
