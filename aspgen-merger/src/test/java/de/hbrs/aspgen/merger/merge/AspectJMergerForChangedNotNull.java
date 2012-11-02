package de.hbrs.aspgen.merger.merge;

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;

public class AspectJMergerForChangedNotNull extends AbstractMergerTest {

    @Test
    public void changedJavaDoc() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/notnull/OldNotNullChangedJavaDoc.aj",
                "src/test/resources/changed/notnull/NewNotNull.aj",
                "src/test/resources/changed/notnull/MergedNotNullChangedJavaDoc.aj",
                getGeneratorDatasForLogging());
    }

    @Test
    public void changedBlock() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/notnull/OldNotNullChangedBlock.aj",
                "src/test/resources/changed/notnull/NewNotNull.aj",
                "src/test/resources/changed/notnull/MergedNotNullChangedBlock.aj",
                getGeneratorDatasForLogging());
    }

    public GeneratorDataImpl getGeneratorDatasForLogging() {
        final AnnotationData data = new AnnotationData("2");
        data.addModified("NotNull");
        final GeneratorDataImpl dataList = new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.notnull.NotNull");
        dataList.addGeneratorData(data);
        return dataList;
    }

    public String orginalGeneratedContent() {
        return "public privileged aspect Person_NotNull {\n"
                + "    @Generated(id = 2, name = \"NotNull\", data = \"String:age;public:void:method;String:age,String:amount,int:a;\")\n"
                + "    before(String age) : execution(public void Person.method(String, String, int)) && args(age, *, *) {\n"
                + "        if (age == null) {\n"
                + "            throw new RuntimeException(\"age is null\");\n"
                + "        }\n"
                + "    }\n"
                + "}";
    }

}
