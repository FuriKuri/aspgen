package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.generator.ConstructorForClass;
import de.hbrs.aspgen.api.generator.ConstructorForClassGenerator;
import de.hbrs.aspgen.api.generator.ExtendClassWithConstructors;
import de.hbrs.aspgen.api.generator.ExtendClassWithFields;
import de.hbrs.aspgen.api.generator.ExtendClassWithMethods;
import de.hbrs.aspgen.api.generator.FieldForClass;
import de.hbrs.aspgen.api.generator.FieldForClassGenerator;
import de.hbrs.aspgen.api.generator.MethodForClass;
import de.hbrs.aspgen.api.generator.MethodForClassGenerator;
import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Field;

public class IdForClassTest {

    @Test
    public void createNewIdClass() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithAnno("de.hbrs.ForClass"), new ForClassGenerator());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\n"
                + "public privileged aspect Person_ForClass perthis(this(Person)) {\n"
                + "    @Generated(id = {newIdClass}, name = \"ClassMethod\", data = \"String:name,int:age;\")\n"
                + "    public void Person.print() {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newIdClass}, name = \"Cons\", data = \"String:name,int:age;\")\n"
                + "    public Person.new() {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newIdClass}, name = \"ClassField\", data = \";\")\n"
                + "    public int Person.a;\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithAnno(final String anno) {
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
    public void createTwoNewGroupIds() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithAnnoAndId("de.hbrs.ForClass"), new ForClassGenerator());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\n"
                + "public privileged aspect Person_ForClass perthis(this(Person)) {\n"
                + "    @Generated(id = 1, name = \"ClassMethod\", data = \"String:name,int:age;\")\n"
                + "    public void Person.print() {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = 1, name = \"Cons\", data = \"String:name,int:age;\")\n"
                + "    public Person.new() {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = 1, name = \"ClassField\", data = \";\")\n"
                + "    public int Person.a;\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithAnnoAndId(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);
        annotation.addAttribute("id", "1");

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

    private static class ForClassGenerator implements FieldForClassGenerator, MethodForClassGenerator, ConstructorForClassGenerator {

        @Override
        public String getName() {
            return "de.hbrs.ForClass";
        }

        @Override
        public void extendJavaClass(final ExtendClassWithConstructors builder, final Map<String, String> properties) {
            final ConstructorForClass constructorForClass = builder.appendNewConstructor("Cons");
            constructorForClass.addLine("System.out.println();");
        }

        @Override
        public void extendJavaClass(final ExtendClassWithMethods methodBuilder, final Map<String, String> properties) {
            final MethodForClass methodForClass = methodBuilder.appendNewMethod("ClassMethod");
            methodForClass.addLine("System.out.println();");
            methodForClass.setMethodDeclaration("public void print()");
        }

        @Override
        public void extendJavaClass(final ExtendClassWithFields fieldBuilder,
                final Map<String, String> properties) {
            final FieldForClass fieldForClass = fieldBuilder.appendNewField("ClassField");
            fieldForClass.setContent("public int a;");
        }

    }
}
