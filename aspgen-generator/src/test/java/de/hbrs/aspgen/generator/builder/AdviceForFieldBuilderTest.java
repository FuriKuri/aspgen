package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;

import de.hbrs.aspgen.api.generator.AdviceForField;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Field;

public class AdviceForFieldBuilderTest {
    @Test
    public void addOneAdvice() {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final Java6Field java6Field = new Java6Field();
        java6Field.setName("name");
        java6Field.setType("String");

        final AdviceForFieldBuilder builder = new AdviceForFieldBuilder(java6Class, java6Field);
        final AdviceForField adviceForField = builder.appendNewAdvice("Dummy");
        adviceForField.setAdviceDeclaration("before() : execution(public void $classname$.start())");
        adviceForField.addLine("System.out.println($fieldname$);");

        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    before() : execution(public void Person.start()) {\n        System.out.println(name);\n    }", builder.createContent("1", new HashMap<String, String>(), new LinkedList<String>()));
    }

    @Test
    public void addTwoAdvices() {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final Java6Field java6Field = new Java6Field();
        java6Field.setName("name");
        java6Field.setType("String");

        final AdviceForFieldBuilder builder = new AdviceForFieldBuilder(java6Class, java6Field);
        final AdviceForField adviceForField = builder.appendNewAdvice("Dummy");
        adviceForField.setAdviceDeclaration("before() : execution(public void $classname$.start())");
        adviceForField.addLine("System.out.println($fieldname$);");

        final AdviceForField adviceForField2 = builder.appendNewAdvice("Dummy");
        adviceForField2.setAdviceDeclaration("after() : execution(public void $classname$.start())");
        adviceForField2.addLine("System.out.println(\"after: \" + $fieldname$);");

        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    before() : execution(public void Person.start()) {\n        System.out.println(name);\n    }\n\n"
                + "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    after() : execution(public void Person.start()) {\n        System.out.println(\"after: \" + name);\n    }", builder.createContent("1", new HashMap<String, String>(), new LinkedList<String>()));

    }
}
