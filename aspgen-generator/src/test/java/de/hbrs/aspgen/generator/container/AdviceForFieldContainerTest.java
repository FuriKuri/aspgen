package de.hbrs.aspgen.generator.container;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.jparser.type.Java6Field;

public class AdviceForFieldContainerTest {
    @Test
    public void createBeforeAdviceForField() {
        final AdviceForFieldContainer container = new AdviceForFieldContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setField(createStringField());
        container.setAdviceDeclaration("before() : execution(public void $classname$.start())");
        container.addLine("System.out.println($fieldname$);");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    before() : execution(public void Person.start()) {\n"
                + "        System.out.println(name);\n"
                + "    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    private JavaField createStringField() {
        final Java6Field stringNameField = new Java6Field();
        stringNameField.setName("name");
        stringNameField.setType("String");
        return stringNameField;
    }

    @Test
    public void createAfterAdviceForField() {
        final AdviceForFieldContainer container = new AdviceForFieldContainer();
        container.setOnType("Person");
        container.setName("Dummy");

        container.setField(createInttFields());
        container.setAdviceDeclaration("after() : execution(public void $classname$.end())");
        container.addLine("log.log($fieldname$);");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"int:age;\")\n    after() : execution(public void Person.end()) {\n"
                + "        log.log(age);\n"
                + "    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    private JavaField createInttFields() {
        final Java6Field intAgeField = new Java6Field();
        intAgeField.setName("age");
        intAgeField.setType("int");
        return intAgeField;
    }

    @Test
    public void createBeforeAdviceForFieldWithJavaDocAndAnnotation() {
        final AdviceForFieldContainer container = new AdviceForFieldContainer();
        container.setOnType("Person");
        container.setName("Dummy");

        container.setField(createStringField());
        container.setAdviceDeclaration("before() : execution(public void $classname$.start())");
        container.addLine("System.out.println($fieldname$);");
        container.setJavaDoc("My JavaDoc\n2.Line");
        container.addAnnotation("@MyAnno");
        final String expectedResult = "    /**\n     * My JavaDoc\n     * 2.Line\n     */\n    @MyAnno\n    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n"
        		+ "    before() : execution(public void Person.start()) {\n"
                + "        System.out.println(name);\n"
                + "    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }
}
