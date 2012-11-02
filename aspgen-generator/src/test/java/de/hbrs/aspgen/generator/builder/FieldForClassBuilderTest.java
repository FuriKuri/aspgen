package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;

import de.hbrs.aspgen.api.generator.FieldForClass;
import de.hbrs.aspgen.jparser.type.Java6Class;

public class FieldForClassBuilderTest {
    @Test
    public void addOneField() {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final FieldForClassBuilder builder = new FieldForClassBuilder(java6Class);
        final FieldForClass fieldForClass = builder.appendNewField("Dummy");
        fieldForClass.setContent("public void int i = 3;");


        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \";\")\n    public void int Person.i = 3;", builder.createContent("1", new HashMap<String, String>(), new LinkedList<String>()));
    }

    @Test
    public void addTwoFields() {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final FieldForClassBuilder builder = new FieldForClassBuilder(java6Class);
        final FieldForClass fieldForClass = builder.appendNewField("Dummy");
        fieldForClass.setContent("public void int i = 3;");

        final FieldForClass fieldForClass2 = builder.appendNewField("Dummy");
        fieldForClass2.setContent("private String name;");

        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \";\")\n    public void int Person.i = 3;\n\n    @Generated(id = 1, name = \"Dummy\", data = \";\")\n    private String Person.name;", builder.createContent("1", new HashMap<String, String>(), new LinkedList<String>()));
    }
}
