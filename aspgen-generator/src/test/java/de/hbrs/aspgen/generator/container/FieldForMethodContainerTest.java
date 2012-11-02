package de.hbrs.aspgen.generator.container;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.jparser.type.Java6Method;
import de.hbrs.aspgen.jparser.type.Java6Parameter;

public class FieldForMethodContainerTest {
    @Test
    public void createStringField() {
        final FieldForMethodContainer container = new FieldForMethodContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setMethod(createJavaMethod());
        container.setContent("private String name;");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"public:String:getName;String:name,int:age;\")\n    private String Person.name;", container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createFieldWithPlaceholder() {
        final FieldForMethodContainer container = new FieldForMethodContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setMethod(createJavaMethod());
        container.setContent("public int ageFieldFor$methodname$;");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"public:String:getName;String:name,int:age;\")\n    public int Person.ageFieldForGetName;", container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createFieldWithPlaceholderAtBegin() {
        final FieldForMethodContainer container = new FieldForMethodContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setMethod(createJavaMethod());
        container.setContent("public int $methodname$Field;");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"public:String:getName;String:name,int:age;\")\n    public int Person.getNameField;", container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createFieldWithPlaceholderWithTwoPlaceholder() {
        final FieldForMethodContainer container = new FieldForMethodContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setMethod(createJavaMethod());
        container.setContent("public int ageFieldFor$methodname$$classname$;");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"public:String:getName;String:name,int:age;\")\n    public int Person.ageFieldForGetNamePerson;", container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createFieldWithPlaceholderAndInitValue() {
        final FieldForMethodContainer container = new FieldForMethodContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setMethod(createJavaMethod());
        container.setContent("public int ageFieldFor$methodname$ = 3;");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"public:String:getName;String:name,int:age;\")\n    public int Person.ageFieldForGetName = 3;", container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createFieldWithPlaceholderAtBeginAndInitValue() {
        final FieldForMethodContainer container = new FieldForMethodContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setMethod(createJavaMethod());
        container.setContent("public String $methodname$Field = \"Hello\";");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"public:String:getName;String:name,int:age;\")\n    public String Person.getNameField = \"Hello\";", container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createStringFieldWithMethodSigAsName() {
        final FieldForMethodContainer container = new FieldForMethodContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setMethod(createJavaMethod());
        container.setContent("private String fieldFor$methodsignature$;");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"public:String:getName;String:name,int:age;\")\n    private String Person.fieldForgetNameStringint;", container.createBlockContent("1", new HashMap<String, String>()));
    }

    @Test
    public void createFieldWithPlaceholderWithTwoPlaceholderAndInitValue() {
        final FieldForMethodContainer container = new FieldForMethodContainer();
        container.setOnType("Person");
        container.setName("Dummy");
        container.setMethod(createJavaMethod());
        container.setContent("public BigDecimal ageFieldFor$methodname$$classname$ = new BigDecimal(3);");
        assertEquals("    @Generated(id = 1, name = \"Dummy\", data = \"public:String:getName;String:name,int:age;\")\n    public BigDecimal Person.ageFieldForGetNamePerson = new BigDecimal(3);", container.createBlockContent("1", new HashMap<String, String>()));
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
