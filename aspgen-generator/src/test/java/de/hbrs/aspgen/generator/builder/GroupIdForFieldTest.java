package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.generator.AdviceForField;
import de.hbrs.aspgen.api.generator.AdviceForFieldGenerator;
import de.hbrs.aspgen.api.generator.ExtendFieldWithAdvices;
import de.hbrs.aspgen.api.generator.ExtendFieldWithMethods;
import de.hbrs.aspgen.api.generator.MethodForField;
import de.hbrs.aspgen.api.generator.MethodForFieldGenerator;
import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Field;

public class GroupIdForFieldTest {

    @Test
    public void createTwoNewGroupIds() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithFieldsWithAnno("de.hbrs.JavaBean"), new JavaBeanGenerator());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\n"
                + "public privileged aspect Person_JavaBean perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"Setter\", data = \"String:name;\")\n"
                + "    public void Person.setName(String name) {\n"
                + "        this.name = name;\n"
                + "    }\n\n"
                + "    @Generated(id = {newid1}, name = \"Getter\", data = \"String:name;\")\n"
                + "    public String Person.getName() {\n"
                + "        return name;\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"Setter\", data = \"int:age;\")\n"
                + "    public void Person.setAge(int age) {\n"
                + "        this.age = age;\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"Getter\", data = \"int:age;\")\n"
                + "    public int Person.getAge() {\n"
                + "        return age;\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithFieldsWithAnno(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final Java6Field java6Field = new Java6Field();
        java6Field.setType("String");
        java6Field.setName("name");
        java6Class.addField(java6Field);
        java6Field.addAnnotation(annotation);


        final Java6Field java6Field2 = new Java6Field();
        java6Field2.setType("int");
        java6Field2.setName("age");
        java6Class.addField(java6Field2);
        java6Field2.addAnnotation(annotation);


        return java6Class;
    }

    @Test
    public void createTwoNewGroupIdsForClassWithAnno() {
        final JavaClassExtender extender = new JavaClassExtender(createClassAnno("de.hbrs.JavaBean"), new JavaBeanGenerator());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\n"
                + "public privileged aspect Person_JavaBean perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"Setter\", data = \"String:name;\")\n"
                + "    public void Person.setName(String name) {\n"
                + "        this.name = name;\n"
                + "    }\n\n"
                + "    @Generated(id = {newid1}, name = \"Getter\", data = \"String:name;\")\n"
                + "    public String Person.getName() {\n"
                + "        return name;\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"Setter\", data = \"int:age;\")\n"
                + "    public void Person.setAge(int age) {\n"
                + "        this.age = age;\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"Getter\", data = \"int:age;\")\n"
                + "    public int Person.getAge() {\n"
                + "        return age;\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassAnno(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");
        java6Class.addAnnotation(annotation);

        final Java6Field java6Field = new Java6Field();
        java6Field.setType("String");
        java6Field.setName("name");
        java6Class.addField(java6Field);


        final Java6Field java6Field2 = new Java6Field();
        java6Field2.setType("int");
        java6Field2.setName("age");
        java6Class.addField(java6Field2);


        return java6Class;
    }


    @Test
    public void createTwoNewGroupIdsAndOneWithFieldAnnoId() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithThreeFieldsAndOneId("de.hbrs.JavaBean"), new JavaBeanGenerator());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\n"
                + "public privileged aspect Person_JavaBean perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"Setter\", data = \"String:name;\")\n"
                + "    public void Person.setName(String name) {\n"
                + "        this.name = name;\n"
                + "    }\n\n"
                + "    @Generated(id = {newid1}, name = \"Getter\", data = \"String:name;\")\n"
                + "    public String Person.getName() {\n"
                + "        return name;\n"
                + "    }\n\n"
                + "    @Generated(id = 4, name = \"Setter\", data = \"int:age;\")\n"
                + "    public void Person.setAge(int age) {\n"
                + "        this.age = age;\n"
                + "    }\n\n"
                + "    @Generated(id = 4, name = \"Getter\", data = \"int:age;\")\n"
                + "    public int Person.getAge() {\n"
                + "        return age;\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"Setter\", data = \"String:address;\")\n"
                + "    public void Person.setAddress(String address) {\n"
                + "        this.address = address;\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"Getter\", data = \"String:address;\")\n"
                + "    public String Person.getAddress() {\n"
                + "        return address;\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithThreeFieldsAndOneId(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");
        java6Class.addAnnotation(annotation);

        final Java6Field java6Field = new Java6Field();
        java6Field.setType("String");
        java6Field.setName("name");
        java6Class.addField(java6Field);

        final Java6Annotation annotation2 = new Java6Annotation();
        annotation2.setName(anno);
        annotation2.addAttribute("id", "4");

        final Java6Field java6Field2 = new Java6Field();
        java6Field2.setType("int");
        java6Field2.setName("age");
        java6Field2.addAnnotation(annotation2);
        java6Class.addField(java6Field2);

        final Java6Field java6Field3 = new Java6Field();
        java6Field3.setType("String");
        java6Field3.setName("address");
        java6Class.addField(java6Field3);

        return java6Class;
    }


    private static class JavaBeanGenerator implements MethodForFieldGenerator {

        @Override
        public String getName() {
            return "de.hbrs.JavaBean";
        }

        @Override
        public void extendJavaClass(final ExtendFieldWithMethods builder,
                final Map<String, String> properties) {
            final MethodForField setterMethod = builder.appendNewMethod("Setter");
            setterMethod.setMethodDeclaration("public void set$fieldname$($fieldtype$ $fieldname$)");
            setterMethod.addLine("this.$fieldname$ = $fieldname$;");

            final MethodForField getterMethod = builder.appendNewMethod("Getter");
            getterMethod.setMethodDeclaration("public $fieldtype$ get$fieldname$()");
            getterMethod.addLine("return $fieldname$;");

        }

    }

    @Test
    public void createGroupIdsForMethodsAndAdvices() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithThreeFields("de.hbrs.JavaBean"), new JavaBeanGeneratorWithAdvice());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\n"
                + "public privileged aspect Person_JavaBean perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"Setter\", data = \"String:name;\")\n"
                + "    public void Person.setName(String name) {\n"
                + "        this.name = name;\n"
                + "    }\n\n"
                + "    @Generated(id = {newid1}, name = \"Getter\", data = \"String:name;\")\n"
                + "    public String Person.getName() {\n"
                + "        return name;\n"
                + "    }\n\n"
                + "    @Generated(id = 4, name = \"Setter\", data = \"int:age;\")\n"
                + "    public void Person.setAge(int age) {\n"
                + "        this.age = age;\n"
                + "    }\n\n"
                + "    @Generated(id = 4, name = \"Getter\", data = \"int:age;\")\n"
                + "    public int Person.getAge() {\n"
                + "        return age;\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"Setter\", data = \"String:address;\")\n"
                + "    public void Person.setAddress(String address) {\n"
                + "        this.address = address;\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"Getter\", data = \"String:address;\")\n"
                + "    public String Person.getAddress() {\n"
                + "        return address;\n"
                + "    }\n\n"
                + "    @Generated(id = {newid1}, name = \"FirstAdvice\", data = \"String:name;\")\n"
                + "    before() : execution(public void Person.start()) {\n"
                + "        System.out.println(name);\n"
                + "    }\n\n"
                + "    @Generated(id = {newid1}, name = \"SecondAdvice\", data = \"String:name;\")\n"
                + "    before() : execution(public void Person.end()) {\n"
                + "        System.out.println(name);\n"
                + "    }\n\n"
                + "    @Generated(id = 4, name = \"FirstAdvice\", data = \"int:age;\")\n"
                + "    before() : execution(public void Person.start()) {\n"
                + "        System.out.println(age);\n"
                + "    }\n\n"
                + "    @Generated(id = 4, name = \"SecondAdvice\", data = \"int:age;\")\n"
                + "    before() : execution(public void Person.end()) {\n"
                + "        System.out.println(age);\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"FirstAdvice\", data = \"String:address;\")\n"
                + "    before() : execution(public void Person.start()) {\n"
                + "        System.out.println(address);\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"SecondAdvice\", data = \"String:address;\")\n"
                + "    before() : execution(public void Person.end()) {\n"
                + "        System.out.println(address);\n"
                + "    }\n"
                + "}";

        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithThreeFields(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final Java6Field java6Field = new Java6Field();
        java6Field.setType("String");
        java6Field.setName("name");
        java6Field.addAnnotation(annotation);
        java6Class.addField(java6Field);

        final Java6Annotation annotation2 = new Java6Annotation();
        annotation2.setName(anno);
        annotation2.addAttribute("id", "4");

        final Java6Field java6Field2 = new Java6Field();
        java6Field2.setType("int");
        java6Field2.setName("age");
        java6Field2.addAnnotation(annotation2);
        java6Class.addField(java6Field2);

        final Java6Field java6Field3 = new Java6Field();
        java6Field3.setType("String");
        java6Field3.setName("address");
        java6Field3.addAnnotation(annotation);
        java6Class.addField(java6Field3);

        return java6Class;
    }

    private static class JavaBeanGeneratorWithAdvice implements MethodForFieldGenerator, AdviceForFieldGenerator {

        @Override
        public String getName() {
            return "de.hbrs.JavaBean";
        }

        @Override
        public void extendJavaClass(final ExtendFieldWithMethods builder,
                final Map<String, String> properties) {
            final MethodForField setterMethod = builder.appendNewMethod("Setter");
            setterMethod.setMethodDeclaration("public void set$fieldname$($fieldtype$ $fieldname$)");
            setterMethod.addLine("this.$fieldname$ = $fieldname$;");

            final MethodForField getterMethod = builder.appendNewMethod("Getter");
            getterMethod.setMethodDeclaration("public $fieldtype$ get$fieldname$()");
            getterMethod.addLine("return $fieldname$;");

        }

        @Override
        public void extendJavaClass(final ExtendFieldWithAdvices builder,
                final Map<String, String> properties) {
            final AdviceForField adviceForField = builder.appendNewAdvice("FirstAdvice");
            adviceForField.setAdviceDeclaration("before() : execution(public void $classname$.start())");
            adviceForField.addLine("System.out.println($fieldname$);");

            final AdviceForField adviceForField2 = builder.appendNewAdvice("SecondAdvice");
            adviceForField2.setAdviceDeclaration("before() : execution(public void $classname$.end())");
            adviceForField2.addLine("System.out.println($fieldname$);");
        }

    }
}
