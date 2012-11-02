package de.hbrs.aspgen.generator.container;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.jparser.type.Java6Method;
import de.hbrs.aspgen.jparser.type.Java6Parameter;

public class AroundAdviceForParameterContainerTest {

    @Test
    public void createLoggingAdviceForMethodForCarWithoutParameter() {
        final AroundAdviceForParameterContainer container = new AroundAdviceForParameterContainer();
        container.setOnType("Car");
        container.setName("Dummy");
        container.setMethod(createJavaMethod());
        container.setParameter(createJavaParameter());
        container.addLine("System.out.println(\"$parametername$\");");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;private:String:getPersonInfo;String:name,int:age;\")\n    String around(final String name) : execution(private String Car.getPersonInfo(String, int)) && args(name, *) {\n"
                + "        System.out.println(\"name\");\n"
                + "    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createAdviceWithThis() {
        final AroundAdviceForParameterContainer container = new AroundAdviceForParameterContainer();
        container.setOnType("Car");
        container.setName("Dummy");
        container.setMethod(createJavaMethod());
        container.setParameter(createJavaParameter());
        container.addLine("System.out.println(\"$parametername$\");");
        container.addThisParameter();
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;private:String:getPersonInfo;String:name,int:age;\")\n    String around(final Car car, final String name) : execution(private String Car.getPersonInfo(String, int)) && args(name, *) && this(car) {\n"
                + "        System.out.println(\"name\");\n"
                + "    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createAdviceWithProceedAndThisParameter() {
        final AroundAdviceForParameterContainer container = new AroundAdviceForParameterContainer();
        container.setOnType("Car");
        container.setName("Dummy");
        container.setMethod(createJavaMethod());
        container.setParameter(createJavaParameter());
        container.addLine("return $proceedcall$;");
        container.addThisParameter();
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;private:String:getPersonInfo;String:name,int:age;\")\n    String around(final Car car, final String name) : execution(private String Car.getPersonInfo(String, int)) && args(name, *) && this(car) {\n"
                + "        return proceed(car, name);\n"
                + "    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }


    @Test
    public void createAdviceWithProceed() {
        final AroundAdviceForParameterContainer container = new AroundAdviceForParameterContainer();
        container.setOnType("Car");
        container.setName("Dummy");
        container.setMethod(createJavaMethod());
        container.setParameter(createJavaParameter());
        container.addLine("return $proceedcall$;");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;private:String:getPersonInfo;String:name,int:age;\")\n    String around(final String name) : execution(private String Car.getPersonInfo(String, int)) && args(name, *) {\n"
                + "        return proceed(name);\n"
                + "    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }


    private JavaParameter createJavaParameter() {
        final Java6Parameter stringName = new Java6Parameter();
        stringName.setName("name");
        stringName.setType("String");
        return stringName;
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
