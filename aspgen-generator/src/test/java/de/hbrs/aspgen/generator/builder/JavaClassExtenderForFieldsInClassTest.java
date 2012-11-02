package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.generator.ExtendFieldWithMethods;
import de.hbrs.aspgen.api.generator.MethodForField;
import de.hbrs.aspgen.api.generator.MethodForFieldGenerator;
import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Field;

public class JavaClassExtenderForFieldsInClassTest {
    @Test
    public void createAspectOnlyForOneField() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithFieldAnnotation("de.hbrs.Print"), new FieldGen());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Print perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"String:name;\")\n    public void Person.print() {\n"
                + "        System.out.println(name);\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithFieldAnnotation(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final Java6Field java6Field = new Java6Field();
        java6Field.setType("String");
        java6Field.setName("name");
        java6Field.addAnnotation(annotation);
        java6Class.addField(java6Field);

        final Java6Field java6Field2 = new Java6Field();
        java6Field2.setType("int");
        java6Field2.setName("age");
        java6Class.addField(java6Field2);

        return java6Class;
    }

    @Test
    public void createAspectOnlyForAllField() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithClassAnnotation("de.hbrs.Print"), new FieldGen());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Print perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"String:name;\")\n    public void Person.print() {\n"
                + "        System.out.println(name);\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"Dummy\", data = \"int:age;\")\n    public void Person.print() {\n"
                + "        System.out.println(age);\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithClassAnnotation(final String anno) {
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

//    @Test
//    public void createAspectForNotExcludedFields() {
//        final JavaClassExtender extender = new JavaClassExtender(createClassWithClassAnnotationAndExcludeAnnotation("de.hbrs.Print"), new FieldGen());
//        final String result = extender.createAspectJFileContent();
//        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Print {\n"
//                + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"data\")\n    public void Person.print() {\n"
//                + "        System.out.println(age);\n"
//                + "    }\n"
//                + "}";
//        assertEquals(expectedResult, result);
//    }

    private JavaClass createClassWithClassAnnotationAndExcludeAnnotation(
            final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Annotation annotation2 = new Java6Annotation();
        annotation2.setName(anno);
        annotation2.addAttribute("exclude", "true");

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");
        java6Class.addAnnotation(annotation);

        final Java6Field java6Field = new Java6Field();
        java6Field.setType("String");
        java6Field.setName("name");
        java6Field.addAnnotation(annotation2);
        java6Class.addField(java6Field);

        final Java6Field java6Field2 = new Java6Field();
        java6Field2.setType("int");
        java6Field2.setName("age");
        java6Class.addField(java6Field2);

        return java6Class;
    }

    private static class FieldGen implements MethodForFieldGenerator {
        @Override
        public String getName() {
            return "de.hbrs.Print";
        }

        @Override
        public void extendJavaClass(final ExtendFieldWithMethods builder,
                final Map<String, String> properties) {
            final MethodForField methodForField = builder.appendNewMethod("Dummy");
            methodForField.setMethodDeclaration("public void print()");
            methodForField.addLine("System.out.println($fieldname$);");
        }
    }
}
