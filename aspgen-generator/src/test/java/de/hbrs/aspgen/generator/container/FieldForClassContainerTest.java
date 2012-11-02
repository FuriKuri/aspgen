package de.hbrs.aspgen.generator.container;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

public class FieldForClassContainerTest {
    @Test
    public void createStringField() {
        final FieldForClassContainer container = new FieldForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setContent("private String name;");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \";\")\n    private String Person.name;", container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createIntField() {
        final FieldForClassContainer container = new FieldForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setContent("public int age;");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \";\")\n    public int Person.age;", container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createFieldWithAnnotations() {
        final FieldForClassContainer container = new FieldForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setContent("public int age;");
        container.addAnnotation("@Ignore");
        container.addAnnotation("@Deprecated");
        final String expectedResult = "    @Ignore\n    @Deprecated\n    @Generated(id = 1, name = \"Dummy\", data = \";\")\n    "
                + "public int Person.age;";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createFieldWithJavaDoc() {
        final FieldForClassContainer container = new FieldForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setContent("public int age;");
        container.setJavaDoc("First Line\nSecond Line");
        final String expectedResult = "    /**\n     * First Line\n     * Second Line\n     */\n    @Generated(id = 1, name = \"Dummy\", data = \";\")\n"
                + "    public int Person.age;";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createFieldWithInitValue() {
        final FieldForClassContainer container = new FieldForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setContent("public int age = 20;");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \";\")\n    public int Person.age = 20;", container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createFieldWithInitObjectValue() {
        final FieldForClassContainer container = new FieldForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setContent("public BigDecimal age = new BigDecimal(\"20\");");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \";\")\n    public BigDecimal Person.age = new BigDecimal(\"20\");", container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createLoggerField() {
        final FieldForClassContainer container = new FieldForClassContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setContent("private static Logger logger = Logger.getLogger($classname$.class);");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \";\")\n    private static Logger Person.logger = Logger.getLogger(Person.class);", container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test(expected=RuntimeException.class)
    public void invalidFieldDeclation() {
        final FieldForClassContainer container = new FieldForClassContainer();
        container.setOnType("Person");
        container.setContent("public int ()age;");
    }

}
