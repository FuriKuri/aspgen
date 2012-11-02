package de.hbrs.aspgen.merger.diff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import de.hbrs.aspgen.jparser.type.Java6Field;

public class AspectJDiffManagerToStringTest {
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
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/tostring/Person_ToString.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent("ToString"));
        assertNull(aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff());
    }

    @Test
    public void changedAccess() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/tostring/Person_ToString_Changed_Access.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent("ToString"));
        assertEquals("1", aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff().getAnnotationData().getId());
        assertEquals(1, aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff().getAnnotationData().getModified().size());
        assertEquals("ToString", aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff().getAnnotationData().getModified().get(0));
    }

    @Test
    public void excludedField() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/tostring/Person_ToString_Excluded_Field.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContentWithExcludedFields("ToString"));
        when(generatorManager.getDynamicParts(argument.capture(), Matchers.anyString())).thenReturn(createDynamicPartsInBlocksForToSring("ToString"));

        assertEquals("1", aspectJDiffManager.createDiff(fileContent, "").getAspectJFieldDiffs().get(0).getAnnotationData().getId());
        assertEquals(1, aspectJDiffManager.createDiff(fileContent, "").getAspectJFieldDiffs().get(0).getAnnotationData().getExcluded().size());
        assertEquals("ToString", aspectJDiffManager.createDiff(fileContent, "").getAspectJFieldDiffs().get(0).getAnnotationData().getExcluded().get(0));
    }

    @Test
    public void excludedFieldAndChangedBlock() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/tostring/Person_ToString_Excluded_Field_And_Changed_Block.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContentWithExcludedFields("ToString"));
        when(generatorManager.getDynamicParts(argument.capture(), Matchers.anyString())).thenReturn(createDynamicPartsInBlocksForToSring("ToString"));

        assertEquals(0, aspectJDiffManager.createDiff(fileContent, "").getAspectJFieldDiffs().size());
        assertEquals("1", aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff().getAnnotationData().getId());
        assertEquals(1, aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff().getAnnotationData().getModified().size());
        assertEquals("ToString", aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff().getAnnotationData().getModified().get(0));
    }

    @Test
    public void changedBlockAndOneExcludedField() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/tostring/Person_ToString_Three_Methods.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(
                "",
                orginalGeneratedContent("ToString"),
                orginalGeneratedContent("ToString2"),
                orginalGeneratedContentWithExcludedFields("ToString3"));

        when(generatorManager.getDynamicParts(argument.capture(), Matchers.anyString())).thenReturn(
                createDynamicPartsInBlocksForToSring("ToString"),
                createDynamicPartsInBlocksForToSring("ToString2"),
                createDynamicPartsInBlocksForToSring("ToString3"));


        final AspectJDiffImpl result = aspectJDiffManager.createDiff(fileContent, "");

        assertEquals("1", result.getAspectJClassDiff().getAnnotationData().getId());
        assertEquals(1, result.getAspectJClassDiff().getAnnotationData().getModified().size());
        assertEquals("ToString", result.getAspectJClassDiff().getAnnotationData().getModified().get(0));
        assertEquals(1, result.getAspectJFieldDiffs().get(0).getAnnotationData().getExcluded().size());
        assertEquals("ToString3", result.getAspectJFieldDiffs().get(0).getAnnotationData().getExcluded().get(0));
    }

    public String orginalGeneratedContent(final String name) {
        return "public aspect Person_ToString { \n @Generated(id = 1, name = \"" + name + "\", data = \"int:age,int:counter,String:name;\")\n"
                + "    public String Person.toString() {\n"
                + "        String result = \"\";\n"
                + "        result += \"age = \" + age + \" \";\n"
                + "        result += \"counter = \" + counter + \" \";\n"
                + "        result += \"name = \" + name + \" \";\n"
                + "        return result;\n"
                + "    }}";
    }

    public String orginalGeneratedContentWithExcludedFields(final String name) {
        return "public aspect Person_ToString { \n @Generated(id = 1, name = \"" + name + "\", data = \"int:age,int:counter,String:name;\")\n"
                + "    public String Person.toString() {\n"
                + "        String result = \"\";\n"
                + "        result += \"age = \" + age + \" \";\n"
                + "        result += \"name = \" + name + \" \";\n"
                + "        return result;\n"
                + "    }}";
    }

    private List<DynamicPartsInBlocks> createDynamicPartsInBlocksForToSring(final String name) {
        final List<DynamicPart> parts = new LinkedList<>();

        final Java6Field field = new Java6Field();
        field.setName("age");
        field.setType("int");
        final DynamicPartImpl dynamicPart = new DynamicPartImpl();
        dynamicPart.setField(field);
        final List<String> line = new LinkedList<>();
        line.add("result += \"age = \" + age + \" \";");
        dynamicPart.setDynamicLines(line);
        parts.add(dynamicPart);

        final Java6Field field2 = new Java6Field();
        field2.setName("counter");
        field2.setType("int");
        final DynamicPartImpl dynamicPart2 = new DynamicPartImpl();
        dynamicPart2.setField(field2);
        final List<String> line2 = new LinkedList<>();
        line2.add("result += \"counter = \" + counter + \" \";");
        dynamicPart2.setDynamicLines(line2);
        parts.add(dynamicPart2);

        final Java6Field field3 = new Java6Field();
        field3.setName("name");
        field3.setType("String");
        final DynamicPartImpl dynamicPart3 = new DynamicPartImpl();
        dynamicPart3.setField(field3);
        final List<String> line3 = new LinkedList<>();
        line3.add("result += \"name = \" + name + \" \";");
        dynamicPart3.setDynamicLines(line3);
        parts.add(dynamicPart3);


        final BlockImpl block = new BlockImpl(name);
        block.setDynamicParts(parts);

        final DynamicPartInBlockImpl dynamicPartInBlock = new DynamicPartInBlockImpl("1");
        dynamicPartInBlock.add(block);

        final List<DynamicPartsInBlocks> dynamicPartInBlockImpls = new LinkedList<>();
        dynamicPartInBlockImpls.add(dynamicPartInBlock);
        return dynamicPartInBlockImpls;

    }
}
