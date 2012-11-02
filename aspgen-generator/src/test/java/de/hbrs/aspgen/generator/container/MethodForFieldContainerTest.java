package de.hbrs.aspgen.generator.container;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.jparser.type.Java6Field;

public class MethodForFieldContainerTest {
    @Test
    public void createMethodWithOneLineStaticPart() {
        final MethodForFieldContainer container = new MethodForFieldContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaField(createStringField());
        container.setMethodDeclaration("public String getName()");
        container.addLine("return \"name\";");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    public String Person.getName() {\n        return \"name\";\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createMethodWithMoreThanOneLineStaticPart() {
        final MethodForFieldContainer container = new MethodForFieldContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaField(createStringField());
        container.setMethodDeclaration("private int getAge()");
        container.addLine("int i = age;");
        container.addLine("return i;");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    private int Person.getAge() {"
                + "\n        int i = age;"
                + "\n        return i;\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createAsectJMethodWithFieldnameAndFieldtypeAsMethodName() {
        final MethodForFieldContainer container = new MethodForFieldContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaField(createStringField());
        container.setMethodDeclaration("public $fieldtype$ get$fieldname$()");
        container.addLine("String result = \"\";");
        container.addLine("return result;");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    public String Person.getName() {"
                + "\n        String result = \"\";"
                + "\n        return result;\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createAsectJMethodWithFieldnameAsFirstPartOfMethodName() {
        final MethodForFieldContainer container = new MethodForFieldContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaField(createInttFields());
        container.setMethodDeclaration("public String $fieldname$ToString()");
        container.addLine("String result = \"\";");
        container.addLine("return result;");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"int:age;\")\n    public String Person.ageToString() {"
                + "\n        String result = \"\";"
                + "\n        return result;\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createMethodWithPlaceholderAtFirstPartInMethodname() {
        final MethodForFieldContainer container = new MethodForFieldContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaField(createStringField());
        container.setMethodDeclaration("public String $fieldtype$To$fieldtype$()");
        container.addLine("return \"name\";");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    public String Person.stringToString() {\n        return \"name\";\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createMethodWithOneParameter() {
        final MethodForFieldContainer container = new MethodForFieldContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaField(createStringField());
        container.setMethodDeclaration("public String set$fieldname$(String name)");
        container.addLine("name = \"name\";");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    public String Person.setName(String name) {\n        name = \"name\";\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createMethodWithDynamicpart() {
        final MethodForFieldContainer container = new MethodForFieldContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaField(createStringField());
        container.setMethodDeclaration("public String set$fieldname$(String name)");
        container.addLine("$fieldname$ = \"$fieldname$\";");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    public String Person.setName(String name) {\n        name = \"name\";\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createMethodWithTwoParameter() {
        final MethodForFieldContainer container = new MethodForFieldContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaField(createStringField());
        container.setMethodDeclaration("public String setName(String name, String vorname)");
        container.addLine("name = \"name\" + \"vorname\";");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    public String Person.setName(String name, String vorname) {\n        name = \"name\" + \"vorname\";\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createMethodWithJavaDoc() {
        final MethodForFieldContainer container = new MethodForFieldContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaField(createStringField());
        container.setMethodDeclaration("public String getName()");
        container.addLine("return \"name\";");
        container.setJavaDoc("First Line\nSecond Line");
        final String expectedResult = "    /**\n     * First Line\n     * Second Line\n     */\n    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n"
                + "    public String Person.getName() {\n        return \"name\";\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createMethodWithOneAnnotation() {
        final MethodForFieldContainer container = new MethodForFieldContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaField(createStringField());
        container.setMethodDeclaration("public String getName()");
        container.addLine("return \"name\";");
        container.addAnnotation("@Override");
        final String expectedResult = "    @Override\n    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    "
                + "public String Person.getName() {\n        return \"name\";\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createMethodWithTwoAnnotations() {
        final MethodForFieldContainer container = new MethodForFieldContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaField(createStringField());
        container.setMethodDeclaration("public String getName()");
        container.addLine("return \"name\";");
        container.addAnnotation("@Ignore");
        container.addAnnotation("@Deprecated");
        final String expectedResult = "    @Ignore\n    @Deprecated\n    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    "
                + "public String Person.getName() {\n        return \"name\";\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createMethodWithPlaceholderInParameter() {
        final MethodForFieldContainer container = new MethodForFieldContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaField(createStringField());
        container.setMethodDeclaration("public String getName($fieldtype$ $fieldname$)");
        container.addLine("return \"name\";");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"String:name;\")\n    public String Person.getName(String name) {\n        return \"name\";\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    private JavaField createStringField() {
        final Java6Field stringNameField = new Java6Field();
        stringNameField.setName("name");
        stringNameField.setType("String");
        return stringNameField;
    }

    private JavaField createInttFields() {
        final Java6Field intAgeField = new Java6Field();
        intAgeField.setName("age");
        intAgeField.setType("int");
        return intAgeField;
    }
}
