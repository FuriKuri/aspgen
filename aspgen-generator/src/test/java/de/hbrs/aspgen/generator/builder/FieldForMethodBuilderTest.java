package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.generator.FieldForMethod;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Method;
import de.hbrs.aspgen.jparser.type.Java6Parameter;

public class FieldForMethodBuilderTest {
    @Test
    public void addOneField() {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final FieldForMethodBuilder builder = new FieldForMethodBuilder(java6Class, createJavaMethod());
        final FieldForMethod fieldForClass = builder.appendNewField("Dummy");
        fieldForClass.setContent("public void int i = 3;");


        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"public:String:getName;String:name,int:age;\")\n    public void int Person.i = 3;", builder.createContent("1", new HashMap<String, String>(), new LinkedList<String>()));
    }

    @Test
    public void addTwoFields() {
        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final FieldForMethodBuilder builder = new FieldForMethodBuilder(java6Class, createJavaMethod());
        final FieldForMethod fieldForClass = builder.appendNewField("Dummy");
        fieldForClass.setContent("public void int i = 3;");

        final FieldForMethod fieldForClass2 = builder.appendNewField("Dummy");
        fieldForClass2.setContent("private String name;");

        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"public:String:getName;String:name,int:age;\")\n    public void int Person.i = 3;\n\n    @Generated(id = 1, name = \"Dummy\", data = \"public:String:getName;String:name,int:age;\")\n    private String Person.name;", builder.createContent("1", new HashMap<String, String>(), new LinkedList<String>()));
    }

    private JavaMethod createJavaMethod() {
        final Java6Method javaMethod = new Java6Method();
        javaMethod.setName("getName");
        javaMethod.setType("String");
        javaMethod.setAccessType("public");

        final Java6Parameter java6Parameter = new Java6Parameter();
        java6Parameter.setName("name");
        java6Parameter.setType("String");
        javaMethod.addParameter(java6Parameter);

        final Java6Parameter java6Parameter2 = new Java6Parameter();
        java6Parameter2.setName("age");
        java6Parameter2.setType("int");
        javaMethod.addParameter(java6Parameter2);

        return javaMethod;
    }
}
