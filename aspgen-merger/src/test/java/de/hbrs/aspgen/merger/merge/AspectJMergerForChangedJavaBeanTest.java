package de.hbrs.aspgen.merger.merge;

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;

public class AspectJMergerForChangedJavaBeanTest extends AbstractMergerTest {

    @Test
    public void mergedMethodWithChangedAccessField() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());

        mergeFiles("src/test/resources/changed/javabean/OldJavaBeanForTwoFieldsChangedAccess.aj",
                "src/test/resources/changed/javabean/NewJavaBeanForTwoFields.aj",
                "src/test/resources/changed/javabean/MergedJavaBeanForTwoFieldsChangedAccess.aj",
                getGeneratorDatasForJavaBean());
    }

    @Test
    public void mergedMethodWithChangedName() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());

        mergeFiles("src/test/resources/changed/javabean/OldJavaBeanForTwoFieldsChangedName.aj",
                "src/test/resources/changed/javabean/NewJavaBeanForTwoFields.aj",
                "src/test/resources/changed/javabean/MergedJavaBeanForTwoFieldsChangedName.aj",
                getGeneratorDatasForJavaBean());
    }

    @Test
    public void mergedMethodWithChangedBlock() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContent());

        mergeFiles("src/test/resources/changed/javabean/OldJavaBeanForTwoFieldsChangedBlock.aj",
                "src/test/resources/changed/javabean/NewJavaBeanForTwoFields.aj",
                "src/test/resources/changed/javabean/MergedJavaBeanForTwoFieldsChangedBlock.aj",
                getGeneratorDatasForJavaBean());
    }

    public GeneratorDataImpl getGeneratorDatasForJavaBean() {
        final GeneratorDataImpl dataList = new GeneratorDataImpl("de.hbrs.aspgen.generator.javabean.JavaBean", "Person");
        final AnnotationData data = new AnnotationData("3");
        data.addModified("Getter");
        dataList.addGeneratorData(data);
        return dataList;
    }

    public String orginalGeneratedContent() {
        return "public privileged aspect Person_JavaBean {\n"
            + "    @Generated(id = 3, name = \"Getter\", data = \"Object:firstname;\")\n"
            + "    public Object Person.getFirstname() {\n"
            + "        return firstname;\n"
            + "    }\n"
            + "}";
    }

    @Test
    public void mergedMethodWithChangedTwoParameter() throws IOException {
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(orginalGeneratedContentForFirstSetter(), orginalGeneratedContentForSecondSetter());

        mergeFiles("src/test/resources/changed/javabean/OldJavaBeanForTwoFieldsChangedTwoParameters.aj",
                "src/test/resources/changed/javabean/NewJavaBeanForTwoNewFields.aj",
                "src/test/resources/changed/javabean/MergedJavaBeanForTwoFieldsChangedTwoParameters.aj",
                getGeneratorDatasForJavaBeanModSetters());
    }

    public GeneratorDataImpl getGeneratorDatasForJavaBeanModSetters() {
        final GeneratorDataImpl dataList = new GeneratorDataImpl("de.hbrs.aspgen.generator.javabean.JavaBean", "Person");
        final AnnotationData data = new AnnotationData("3");
        data.addModified("Setter");
        dataList.addGeneratorData(data);

        final AnnotationData data2 = new AnnotationData("2");
        data2.addModified("Setter");
        dataList.addGeneratorData(data2);
        return dataList;
    }

    public String orginalGeneratedContentForSecondSetter() {
        return "public privileged aspect Person_JavaBean {\n"
            + "    @Generated(id = 2, name = \"Setter\", data = \"int:age;\")\n"
            + "    public void Person.setAge(int age) {\n"
            + "        this.age = age;\n"
            + "    }\n"
            + "}";
    }

    public String orginalGeneratedContentForFirstSetter() {
        return "public privileged aspect Person_JavaBean {\n"
            + "    @Generated(id = 3, name = \"Setter\", data = \"Object:firstname;\")\n"
            + "    public void Person.setFirstname(Object firstname) {\n"
            + "        this.firstname = firstname;\n"
            + "    }\n"
            + "}";
    }
}
