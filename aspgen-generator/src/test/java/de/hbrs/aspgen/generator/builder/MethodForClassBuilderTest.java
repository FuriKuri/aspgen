package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;

import de.hbrs.aspgen.api.generator.MethodForClass;
import de.hbrs.aspgen.jparser.type.Java6Class;

public class MethodForClassBuilderTest {
    @Test
    public void addOneMethod() {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final MethodForClassBuilder builder = new MethodForClassBuilder(java6Class, "de.hbrs.Dummy");
        final MethodForClass methodForClass = builder.appendNewMethod("Dummy");
        methodForClass.setMethodDeclaration("public void method()");
        methodForClass.addLine("System.out.println();");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \";\")\n    public void Person.method() {\n        System.out.println();\n    }", builder.createContent("1", new HashMap<String, String>(), new LinkedList<String>()));
    }

    @Test
    public void addTwoMethod() {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final MethodForClassBuilder builder = new MethodForClassBuilder(java6Class, "de.hbrs.Dummy");
        final MethodForClass methodForClass = builder.appendNewMethod("Dummy");
        methodForClass.setMethodDeclaration("public void method()");
        methodForClass.addLine("System.out.println();");

        final MethodForClass methodForClass2 = builder.appendNewMethod("Dummy");
        methodForClass2.setMethodDeclaration("public void method2()");
        methodForClass2.addLine("System.out.println();");

        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \";\")\n    public void Person.method() {\n        System.out.println();\n    }\n\n"
                + "    @Generated(id = 1, name = \"Dummy\", data = \";\")\n    public void Person.method2() {\n        System.out.println();\n    }", builder.createContent("1", new HashMap<String, String>(), new LinkedList<String>()));
    }

    // TODO eclude testen, auch mit mehreren namen
}
