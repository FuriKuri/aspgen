package de.hbrs.aspgen.generator.container;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.jparser.type.Java6Method;
import de.hbrs.aspgen.jparser.type.Java6Parameter;

public class BeforeAdviceContainerTest {
    @Test
    public void createLoggingAdviceForMethodForCarWithoutParameter() {
        final BeforeAdvicePerMethodContainer container = new BeforeAdvicePerMethodContainer();
        container.setOnType("Car");
        container.setName("Dummy");
        container.setMethod(createSimpleVoidMethod());
        container.addLine("System.out.println(\"Hello World\");");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"public:void:simpleMethod;;\")\n    before() : execution(public void Car.simpleMethod()) {\n"
                + "        System.out.println(\"Hello World\");\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createLoggingAdviceForMethodForPersonWithoutParameter() {
        final BeforeAdvicePerMethodContainer container = new BeforeAdvicePerMethodContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setMethod(createStringMethod());
        container.addLine("System.out.println(\"Hello World\");");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"private:String:getName;;\")\n    before() : execution(private String Person.getName()) {\n"
                + "        System.out.println(\"Hello World\");\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createLoggingAdviceForMethodForPersonWitharameter() {
        final BeforeAdvicePerMethodContainer container = new BeforeAdvicePerMethodContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setMethod(createJavaMethod());
        container.setNotExcludedParameters(createJavaMethod().getParameters());
        container.addLine("System.out.println(\"Hello World\");");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"private:String:getPersonInfo;String:name,int:age;\")\n    before(final String name, final int age) : execution(private String Person.getPersonInfo(String, int)) && args(name, age) {\n"
                + "        System.out.println(\"Hello World\");\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createLoggingAdviceForMethodForPersonWitharametersAndDynamicPart() {
        final BeforeAdvicePerMethodContainer container = new BeforeAdvicePerMethodContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setMethod(createJavaMethod());
        container.setNotExcludedParameters(createJavaMethod().getParameters());
        container.addLine("System.out.println(\"Begin\");");
        container.addLineForeachParameter("System.out.println(\"$parametername$=\" + $parametername$);");
        container.addLine("System.out.println(\"End\");");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"private:String:getPersonInfo;String:name,int:age;\")\n    before(final String name, final int age) : execution(private String Person.getPersonInfo(String, int)) && args(name, age) {\n"
                + "        System.out.println(\"Begin\");\n"
                + "        System.out.println(\"name=\" + name);\n"
                + "        System.out.println(\"age=\" + age);\n"
                + "        System.out.println(\"End\");\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createLoggingAdviceWithAnnotationsForMethod() {
        final BeforeAdvicePerMethodContainer container = new BeforeAdvicePerMethodContainer();
        container.setOnType("Car");
        container.setName("Dummy");
        container.setMethod(createSimpleVoidMethod());
        container.addAnnotation("@Ignore");
        container.addAnnotation("@Deprecated");
        container.addLine("System.out.println(\"Hello World\");");
        final String expectedResult = "    @Ignore\n    @Deprecated\n    @Generated(id = 1, name = \"Dummy\", data = \"public:void:simpleMethod;;\")\n"
                + "    before() : execution(public void Car.simpleMethod()) {\n"
                + "        System.out.println(\"Hello World\");\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createLoggingAdviceWithJavaDocForMethod() {
        final BeforeAdvicePerMethodContainer container = new BeforeAdvicePerMethodContainer();
        container.setOnType("Car");
        container.setName("Dummy");
        container.setJavaDoc("First Line\nSecond Line");
        container.setMethod(createSimpleVoidMethod());
        container.addLine("System.out.println(\"Hello World\");");
        final String expectedResult = "    /**\n     * First Line\n     * Second Line\n     */\n    @Generated(id = 1, name = \"Dummy\", data = \"public:void:simpleMethod;;\")\n"
                + "    before() : execution(public void Car.simpleMethod()) {\n"
                + "        System.out.println(\"Hello World\");\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createAdviceWithThis() {
        final BeforeAdvicePerMethodContainer container = new BeforeAdvicePerMethodContainer();
        container.setOnType("Car");
        container.setName("Dummy");
        container.setMethod(createSimpleVoidMethod());
        container.addThisParameter();
        container.addLine("System.out.println(\"Hello World\");");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"public:void:simpleMethod;;\")\n    before(final Car car) : execution(public void Car.simpleMethod()) && this(car) {\n"
                + "        System.out.println(\"Hello World\");\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    private JavaMethod createSimpleVoidMethod() {
        final Java6Method java6Method = new Java6Method();
        java6Method.setName("simpleMethod");
        java6Method.setType("void");
        java6Method.setAccessType("public");
        return java6Method;
    }

    private JavaMethod createStringMethod() {
        final Java6Method java6Method = new Java6Method();
        java6Method.setName("getName");
        java6Method.setType("String");
        java6Method.setAccessType("private");
        return java6Method;
    }

    private JavaMethod createJavaMethod() {
        final Java6Method java6Method = new Java6Method();
        java6Method.setName("getPersonInfo");
        java6Method.setType("String");
        final Java6Parameter stringName = new Java6Parameter();
        stringName.setName("name");
        stringName.setType("String");
        java6Method.addParameter(stringName);
        final Java6Parameter intAge = new Java6Parameter();
        intAge.setName("age");
        intAge.setType("int");
        java6Method.addParameter(intAge);
        java6Method.setAccessType("private");
        return java6Method;
    }
}
