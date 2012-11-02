package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.generator.AdviceForMethod;
import de.hbrs.aspgen.api.generator.AdviceForMethodGenerator;
import de.hbrs.aspgen.api.generator.ExtendClassWithFields;
import de.hbrs.aspgen.api.generator.ExtendClassWithMethods;
import de.hbrs.aspgen.api.generator.ExtendFieldWithMethods;
import de.hbrs.aspgen.api.generator.ExtendMethodWithAdvices;
import de.hbrs.aspgen.api.generator.FieldForClass;
import de.hbrs.aspgen.api.generator.FieldForClassGenerator;
import de.hbrs.aspgen.api.generator.MethodForClass;
import de.hbrs.aspgen.api.generator.MethodForClassGenerator;
import de.hbrs.aspgen.api.generator.MethodForField;
import de.hbrs.aspgen.api.generator.MethodForFieldGenerator;
import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Field;
import de.hbrs.aspgen.jparser.type.Java6Method;

public class AddAnnotationPropertiesTest {
    @Test
    public void createAspectWithCustomValue() {
        final JavaClassExtender extender = new JavaClassExtender(
                createClassWithCustomValue("de.hbrs.Dummy", "1001"),
                new DummyGeneratorUseProperties());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Dummy perthis(this(Person)) {\n"
                + "    @Generated(id = {newIdClass}, name = \"Dummy\", data = \"String:name;customkey:1001,customkey2:10012,customkey3:10013\")\n    public void Person.print() {\n"
                + "        System.out.println(1001);\n"
                + "    }\n\n"
                + "    @Generated(id = {newIdClass}, name = \"Dummy\", data = \";customkey:1001,customkey2:10012,customkey3:10013\")\n    int Person.i = 1001;\n\n"
                + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"String:name;customkey:1001,customkey2:10012,customkey3:10013\")\n    public void Person.print() {\n"
                + "        System.out.println(1001);\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"Dummy\", data = \"private:void:print;;customkey:1001,customkey2:10012,customkey3:10013\")\n    before() : execution(private void Person.print()) {\n"
                + "        System.out.println(\"1001\");\n" + "    }\n" + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithCustomValue(final String anno, final String customValue) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);
        annotation.addAttribute("customkey", customValue);
        annotation.addAttribute("customkey2", customValue + "2");
        annotation.addAttribute("customkey3", customValue + "3");

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");
        java6Class.addAnnotation(annotation);


        final Java6Field java6Field = new Java6Field();
        java6Field.setType("String");
        java6Field.setName("name");
        java6Field.addAnnotation(annotation);
        java6Class.addField(java6Field);

        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType("private");
        java6Method.setName("print");
        java6Method.setType("void");
        java6Method.addAnnotation(annotation);
        java6Class.addMethod(java6Method);

        return java6Class;
    }

    private static class DummyGeneratorUseProperties implements
            MethodForClassGenerator, FieldForClassGenerator,
            MethodForFieldGenerator, AdviceForMethodGenerator {

        @Override
        public String getName() {
            return "de.hbrs.Dummy";
        }

        @Override
        public void extendJavaClass(final ExtendMethodWithAdvices builder,
                final Map<String, String> properties) {
            final AdviceForMethod adviceForMethod = builder
                    .appendNewBeforeAdvice("Dummy");
            adviceForMethod.addLine("System.out.println(\""
                    + properties.get("customkey") + "\");");
        }

        @Override
        public void extendJavaClass(final ExtendFieldWithMethods builder,
                final Map<String, String> properties) {
            final MethodForField methodForField = builder
                    .appendNewMethod("Dummy");
            methodForField.setMethodDeclaration("public void print()");
            methodForField.addLine("System.out.println("
                    + properties.get("customkey") + ");");
        }

        @Override
        public void extendJavaClass(final ExtendClassWithFields fieldBuilder,
                final Map<String, String> properties) {
            final FieldForClass fieldForClass = fieldBuilder
                    .appendNewField("Dummy");
            fieldForClass.setContent("int i = " + properties.get("customkey")
                    + ";");
        }

        @Override
        public void extendJavaClass(final ExtendClassWithMethods methodBuilder,
                final Map<String, String> properties) {
            final MethodForClass methodForClass = methodBuilder
                    .appendNewMethod("Dummy");
            methodForClass.addLine("System.out.println("
                    + properties.get("customkey") + ");");
            methodForClass.setMethodDeclaration("public void print()");
        }

    }
}
