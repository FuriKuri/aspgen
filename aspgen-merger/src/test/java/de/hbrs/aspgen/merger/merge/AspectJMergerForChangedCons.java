package de.hbrs.aspgen.merger.merge;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.api.generator.DynamicPart;
import de.hbrs.aspgen.api.generator.DynamicPartsInBlocks;
import de.hbrs.aspgen.generator.process.BlockImpl;
import de.hbrs.aspgen.generator.process.DynamicPartImpl;
import de.hbrs.aspgen.generator.process.DynamicPartInBlockImpl;
import de.hbrs.aspgen.jparser.type.Java6Field;
import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;

public class AspectJMergerForChangedCons extends AbstractMergerTest {

    @Test
    public void changedAccessVisibility() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/cons/OldConsWithChangedAccess.aj",
                "src/test/resources/changed/cons/NewCons.aj",
                "src/test/resources/changed/cons/MergedConsWithChangedAccess.aj",
                getGeneratorDatasForCons());
    }

    @Test
    public void changedParameter() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/cons/OldConsWithChangedParameter.aj",
                "src/test/resources/changed/cons/NewCons.aj",
                "src/test/resources/changed/cons/MergedConsWithChangedParameter.aj",
                getGeneratorDatasForCons());
    }

    @Test
    public void changedBlock() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/cons/OldConsWithChangedBlock.aj",
                "src/test/resources/changed/cons/NewCons.aj",
                "src/test/resources/changed/cons/MergedConsWithChangedBlock.aj",
                getGeneratorDatasForCons());
    }

    @Test
    public void changedAccessWithExcludedFields() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(excludedFieldGeneratedContent());
        when(generatorManager.getDynamicParts(argument.capture(), Matchers.anyString())).thenReturn(createDynamicPartsInBlocksForCons());
        mergeFiles("src/test/resources/changed/cons/OldConsWithExcludedFieldsChangedAccess.aj",
                "src/test/resources/changed/cons/NewConsWithExcludedFields.aj",
                "src/test/resources/changed/cons/MergedConsWithExcludedFieldsAndChangedAccess.aj",
                getGeneratorDatasForCons());
    }

    public GeneratorDataImpl getGeneratorDatasForCons() {
        final AnnotationData data = new AnnotationData("1");
        data.addModified("Cons");
        final GeneratorDataImpl dataList = new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.cons.Cons");
        dataList.addGeneratorData(data);
        return dataList;
    }

    public String orginalGeneratedContent() {
        return "public privileged aspect Person_Constructor {\n"
                + "    @Generated(id = 1, name = \"Cons\", data = \"int:age,String:name;\")\n"
                + "    public Person.new(int age, String name) {\n"
                + "        System.out.println(\"Init\");\n"
                + "        System.out.println(\"age\");\n"
                + "        System.out.println(\"name\");\n"
                + "    }\n"
                + "}";
    }

    public String excludedFieldGeneratedContent() {
        return "public privileged aspect Person_Constructor {\n"
                + "    @Generated(id = 1, name = \"Cons\", data = \"int:age,String:name;\")\n"
                + "    public Person.new(int age, String name) {\n"
                + "        System.out.println(\"Init\");\n"
                + "        System.out.println(\"name\");\n"
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


        final BlockImpl block = new BlockImpl("Cons");
        block.setDynamicParts(parts);

        final DynamicPartInBlockImpl dynamicPartInBlock = new DynamicPartInBlockImpl("1");
        dynamicPartInBlock.add(block);

        final List<DynamicPartsInBlocks> dynamicPartInBlockImpls = new LinkedList<>();
        dynamicPartInBlockImpls.add(dynamicPartInBlock);
        return dynamicPartInBlockImpls;

    }
}
