package de.hbrs.aspgen.merger.diff;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import de.hbrs.aspgen.ajparser.AspectJ6Parser;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.parser.AspectJParser;
import de.hbrs.aspgen.api.parser.JavaParser;
import de.hbrs.aspgen.jparser.Java6Parser;
import de.hbrs.aspgen.jparser.factory.Java7Factory;

public class AspectJDiffManagerJavaBeanTest {
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
                new DeclareDiffManager(ajParser, generatorManager, javaFactory));    }

    @Test
    public void noChanges() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/javabean/Person_JavaBean.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(
                "",
                orginalGeneratedContentFor1Setter(),
                orginalGeneratedContentFor1Getter(),
                orginalGeneratedContentFor2Setter(),
                orginalGeneratedContentFor2Getter()
                );
        assertEquals(0, aspectJDiffManager.createDiff(fileContent, "").getAspectJFieldDiffs().size());
    }

    @Test
    public void changedMethods() throws IOException {
        final String fileContent = FileUtils.readFileToString(new File("src/test/resources/ajdiff/javabean/Person_JavaBean_ChangedMethods.aj"));
        final ArgumentCaptor<JavaClass> argument = ArgumentCaptor.forClass(JavaClass.class);
        when(generatorManager.generateContentForGenerator(argument.capture(), Matchers.anyString())).thenReturn(
                "",
                orginalGeneratedContentFor1Setter(),
                orginalGeneratedContentFor1Getter(),
                orginalGeneratedContentFor2Setter(),
                orginalGeneratedContentFor2Getter()
                );

        final AspectJDiffImpl result = aspectJDiffManager.createDiff(fileContent, "");


        assertEquals(2, result.getAspectJFieldDiffs().size());
        assertEquals("2", result.getAspectJFieldDiffs().get(0).getAnnotationData().getId());
        assertEquals("Setter", result.getAspectJFieldDiffs().get(0).getAnnotationData().getModified().get(0));

        assertEquals("3", result.getAspectJFieldDiffs().get(1).getAnnotationData().getId());
        assertEquals("Getter", result.getAspectJFieldDiffs().get(1).getAnnotationData().getModified().get(0));


    }

    public String orginalGeneratedContentFor1Setter() {
        return "public privileged aspect Person_JavaBean {\n"
                + "    @Generated(id = 2, name = \"Setter\", data = \"int:age;\")\n"
                + "    public void Person.setAge(int age) {\n"
                + "        this.age = age;\n"
                + "    }\n"
                + "}";
    }

    public String orginalGeneratedContentFor1Getter() {
        return "public privileged aspect Person_JavaBean {\n"
                + "    @Generated(id = 2, name = \"Getter\", data = \"int:age;\")\n"
                + "    public int Person.getAge() {\n"
                + "        return age;\n"
                + "    }\n"
                + "}";
    }

    public String orginalGeneratedContentFor2Setter() {
        return "public privileged aspect Person_JavaBean {\n"
                + "    @Generated(id = 3, name = \"Setter\", data = \"String:name;\")\n"
                + "    public void Person.setName(String name) {\n"
                + "        this.name = name;\n"
                + "    }\n"
                + "}";
    }

    public String orginalGeneratedContentFor2Getter() {
        return "public privileged aspect Person_JavaBean {\n"
                + "    @Generated(id = 3, name = \"Getter\", data = \"String:name;\")\n"
                + "    public String Person.getName() {\n"
                + "        return name;\n"
                + "    }\n"
                + "}";
    }

}
