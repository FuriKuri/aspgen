package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.generator.ExtendClassWithMethods;
import de.hbrs.aspgen.api.generator.MethodForClass;
import de.hbrs.aspgen.api.generator.MethodForClassGenerator;
import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Field;

public class JavaClassExtenderForClassesTest {

    @Test
    public void createMethod() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithClassAnnotation("de.hbrs.Print"), new MethodGen());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Print perthis(this(Person)) {\n"
                + "    @Generated(id = {newIdClass}, name = \"Dummy\", data = \"String:name,int:age;\")\n    public void Person.print() {\n"
                + "        System.out.println(name);\n"
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

    @Test
    public void createMethodWithExcludedField() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithClassAnnotationAndExclduedField("de.hbrs.Print"), new MethodGen());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Print perthis(this(Person)) {\n"
                + "    @Generated(id = {newIdClass}, name = \"Dummy\", data = \"String:name,int:age;\")\n    public void Person.print() {\n"
                + "        System.out.println(age);\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithClassAnnotationAndExclduedField(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Annotation annotation2 = new Java6Annotation();
        annotation2.setName(anno);
        annotation2.addAttribute("exclude", "\"Dummy\"");

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

    private static class MethodGen implements MethodForClassGenerator {
        @Override
        public String getName() {
            return "de.hbrs.Print";
        }

        @Override
        public void extendJavaClass(final ExtendClassWithMethods methodBuilder,
                final Map<String, String> properties) {
            final MethodForClass methodForClass = methodBuilder.appendNewMethod("Dummy");
            methodForClass.setMethodDeclaration("public void print()");
            methodForClass.addLineForeachField("System.out.println($fieldname$);");
        }
    }
}
