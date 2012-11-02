package de.hbrs.aspgen.merger.merge;

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;

public class AspectJMergerForChangedCache extends AbstractMergerTest {

    @Test
    public void changedJavaDoc() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/cache/OldCacheChangedJavaDoc.aj",
                "src/test/resources/changed/cache/NewCache.aj",
                "src/test/resources/changed/cache/MergedCacheChangedJavaDoc.aj",
                getGeneratorDatasForCache());
    }

    @Test
    public void changedBlock() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/cache/OldCacheChangedBlock.aj",
                "src/test/resources/changed/cache/NewCache.aj",
                "src/test/resources/changed/cache/MergedCacheChangedBlock.aj",
                getGeneratorDatasForCache());
    }

    @Test
    public void changedAccess() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());
        mergeFiles("src/test/resources/changed/cache/OldCacheChangedAccess.aj",
                "src/test/resources/changed/cache/NewCache.aj",
                "src/test/resources/changed/cache/MergedCacheChangedAccess.aj",
                getGeneratorDatasForCache());
    }

    public GeneratorDataImpl getGeneratorDatasForCache() {
        final AnnotationData data = new AnnotationData("2");
        data.addModified("Cache Field");
        final GeneratorDataImpl dataList = new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.log.Cache");
        dataList.addGeneratorData(data);
        return dataList;
    }

    public String orginalGeneratedContent() {
        return "public privileged aspect Person_NotNull {\n"
                + "    @Generated(id = 2, name = \"Cache Field\", data = \"public:void:method;String:name;\")\n"
                + "    private Cache Person.methodString = new Cache();\n"
                + "}";
    }

}
