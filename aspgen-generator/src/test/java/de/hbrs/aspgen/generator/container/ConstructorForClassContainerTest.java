package de.hbrs.aspgen.generator.container;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.jparser.type.Java6Field;

public class ConstructorForClassContainerTest {
    @Test
    public void createConstructorWithOneLineStaticPart() {
        final ConstructorForClassContainer container = new ConstructorForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaFields(createJavaFields());
        container.setNotExcludedFields(createJavaFields());
        container.addLine("System.out.println(\"In Constructor\");");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"int:age,String:name;\")\n    public Person.new() {\n        System.out.println(\"In Constructor\");\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createConstructorWithStaticAndDynamicPart() {
        final ConstructorForClassContainer container = new ConstructorForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaFields(createJavaFields());
        container.setNotExcludedFields(createJavaFields());
        container.addLine("String result = \"\";");
        container.addLineForeachField("result += $fieldname$;");

        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"int:age,String:name;\")\n    public Person.new() {"
                + "\n        String result = \"\";"
                + "\n        result += age;"
                + "\n        result += name;"
                + "\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createConstructorWithTwoParameter() {
        final ConstructorForClassContainer container = new ConstructorForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaFields(createJavaFields());
        container.addParameter("String name");
        container.addParameter("int age");
        container.addLine("System.out.println(\"In Constructor\");");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"int:age,String:name;\")\n    public Person.new(String name, int age) {\n        System.out.println(\"In Constructor\");\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createConstructorWithTwoParameterAndAllFieldAsParameters() {
        final ConstructorForClassContainer container = new ConstructorForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaFields(createJavaFields());
        container.setNotExcludedFields(createJavaFields());
        container.addParameter("double a");
        container.addParameterForFields("$fieldtype$ $fieldname$");
        container.addParameter("String b");
        container.addLine("System.out.println(\"In Constructor\");");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"int:age,String:name;\")\n    public Person.new(double a, int age, String name, String b) {\n        System.out.println(\"In Constructor\");\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    private List<JavaField> createJavaFields() {
        final List<JavaField> fields = new LinkedList<>();
        final Java6Field stringNameField = new Java6Field();
        stringNameField.setName("name");
        stringNameField.setType("String");
        final Java6Field intAgeField = new Java6Field();
        intAgeField.setName("age");
        intAgeField.setType("int");
        fields.add(intAgeField);
        fields.add(stringNameField);
        return fields;
    }
}
