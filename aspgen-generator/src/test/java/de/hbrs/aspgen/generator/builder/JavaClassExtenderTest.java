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
import de.hbrs.aspgen.api.generator.Generator;
import de.hbrs.aspgen.api.generator.MethodForClass;
import de.hbrs.aspgen.api.generator.MethodForClassGenerator;
import de.hbrs.aspgen.api.generator.MethodForField;
import de.hbrs.aspgen.api.generator.MethodForFieldGenerator;
import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Field;
import de.hbrs.aspgen.jparser.type.Java6Method;

public class JavaClassExtenderTest {
    @Test
    public void createEmptyStringIfNoAnnotationExists() {
        final JavaClassExtender extender = new JavaClassExtender(createClass("de.hbrs.NoExists"), new DummyGenerator());
        final String result = extender.createAspectJFileContent();
        assertEquals("", result);
    }

    @Test
    public void createAspectWithExtendMethod() {
        final Generator extendsClassWithMethod = new MethodForClassGenerator() {
            @Override
            public String getName() {
                return "de.hbrs.ToString";
            }

            @Override
            public void extendJavaClass(final ExtendClassWithMethods methodBuilder,
                    final Map<String, String> properties) {
                final MethodForClass methodForClass = methodBuilder.appendNewMethod("Dummy");
                methodForClass.addLine("System.out.println();");
                methodForClass.setMethodDeclaration("public void print()");
            }
        };

        final JavaClassExtender extender = new JavaClassExtender(createClass("de.hbrs.ToString"), extendsClassWithMethod);
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_ToString perthis(this(Person)) {\n"
            + "    @Generated(id = {newIdClass}, name = \"Dummy\", data = \"String:name;\")\n    public void Person.print() {\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
        assertEquals(expectedResult, result);
    }

    @Test
    public void createAspectWithExtendField() {
        final Generator extendsClassWithField = new FieldForClassGenerator() {

            @Override
            public String getName() {
                return "de.hbrs.ToString";
            }

            @Override
            public void extendJavaClass(final ExtendClassWithFields fieldBuilder,
                    final Map<String, String> properties) {
                final FieldForClass fieldForClass = fieldBuilder.appendNewField("Dummy");
                fieldForClass.setContent("int i = 3;");
            }
        };

        final JavaClassExtender extender = new JavaClassExtender(createClass("de.hbrs.ToString"), extendsClassWithField);
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_ToString perthis(this(Person)) {\n"
            + "    @Generated(id = {newIdClass}, name = \"Dummy\", data = \";\")\n    int Person.i = 3;\n"
            + "}";
        assertEquals(expectedResult, result);
    }

    @Test
    public void createAspectWithMethodsForFields() {
        final Generator extendsMethodWithField = new MethodForFieldGenerator() {

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
        };

        final JavaClassExtender extender = new JavaClassExtender(createClass("de.hbrs.Print"), extendsMethodWithField);
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Print perthis(this(Person)) {\n"
            + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"String:name;\")\n    public void Person.print() {\n"
            + "        System.out.println(name);\n"
            + "    }\n"
            + "}";
        assertEquals(expectedResult, result);
    }

    @Test
    public void createAspectWithAdviceForMethod() {
        final Generator extendsMethodWithAdvice = new AdviceForMethodGenerator() {

            @Override
            public String getName() {
                return "de.hbrs.Logging";
            }

            @Override
            public void extendJavaClass(final ExtendMethodWithAdvices builder,
                    final Map<String, String> properties) {
                final AdviceForMethod adviceForMethod = builder.appendNewBeforeAdvice("Dummy");
                adviceForMethod.addLine("System.out.println(\"Advice\");");
            }
        };

        final JavaClassExtender extender = new JavaClassExtender(createClass("de.hbrs.Logging"), extendsMethodWithAdvice);
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Logging perthis(this(Person)) {\n"
            + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"private:void:print;;\")\n    before() : execution(private void Person.print()) {\n"
            + "        System.out.println(\"Advice\");\n"
            + "    }\n"
            + "}";
        assertEquals(expectedResult, result);
    }

    @Test
    public void createAspectWithFieldMethodAndAdvice() {
        final JavaClassExtender extender = new JavaClassExtender(createClass("de.hbrs.Dummy"), new DummyGenerator());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Dummy perthis(this(Person)) {\n"
                + "    @Generated(id = {newIdClass}, name = \"Dummy\", data = \"String:name;\")\n    public void Person.print() {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newIdClass}, name = \"Dummy\", data = \";\")\n    int Person.i = 3;\n\n"
                + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"String:name;\")\n    public void Person.print() {\n"
                + "        System.out.println(name);\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"Dummy\", data = \"private:void:print;;\")\n    before() : execution(private void Person.print()) {\n"
                + "        System.out.println(\"Advice\");\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    @Test
    public void createAspectOnlyForField() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithFieldAnnotation("de.hbrs.Dummy"), new DummyGenerator());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Dummy perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"String:name;\")\n    public void Person.print() {\n"
                + "        System.out.println(name);\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    @Test
    public void createAspectOnlyForMethod() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithMethodAnnotation("de.hbrs.Dummy"), new DummyGenerator());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Dummy perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"private:void:print;;\")\n    before() : execution(private void Person.print()) {\n"
                + "        System.out.println(\"Advice\");\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private static class DummyGenerator implements MethodForClassGenerator, FieldForClassGenerator,
            MethodForFieldGenerator, AdviceForMethodGenerator {

        @Override
        public String getName() {
            return "de.hbrs.Dummy";
        }

        @Override
        public void extendJavaClass(final ExtendMethodWithAdvices builder,
                final Map<String, String> properties) {
            final AdviceForMethod adviceForMethod = builder.appendNewBeforeAdvice("Dummy");
            adviceForMethod.addLine("System.out.println(\"Advice\");");
        }

        @Override
        public void extendJavaClass(final ExtendFieldWithMethods builder,
                final Map<String, String> properties) {
            final MethodForField methodForField = builder.appendNewMethod("Dummy");
            methodForField.setMethodDeclaration("public void print()");
            methodForField.addLine("System.out.println($fieldname$);");
        }

        @Override
        public void extendJavaClass(final ExtendClassWithFields fieldBuilder,
                final Map<String, String> properties) {
            final FieldForClass fieldForClass = fieldBuilder.appendNewField("Dummy");
            fieldForClass.setContent("int i = 3;");
        }

        @Override
        public void extendJavaClass(final ExtendClassWithMethods methodBuilder,
                final Map<String, String> properties) {
            final MethodForClass methodForClass = methodBuilder.appendNewMethod("Dummy");
            methodForClass.addLine("System.out.println();");
            methodForClass.setMethodDeclaration("public void print()");
        }

    }

    @Test
    public void createAspectWithCustomValue() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithCustomValue("de.hbrs.Dummy", "1001"), new DummyGeneratorUseProperties());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Dummy perthis(this(Person)) {\n"
                + "    @Generated(id = {newIdClass}, name = \"Dummy\", data = \"String:name;customkey:1001\")\n    public void Person.print() {\n"
                + "        System.out.println(1001);\n"
                + "    }\n\n"
                + "    @Generated(id = {newIdClass}, name = \"Dummy\", data = \";customkey:1001\")\n    int Person.i = 1001;\n\n"
                + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"String:name;customkey:1001\")\n    public void Person.print() {\n"
                + "        System.out.println(1001);\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"Dummy\", data = \"private:void:print;;customkey:1001\")\n    before() : execution(private void Person.print()) {\n"
                + "        System.out.println(\"1001\");\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private static class DummyGeneratorUseProperties implements MethodForClassGenerator,
            FieldForClassGenerator, MethodForFieldGenerator,
            AdviceForMethodGenerator {

        @Override
        public String getName() {
            return "de.hbrs.Dummy";
        }

        @Override
        public void extendJavaClass(final ExtendMethodWithAdvices builder,
                final Map<String, String> properties) {
            final AdviceForMethod adviceForMethod = builder
                    .appendNewBeforeAdvice("Dummy");
            adviceForMethod.addLine("System.out.println(\"" + properties.get("customkey") + "\");");
        }

        @Override
        public void extendJavaClass(final ExtendFieldWithMethods builder,
                final Map<String, String> properties) {
            final MethodForField methodForField = builder.appendNewMethod("Dummy");
            methodForField.setMethodDeclaration("public void print()");
            methodForField.addLine("System.out.println(" + properties.get("customkey") + ");");
        }

        @Override
        public void extendJavaClass(final ExtendClassWithFields fieldBuilder,
                final Map<String, String> properties) {
            final FieldForClass fieldForClass = fieldBuilder.appendNewField("Dummy");
            fieldForClass.setContent("int i = " + properties.get("customkey") + ";");
        }

        @Override
        public void extendJavaClass(final ExtendClassWithMethods methodBuilder,
                final Map<String, String> properties) {
            final MethodForClass methodForClass = methodBuilder
                    .appendNewMethod("Dummy");
            methodForClass.addLine("System.out.println(" + properties.get("customkey") + ");");
            methodForClass.setMethodDeclaration("public void print()");
        }

    }

    private JavaClass createClassOnlyClassAnnotation(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Annotation annotationExclude = new Java6Annotation();
        annotationExclude.setName(anno);
        annotationExclude.addAttribute("exclude", "true");

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");
        java6Class.addAnnotation(annotation);

        final Java6Field java6Field = new Java6Field();
        java6Field.setType("String");
        java6Field.setName("name");
        java6Field.addAnnotation(annotationExclude);
        java6Class.addField(java6Field);

        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType("private");
        java6Method.setName("print");
        java6Method.setType("void");
        java6Method.addAnnotation(annotationExclude);
        java6Class.addMethod(java6Method);

        return java6Class;
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

        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType("private");
        java6Method.setName("print");
        java6Method.setType("void");
        java6Class.addMethod(java6Method);

        return java6Class;
    }

    private JavaClass createClassWithMethodAnnotation(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final Java6Field java6Field = new Java6Field();
        java6Field.setType("String");
        java6Field.setName("name");
        java6Field.addAnnotation(annotation);

        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType("private");
        java6Method.setName("print");
        java6Method.setType("void");
        java6Method.addAnnotation(annotation);
        java6Class.addMethod(java6Method);

        return java6Class;
    }

    private JavaClass createClassWithCustomValue(final String anno, final String customValue) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);
        annotation.addAttribute("customkey", customValue);

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

    private JavaClass createClass(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

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
}
