package de.hbrs.aspgen.merger.merge;

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;

public class AspectJMergerForChangedOsgiService extends AbstractMergerTest {

    @Test
    public void changedJavaDoc() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/osgiservice/OldOsgiServiceChangedJavaDoc.aj",
                "src/test/resources/changed/osgiservice/NewOsgiService.aj",
                "src/test/resources/changed/osgiservice/MergedOsgiServiceChangedJavaDoc.aj",
                getGeneratorDatasForLogging());
    }

    @Test
    public void changedBlock() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/osgiservice/OldOsgiServiceChangedBlock.aj",
                "src/test/resources/changed/osgiservice/NewOsgiService.aj",
                "src/test/resources/changed/osgiservice/MergedOsgiServiceChangedBlock.aj",
                getGeneratorDatasForLogging());
    }

    public GeneratorDataImpl getGeneratorDatasForLogging() {
        final AnnotationData data = new AnnotationData("2");
        data.addModified("Service Init");
        final GeneratorDataImpl dataList = new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.osgiservice.OsgiService");
        dataList.addGeneratorData(data);
        return dataList;
    }

    public String orginalGeneratedContent() {
        return "public privileged aspect Person_NotNull {\n"
                + "    @Generated(id = 2, name = \"Service Init\", data = \"LogService:service;\")\n"
                + "    before(BundleContext context, Person activator) : execution(public void start(BundleContext)) && args(context) && this(activator) {\n"
                + "        activator.service = (LogService) context.getService(context.getServiceReference(LogService.class.getName()));\n"
                + "    }\n"
                + "}";
    }

}
