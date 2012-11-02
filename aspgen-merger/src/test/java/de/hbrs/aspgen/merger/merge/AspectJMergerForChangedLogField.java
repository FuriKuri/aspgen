package de.hbrs.aspgen.merger.merge;

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;

public class AspectJMergerForChangedLogField extends AbstractMergerTest {

    @Test
    public void changedJavaDoc() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/logfield/OldLoggingChangedJavaDoc.aj",
                "src/test/resources/changed/logfield/NewLogging.aj",
                "src/test/resources/changed/logfield/MergedLoggingChangedJavaDoc.aj",
                getGeneratorDatasForLogging());
    }

    @Test
    public void changedBlock() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/logfield/OldLoggingChangedBlock.aj",
                "src/test/resources/changed/logfield/NewLogging.aj",
                "src/test/resources/changed/logfield/MergedLoggingChangedBlock.aj",
                getGeneratorDatasForLogging());
    }

    public GeneratorDataImpl getGeneratorDatasForLogging() {
        final AnnotationData data = new AnnotationData("1");
        data.addModified("Logger");
        final GeneratorDataImpl dataList = new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.log.LogField");
        dataList.addGeneratorData(data);
        return dataList;
    }

    public String orginalGeneratedContent() {
        return "public privileged aspect Person_NotNull {\n"
                + "    @Generated(id = 1, name = \"Logger\", data = \";\")\n"
                + "    private String Person.logger = \"Logger.get(Person.class)\";\n"
                + "}";
    }

}
