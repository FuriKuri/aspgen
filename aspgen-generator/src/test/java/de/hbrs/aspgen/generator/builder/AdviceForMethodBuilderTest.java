package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;

import de.hbrs.aspgen.api.generator.AdviceForMethod;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Method;

public class AdviceForMethodBuilderTest {
    @Test
    public void addOneAdvice() {
        final Java6Class javaClass = new Java6Class();
        javaClass.setClassName("Person");
        final Java6Method javaMethod = new Java6Method();
        javaMethod.setAccessType("public");
        javaMethod.setName("myMethod");
        javaMethod.setType("String");

        final AdviceForMethodBuilder builder = new AdviceForMethodBuilder(javaClass, javaMethod, "Dummy");
        final AdviceForMethod adviceForMethod = builder.appendNewBeforeAdvice("Dummy");
        adviceForMethod.addLine("System.out.println()");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"public:String:myMethod;;\")\n    before() : execution(public String Person.myMethod()) {\n"
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

        final AdviceForMethodBuilder builder = new AdviceForMethodBuilder(javaClass, javaMethod, "Dummy");
        final AdviceForMethod beforeAdvice = builder.appendNewBeforeAdvice("Dummy");
        beforeAdvice.addLine("System.out.println()");

        final AdviceForMethod afterAdvice = builder.appendNewAfterAdvice("Dummy");
        afterAdvice.addLine("System.out.println()");

        final AdviceForMethod aroundAdvice = builder.appendNewAroundAdvice("Dummy");
        aroundAdvice.addLine("System.out.println()");

        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"public:String:myMethod;;\")\n    before() : execution(public String Person.myMethod()) {\n"
                + "        System.out.println()\n"
                + "    }\n\n"
                + "    @Generated(id = 1, name = \"Dummy\", data = \"public:String:myMethod;;\")\n    after() returning(String returnValue) : execution(public String Person.myMethod()) {\n"
                + "        System.out.println()\n"
                + "    }\n\n"
                + "    @Generated(id = 1, name = \"Dummy\", data = \"public:String:myMethod;;\")\n    String around() : execution(public String Person.myMethod()) {\n"
                + "        System.out.println()\n"
                + "    }", builder.createContent("1", new HashMap<String, String>(), new LinkedList<String>()));
    }

}
