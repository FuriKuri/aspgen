package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;

import de.hbrs.aspgen.api.generator.AdviceForParameter;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Method;
import de.hbrs.aspgen.jparser.type.Java6Parameter;

public class AdviceForParameterBuilderTest {
    @Test
    public void addOneAdvice() {
        final Java6Class javaClass = new Java6Class();
        javaClass.setClassName("Person");
        final Java6Method javaMethod = new Java6Method();
        javaMethod.setAccessType("public");
        javaMethod.setName("myMethod");
        javaMethod.setType("String");
        final Java6Parameter java6Parameter = new Java6Parameter();
        java6Parameter.setName("name");
        java6Parameter.setType("String");
        javaMethod.addParameter(java6Parameter);

        final AdviceForParameterBuilder builder = new AdviceForParameterBuilder(javaClass, javaMethod, java6Parameter);
        final AdviceForParameter adviceForMethod = builder.appendNewBeforeAdviceForParameter("Dummy");
        adviceForMethod.addLine("System.out.println()");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"String:name;public:String:myMethod;String:name;\")\n    before(final String name) : execution(public String Person.myMethod(String)) && args(name) {\n"
                + "        System.out.println()\n"
                + "    }", builder.createContent("1", new HashMap<String, String>(), new LinkedList<String>()));
    }

    @Test
    public void addThreeAdvices() {
        final Java6Class javaClass = new Java6Class();
        javaClass.setClassName("Person");
        final Java6Method javaMethod = new Java6Method();
        javaMethod.setAccessType("public");
        javaMethod.setName("myMethod");
        javaMethod.setType("String");
        final Java6Parameter java6Parameter = new Java6Parameter();
        java6Parameter.setName("name");
        java6Parameter.setType("String");
        javaMethod.addParameter(java6Parameter);

        final AdviceForParameterBuilder builder = new AdviceForParameterBuilder(javaClass, javaMethod, java6Parameter);
        final AdviceForParameter beforeAdvice = builder.appendNewBeforeAdviceForParameter("Dummy");
        beforeAdvice.addLine("System.out.println()");

        final AdviceForParameter afterAdvice = builder.appendNewAfterAdviceForParameter("Dummy");
        afterAdvice.addLine("System.out.println()");

        final AdviceForParameter aroundAdvice = builder.appendNewAroundAdviceForParameter("Dummy");
        aroundAdvice.addLine("System.out.println()");

        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"String:name;public:String:myMethod;String:name;\")\n    before(final String name) : execution(public String Person.myMethod(String)) && args(name) {\n"
                + "        System.out.println()\n"
                + "    }\n\n"
                + "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;public:String:myMethod;String:name;\")\n    after(final String name) returning(String returnValue) : execution(public String Person.myMethod(String)) && args(name) {\n"
                + "        System.out.println()\n"
                + "    }\n\n"
                + "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;public:String:myMethod;String:name;\")\n    String around(final String name) : execution(public String Person.myMethod(String)) && args(name) {\n"
                + "        System.out.println()\n"
                + "    }", builder.createContent("1", new HashMap<String, String>(), new LinkedList<String>()));
    }
}
