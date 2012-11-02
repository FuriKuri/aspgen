package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;

import de.hbrs.aspgen.api.generator.ConstructorForClass;
import de.hbrs.aspgen.jparser.type.Java6Class;

public class ConstructorForClassBuilderTest {
    @Test
    public void addOneMethod() {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final ConstructorForClassBuilder builder = new ConstructorForClassBuilder(java6Class, "Dummy");
        final ConstructorForClass consForClass = builder.appendNewConstructor("Dummy");
        consForClass.addLine("System.out.println();");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \";\")\n    public Person.new() {\n        System.out.println();\n    }", builder.createContent("1", new HashMap<String, String>(), new LinkedList<String>()));
    }

    @Test
    public void addTwoMethod() {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final ConstructorForClassBuilder builder = new ConstructorForClassBuilder(java6Class, "Dummy");
        final ConstructorForClass consForClass = builder.appendNewConstructor("Dummy");
        consForClass.addLine("System.out.println();");

        final ConstructorForClass consForClass2 = builder.appendNewConstructor("Dummy");
        consForClass2.addLine("System.out.println();");
        consForClass2.addParameter("String name");

        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \";\")\n    public Person.new() {\n        System.out.println();\n    }\n\n"
                + "    @Generated(id = 1, name = \"Dummy\", data = \";\")\n    public Person.new(String name) {\n        System.out.println();\n    }", builder.createContent("1", new HashMap<String, String>(), new LinkedList<String>()));
    }
}
