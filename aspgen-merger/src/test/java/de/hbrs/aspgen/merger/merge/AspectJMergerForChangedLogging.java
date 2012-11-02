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
import de.hbrs.aspgen.jparser.type.Java6Parameter;
import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;

public class AspectJMergerForChangedLogging extends AbstractMergerTest {

    @Test
    public void changedJavaDoc() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/logging/OldLoggingChangedJavaDoc.aj",
                "src/test/resources/changed/logging/NewLogging.aj",
                "src/test/resources/changed/logging/MergedLoggingChangedJavaDoc.aj",
                getGeneratorDatasForLogging());
    }

    @Test
    public void changedBlock() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/logging/OldLoggingChangedBlock.aj",
                "src/test/resources/changed/logging/NewLogging.aj",
                "src/test/resources/changed/logging/MergedLoggingChangedBlock.aj",
                getGeneratorDatasForLogging());
    }

    @Test
    public void changedAdvice() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/logging/OldLoggingChangedAdvice.aj",
                "src/test/resources/changed/logging/NewLogging.aj",
                "src/test/resources/changed/logging/MergedLoggingChangedAdvice.aj",
                getGeneratorDatasForLogging());
    }

    public GeneratorDataImpl getGeneratorDatasForLogging() {
        final AnnotationData data = new AnnotationData("2");
        data.addModified("Logging");
        final GeneratorDataImpl dataList = new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.logging.Logging");
        dataList.addGeneratorData(data);
        return dataList;
    }

    public String orginalGeneratedContent() {
        return "public privileged aspect Person_ToString {\n"
                + "    @Generated(id = 2, name = \"Logging\", data = \"public:void:method;Integer:a,Integer:b;\")\n"
                + "    before(Integer a, Integer b, String msg) : execution(public void Person.method(Integer, Integer, String)) && args(a, b, msg) {\n"
                + "        System.out.println(a);\n"
                + "        System.out.println(b);\n"
                + "        System.out.println(msg);\n"
                + "    }\n"
                + "}\n";
    }

    @Test
    public void changedJavaDocAndExcludedParameters() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(withExcludedFields());
        when(generatorManager.getDynamicParts(argument.capture(), Matchers.anyString(), Matchers.anyString())).thenReturn(createDynamicPartsInBlocksForLogging());

        mergeFiles("src/test/resources/changed/logging/OldLoggingChangedJavaDocAndExcludedParameter.aj",
                "src/test/resources/changed/logging/NewLoggingWithExcludedParameter.aj",
                "src/test/resources/changed/logging/MergedLoggingChangedJavaDocAndExcludedParameters.aj",
                getGeneratorDatasForLogging());
    }

    public String withExcludedFields() {
        return "public privileged aspect Person_ToString {\n"
                + "    @Generated(id = 2, name = \"Logging\", data = \"public:void:method;Integer:a,Integer:b;\")\n"
                + "    before(Integer a, Integer b, String msg) : execution(public void Person.method(Integer, Integer, String)) && args(a, b, msg) {\n"
                + "        System.out.println(a);\n"
                + "        System.out.println(msg);\n"
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
        field4.setName("msg");
        field4.setType("String");
        final DynamicPartImpl dynamicPart4 = new DynamicPartImpl();
        dynamicPart4.setParameter(field4);
        final List<String> line4 = new LinkedList<>();
        line4.add("System.out.println(msg);");
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
