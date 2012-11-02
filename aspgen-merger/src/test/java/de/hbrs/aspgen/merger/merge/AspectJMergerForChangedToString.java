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

public class AspectJMergerForChangedToString extends AbstractMergerTest {

    @Test
    public void changedAccessVisibility() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/tostring/OldToStringChangedModifer.aj",
                "src/test/resources/changed/tostring/NewToString.aj",
                "src/test/resources/changed/tostring/MergedToStringChangedModifer.aj",
                getGeneratorDatasForToString());
    }

    @Test
    public void changedMethodName() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/tostring/OldToStringChangedName.aj",
                "src/test/resources/changed/tostring/NewToString.aj",
                "src/test/resources/changed/tostring/MergedToStringChangedName.aj",
                getGeneratorDatasForToString());
    }

    @Test
    public void changedJavaDoc() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/tostring/OldToStringChangedJavaDoc.aj",
                "src/test/resources/changed/tostring/NewToString.aj",
                "src/test/resources/changed/tostring/MergedToStringChangedJavaDoc.aj",
                getGeneratorDatasForToString());
    }

    @Test
    public void changedBlock() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/tostring/OldToStringChangedBlock.aj",
                "src/test/resources/changed/tostring/NewToString.aj",
                "src/test/resources/changed/tostring/MergedToStringChangedBlock.aj",
                getGeneratorDatasForToString());
    }

    @Test
    public void blockWithExcludedField() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);

        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(excludedFieldGeneratedContent());
        when(generatorManager.getDynamicParts(argument.capture(), Matchers.anyString())).thenReturn(createDynamicPartsInBlocksForToSring());
        mergeFiles("src/test/resources/changed/tostring/OldToStringWithExcludedField.aj",
                "src/test/resources/changed/tostring/NewToStringWithExcludedField.aj",
                "src/test/resources/changed/tostring/MergedToStringWithExcludedField.aj",
                getGeneratorDatasForToString());
    }

    @Test
    public void blockWithExcludedFieldAndChangedAccess() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);

        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(excludedFieldGeneratedContent());
        when(generatorManager.getDynamicParts(argument.capture(), Matchers.anyString())).thenReturn(createDynamicPartsInBlocksForToSring());
        mergeFiles("src/test/resources/changed/tostring/OldToStringWithExcludedFieldAndChangedAccess.aj",
                "src/test/resources/changed/tostring/NewToStringWithExcludedField.aj",
                "src/test/resources/changed/tostring/MergedToStringWithExcludedFieldAndChangedAccess.aj",
                getGeneratorDatasForToString());
    }

    @Test
    public void onlyUpdateMethodWhichAreModified() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);

        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(excludedFieldGeneratedContent());
        when(generatorManager.getDynamicParts(argument.capture(), Matchers.anyString())).thenReturn(createDynamicPartsInBlocksForToSring());
        mergeFiles("src/test/resources/changed/tostring/OldToStringWithChangedBlockAndNoChangedBlock.aj",
                "src/test/resources/changed/tostring/NewToStringWithTwoBlocks.aj",
                "src/test/resources/changed/tostring/MergedToStringWithChangedBlockAndNoChangedBlock.aj",
                getGeneratorDatasForToString());
    }

    public GeneratorDataImpl getGeneratorDatasForToString() {
        final AnnotationData data = new AnnotationData("1");
        data.addModified("ToString");
        final GeneratorDataImpl dataList = new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.tostring.ToString");
        dataList.addGeneratorData(data);
        return dataList;
    }

    public String orginalGeneratedContent() {
        return "public aspect Person_ToString { \n @Generated(id = 1, name = \"ToString\", data = \"int:age,String:name,int:count,String:hellos;\")\n"
                + "    public String Person.toString() {\n"
                + "        String result = \"\";\n"
                + "        result += \"age = \" + age + \" \";\n"
                + "        result += \"name = \" + name + \" \";\n"
                + "        result += \"count = \" + count + \" \";\n"
                + "        result += \"hellos = \" + hellos + \" \";\n"
                + "        return result;\n"
                + "    }}";
    }

    public String excludedFieldGeneratedContent() {
        return "public aspect Person_ToString { \n @Generated(id = 1, name = \"ToString\", data = \"int:age,String:name,int:count,String:hellos;\")\n"
                + "    public String Person.toString() {\n"
                + "        String result = \"\";\n"
                + "        result += \"age = \" + age + \" \";\n"
                + "        result += \"count = \" + count + \" \";\n"
                + "        result += \"hellos = \" + hellos + \" \";\n"
                + "        return result;\n"
                + "    }}";
    }

    private List<DynamicPartsInBlocks> createDynamicPartsInBlocksForToSring() {
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
        field2.setName("name");
        field2.setType("String");
        final DynamicPartImpl dynamicPart2 = new DynamicPartImpl();
        dynamicPart2.setField(field2);
        final List<String> line2 = new LinkedList<>();
        line2.add("result += \"name = \" + name + \" \";");
        dynamicPart2.setDynamicLines(line2);
        parts.add(dynamicPart2);

        final Java6Field field3 = new Java6Field();
        field3.setName("count");
        field3.setType("int");
        final DynamicPartImpl dynamicPart3 = new DynamicPartImpl();
        dynamicPart3.setField(field3);
        final List<String> line3 = new LinkedList<>();
        line3.add("result += \"count = \" + count + \" \";");
        dynamicPart3.setDynamicLines(line3);
        parts.add(dynamicPart3);

        final Java6Field field4 = new Java6Field();
        field4.setName("hellos");
        field4.setType("String");
        final DynamicPartImpl dynamicPart4 = new DynamicPartImpl();
        dynamicPart4.setField(field4);
        final List<String> line4 = new LinkedList<>();
        line4.add("result += \"hellos = \" + hellos + \" \";");
        dynamicPart4.setDynamicLines(line4);
        parts.add(dynamicPart4);

        final BlockImpl block = new BlockImpl("ToString");
        block.setDynamicParts(parts);

        final DynamicPartInBlockImpl dynamicPartInBlock = new DynamicPartInBlockImpl("1");
        dynamicPartInBlock.add(block);

        final List<DynamicPartsInBlocks> dynamicPartInBlockImpls = new LinkedList<>();
        dynamicPartInBlockImpls.add(dynamicPartInBlock);
        return dynamicPartInBlockImpls;

    }
}
