package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;

import de.hbrs.aspgen.api.generator.MethodForField;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Field;

public class MethodForFieldBuilderTest {
    @Test
    public void addOneMethod() {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final Java6Field java6Field = new Java6Field();
        java6Field.setName("name");
        java6Field.setType("String");

        final MethodForFieldBuilder builder = new MethodForFieldBuilder(java6Class, java6Field);
        final MethodForField methodForField = builder.appendNewMethod("Dummy");
        methodForField.setMethodDeclaration("public String get$fieldname$()");
        methodForField.addLine("System.out.println($fieldname$);");

        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    public String Person.getName() {\n        System.out.println(name);\n    }", builder.createContent("1", new HashMap<String, String>(), new LinkedList<String>()));
    }

    @Test
    public void addTwoMethods() {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final Java6Field java6Field = new Java6Field();
        java6Field.setName("name");
        java6Field.setType("String");

        final MethodForFieldBuilder builder = new MethodForFieldBuilder(java6Class, java6Field);
        final MethodForField methodForField = builder.appendNewMethod("Dummy");
        methodForField.setMethodDeclaration("public String get$fieldname$()");
        methodForField.addLine("System.out.println($fieldname$);");

        final MethodForField methodForField2 = builder.appendNewMethod("Dummy");
        methodForField2.setMethodDeclaration("public String set$fieldname$($fieldtype$ $fieldname$)");
        methodForField2.addLine("System.out.println($fieldname$);");

        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    public String Person.getName() {\n        System.out.println(name);\n    }\n\n"
                + "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    public String Person.setName(String name) {\n        System.out.println(name);\n    }", builder.createContent("1", new HashMap<String, String>(), new LinkedList<String>()));
    }
}
