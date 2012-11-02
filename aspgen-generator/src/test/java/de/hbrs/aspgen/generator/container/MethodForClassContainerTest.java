package de.hbrs.aspgen.generator.container;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.jparser.type.Java6Field;

public class MethodForClassContainerTest {
    @Test
    public void createMethodWithOneLineStaticPart() {
        final MethodForClassContainer container = new MethodForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaFields(createJavaFields());
        container.setNotExcludedFields(createJavaFields());
        container.setMethodDeclaration("public String getName()");
        container.addLine("return \"name\";");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"int:age,String:name;\")\n    public String Person.getName() {\n        return \"name\";\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createMethodWithMoreThanOneLineStaticPart() {
        final MethodForClassContainer container = new MethodForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaFields(createJavaFields());
        container.setNotExcludedFields(createJavaFields());
        container.setMethodDeclaration("private int getAge()");
        container.addLine("int i = age;");
        container.addLine("return i;");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"int:age,String:name;\")\n    private int Person.getAge() {"
                + "\n        int i = age;"
                + "\n        return i;\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createAsectJMethodWithStaticAndDynamicPart() {
        final MethodForClassContainer container = new MethodForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaFields(createJavaFields());
        container.setNotExcludedFields(createJavaFields());
        container.setMethodDeclaration("public String toString()");
        container.addLine("String result = \"\";");
        container.addLineForeachField("result += $fieldname$;");
        container.addLine("return result;");

        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"int:age,String:name;\")\n    public String Person.toString() {"
                + "\n        String result = \"\";"
                + "\n        result += age;"
                + "\n        result += name;"
                + "\n        return result;\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createAsectJMethodWithStaticAndDynamicPartForFieldsAndClass() {
        final MethodForClassContainer container = new MethodForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaFields(createJavaFields());
        container.setNotExcludedFields(createJavaFields());
        container.setMethodDeclaration("public boolean equals(Object o)");
        container.addLine("if (o instanceof $classname$) {");
        container.addLine("    $classname$ other = ($classname$) o;");
        container.addLineForeachField("    if ($fieldname$ != other.$fieldname$) return false;");
        container.addLine("    return true;");
        container.addLine("} else {");
        container.addLine("    return false;");
        container.addLine("}");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"int:age,String:name;\")\n    public boolean Person.equals(Object o) {\n"
                + "        if (o instanceof Person) {\n"
                + "            Person other = (Person) o;\n"
                + "            if (age != other.age) return false;\n"
                + "            if (name != other.name) return false;\n"
                + "            return true;\n"
                + "        } else {\n"
                + "            return false;\n"
                + "        }\n"
                + "    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createMethodWithOneParameter() {
        final MethodForClassContainer container = new MethodForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaFields(createJavaFields());
        container.setNotExcludedFields(createJavaFields());
        container.setMethodDeclaration("public String setName(String name)");
        container.addLine("name = \"name\";");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"int:age,String:name;\")\n    public String Person.setName(String name) {\n        name = \"name\";\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createMethodWithTwoParameter() {
        final MethodForClassContainer container = new MethodForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaFields(createJavaFields());
        container.setNotExcludedFields(createJavaFields());
        container.setMethodDeclaration("public String setName(String name, String vorname)");
        container.addLine("name = \"name\" + \"vorname\";");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"int:age,String:name;\")\n    public String Person.setName(String name, String vorname) {\n        name = \"name\" + \"vorname\";\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createMethodWithJavaDoc() {
        final MethodForClassContainer container = new MethodForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaFields(createJavaFields());
        container.setNotExcludedFields(createJavaFields());
        container.setMethodDeclaration("public String getName()");
        container.addLine("return \"name\";");
        container.setJavaDoc("First Line\nSecond Line");
        final String expectedResult = "    /**\n     * First Line\n     * Second Line\n     */\n    @Generated(id = 1, name = \"Dummy\", data = \"int:age,String:name;\")\n"
                + "    public String Person.getName() {\n        return \"name\";\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createMethodWithOneAnnotation() {
        final MethodForClassContainer container = new MethodForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaFields(createJavaFields());
        container.setNotExcludedFields(createJavaFields());
        container.setMethodDeclaration("public String getName()");
        container.addLine("return \"name\";");
        container.addAnnotation("@Override");
        final String expectedResult = "    @Override\n    @Generated(id = 1, name = \"Dummy\", data = \"int:age,String:name;\")\n    "
                + "public String Person.getName() {\n        return \"name\";\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createMethodWithTwoAnnotations() {
        final MethodForClassContainer container = new MethodForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setJavaFields(createJavaFields());
        container.setNotExcludedFields(createJavaFields());
        container.setMethodDeclaration("public String getName()");
        container.addLine("return \"name\";");
        container.addAnnotation("@Ignore");
        container.addAnnotation("@Deprecated");
        final String expectedResult = "    @Ignore\n    @Deprecated\n    @Generated(id = 1, name = \"Dummy\", data = \"int:age,String:name;\")\n    "
                + "public String Person.getName() {\n        return \"name\";\n    }";
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


    // TODO test not excluded dynmaic fields
}
