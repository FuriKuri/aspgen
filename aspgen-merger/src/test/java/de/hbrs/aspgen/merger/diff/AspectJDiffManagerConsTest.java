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
import de.hbrs.aspgen.merger.diff.AdviceDiffManager;
import de.hbrs.aspgen.merger.diff.AspectJDiffCreator;
import de.hbrs.aspgen.merger.diff.ConstructorDiffManager;
import de.hbrs.aspgen.merger.diff.DeclareDiffManager;
import de.hbrs.aspgen.merger.diff.FieldDiffManager;
import de.hbrs.aspgen.merger.diff.MethodDiffManager;

public class AspectJDiffManagerConsTest {
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
                new DeclareDiffManager(ajParser, generatorManager, javaFactory));
    }

    @Test
    public void noChanges() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/cons/Person_Cons.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        assertNull(aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff());
    }

    @Test
    public void changedBlock() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/cons/Person_Cons_Changed_Block.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        assertEquals("1", aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff().getAnnotationData().getId());
        assertEquals(1, aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff().getAnnotationData().getModified().size());
        assertEquals("Cons", aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff().getAnnotationData().getModified().get(0));
    }

    @Test
    public void excludedField() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/cons/Person_Cons_Exclude_Field.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContentWithExcludedFields());
        when(generatorManager.getDynamicParts(argument.capture(), Matchers.anyString())).thenReturn(createDynamicPartsInBlocksForCons());

        assertEquals("1", aspectJDiffManager.createDiff(fileContent, "").getAspectJFieldDiffs().get(0).getAnnotationData().getId());
        assertEquals(1, aspectJDiffManager.createDiff(fileContent, "").getAspectJFieldDiffs().get(0).getAnnotationData().getExcluded().size());
        assertEquals("Cons", aspectJDiffManager.createDiff(fileContent, "").getAspectJFieldDiffs().get(0).getAnnotationData().getExcluded().get(0));
    }

    @Test
    public void excludedFieldchangedBlock() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/cons/Person_Cons_Exclude_Field_Changed_Block.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        assertEquals("1", aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff().getAnnotationData().getId());
        assertEquals(1, aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff().getAnnotationData().getModified().size());
        assertEquals("Cons", aspectJDiffManager.createDiff(fileContent, "").getAspectJClassDiff().getAnnotationData().getModified().get(0));
    }

    public String orginalGeneratedContent() {
        return "public aspect Person_ToString {\n "
                + "    @Generated(id = 1, name = \"Cons\", data = \"int:age,String:name,int:count;\")\n"
                + "    public Person.new(int age, String name, int count) {\n"
                + "        System.out.println(\"Init\");\n"
                + "        System.out.println(\"age\");\n"
                + "        System.out.println(\"name\");\n"
                + "        System.out.println(\"count\");\n"
                + "    }\n"
                + "}";
    }

    public String orginalGeneratedContentWithExcludedFields() {
        return "public aspect Person_ToString {\n "
                + "    @Generated(id = 1, name = \"Cons\", data = \"int:age,String:name,int:count;\")\n"
                + "    public Person.new(int age, String name, int count) {\n"
                + "        System.out.println(\"Init\");\n"
                + "        System.out.println(\"age\");\n"
                + "        System.out.println(\"count\");\n"
                + "    }\n"
                + "}";
    }

    private List<DynamicPartsInBlocks> createDynamicPartsInBlocksForCons() {
        final List<DynamicPart> parts = new LinkedList<>();

        final Java6Field field = new Java6Field();
        field.setName("age");
        field.setType("int");
        final DynamicPartImpl dynamicPart = new DynamicPartImpl();
        dynamicPart.setField(field);
        final List<String> line = new LinkedList<>();
        line.add("System.out.println(\"age\")");
        dynamicPart.setDynamicLines(line);
        parts.add(dynamicPart);

        final Java6Field field2 = new Java6Field();
        field2.setName("name");
        field2.setType("String");
        final DynamicPartImpl dynamicPart2 = new DynamicPartImpl();
        dynamicPart2.setField(field2);
        final List<String> line2 = new LinkedList<>();
        line2.add("System.out.println(\"name\")");
        dynamicPart2.setDynamicLines(line2);
        parts.add(dynamicPart2);

        final Java6Field field3 = new Java6Field();
        field3.setName("int");
        field3.setType("count");
        final DynamicPartImpl dynamicPart3 = new DynamicPartImpl();
        dynamicPart3.setField(field3);
        final List<String> line3 = new LinkedList<>();
        line3.add("System.out.println(\"count\")");
        dynamicPart3.setDynamicLines(line3);
        parts.add(dynamicPart3);

        final BlockImpl block = new BlockImpl("Cons");
        block.setDynamicParts(parts);

        final DynamicPartInBlockImpl dynamicPartInBlock = new DynamicPartInBlockImpl("1");
        dynamicPartInBlock.add(block);

        final List<DynamicPartsInBlocks> dynamicPartInBlockImpls = new LinkedList<>();
        dynamicPartInBlockImpls.add(dynamicPartInBlock);
        return dynamicPartInBlockImpls;

    }
}
