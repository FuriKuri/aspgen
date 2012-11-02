package de.hbrs.aspgen.merger.merge;

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;

public class AspectJMergerForChangedDeclare extends AbstractMergerTest {

    @Test
    public void changedBlock() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/declare/OldDeclareChangedBlock.aj",
                "src/test/resources/changed/declare/NewDeclare.aj",
                "src/test/resources/changed/declare/MergedDeclareChangedBlock.aj",
                getGeneratorDatasForDeclare());
    }

    public GeneratorDataImpl getGeneratorDatasForDeclare() {
        final AnnotationData data = new AnnotationData("2");
        data.addModified("softeningCatch1");
        final GeneratorDataImpl dataList = new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.log.Cache");
        dataList.addGeneratorData(data);
        return dataList;
    }

    public String orginalGeneratedContent() {
        return "public privileged aspect Person_NotNull {\n"
                + "    //@Generated(id = 2, name = \"softeningCatch1\", data = \"public:void:setNames;String:names;\")\n"
                + "    declare soft : Exception : execution(public void  Person.setNames(String));\n"
                + "}";
    }

}
