package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.generator.AdviceForMethod;
import de.hbrs.aspgen.api.generator.AdviceForMethodGenerator;
import de.hbrs.aspgen.api.generator.ExtendMethodWithAdvices;
import de.hbrs.aspgen.api.generator.ExtendMethodWithFields;
import de.hbrs.aspgen.api.generator.FieldForMethod;
import de.hbrs.aspgen.api.generator.FieldForMethodGenerator;
import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Method;
import de.hbrs.aspgen.jparser.type.Java6Parameter;

public class JavaClassExtenderForMethodsInClassTest {

    @Test
    public void createAspectOnlyForMethod() {
        final JavaClassExtender extender = new JavaClassExtender(createClass("de.hbrs.Print"), new MethodGen());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Print perthis(this(Person)) {\n"
            + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"private:void:print;;\")\n    before() : execution(private void Person.print()) {\n"
            + "        System.out.println();\n"
            + "    }\n\n"
            + "    @Generated(id = {newid2}, name = \"Dummy\", data = \"public:String:do2;;\")\n    before() : execution(public String Person.do2()) {\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClass(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType("private");
        java6Method.setName("print");
        java6Method.setType("void");
        java6Method.addAnnotation(annotation);
        java6Class.addMethod(java6Method);

        final Java6Method java6Method2 = new Java6Method();
        java6Method2.setAccessType("public");
        java6Method2.setName("do2");
        java6Method2.setType("String");
        java6Method2.addAnnotation(annotation);
        java6Class.addMethod(java6Method2);

        return java6Class;
    }

    @Test
    public void createAspectForMethodWithAnnotationClass() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithAnnoation("de.hbrs.Print"), new MethodGen());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Print perthis(this(Person)) {\n"
            + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"private:void:print;;\")\n    before() : execution(private void Person.print()) {\n"
            + "        System.out.println();\n"
            + "    }\n\n"
            + "    @Generated(id = {newid2}, name = \"Dummy\", data = \"public:String:do2;;\")\n    before() : execution(public String Person.do2()) {\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithAnnoation(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");
        java6Class.addAnnotation(annotation);

        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType("private");
        java6Method.setName("print");
        java6Method.setType("void");
        java6Class.addMethod(java6Method);

        final Java6Method java6Method2 = new Java6Method();
        java6Method2.setAccessType("public");
        java6Method2.setName("do2");
        java6Method2.setType("String");
        java6Class.addMethod(java6Method2);

        return java6Class;
    }


    private static class MethodGen implements AdviceForMethodGenerator {
        @Override
        public String getName() {
            return "de.hbrs.Print";
        }

        @Override
        public void extendJavaClass(final ExtendMethodWithAdvices builder,
                final Map<String, String> properties) {
            final AdviceForMethod adviceForMethod = builder.appendNewBeforeAdvice("Dummy");
            adviceForMethod.addLine("System.out.println();");
        }

    }


    @Test
    public void createFieldForMethod() {
        final JavaClassExtender extender = new JavaClassExtender(createClass("de.hbrs.Print"), new FieldGen());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Print perthis(this(Person)) {\n"
            + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"private:void:print;;\")\n    public void Person.fieldPrint;\n\n"
            + "    @Generated(id = {newid2}, name = \"Dummy\", data = \"public:String:do2;;\")\n    public void Person.fieldDo2;\n"
            + "}";
        assertEquals(expectedResult, result);
    }

    private static class FieldGen implements FieldForMethodGenerator {
        @Override
        public String getName() {
            return "de.hbrs.Print";
        }

        @Override
        public void extendJavaClass(final ExtendMethodWithFields builder,
                final Map<String, String> properties) {
            final FieldForMethod fieldForMethod = builder.appendNewField("Dummy");
            fieldForMethod.setContent("public void field$methodname$;");
        }
    }

    @Test
    public void createOneAdviceForNotExcludeParameters() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithAnnoationAndExcludeParameter("de.hbrs.Print"), new MethodForEachParameterGen());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Print perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"public:String:do2;String:name,int:age;\")\n"
                + "    before(final String name, final int age) : execution(public String Person.do2(String, int)) && args(name, age) {\n"
                + "        System.out.println(name);\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithAnnoationAndExcludeParameter(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType("public");
        java6Method.setName("do2");
        java6Method.setType("String");
        java6Method.addAnnotation(annotation);
        java6Class.addMethod(java6Method);

        final Java6Parameter javaParameter = new Java6Parameter();
        javaParameter.setName("name");
        javaParameter.setType("String");
        java6Method.addParameter(javaParameter);

        final Java6Parameter javaParameter2 = new Java6Parameter();
        javaParameter2.setName("age");
        javaParameter2.setType("int");
        java6Method.addParameter(javaParameter2);

        final Java6Annotation annotation2 = new Java6Annotation();
        annotation2.setName(anno);
        annotation2.addAttribute("exclude", "\"Dummy\"");
        javaParameter2.addAnnotation(annotation2);

        return java6Class;
    }

    private static class MethodForEachParameterGen implements AdviceForMethodGenerator {
        @Override
        public String getName() {
            return "de.hbrs.Print";
        }

        @Override
        public void extendJavaClass(final ExtendMethodWithAdvices builder,
                final Map<String, String> properties) {
            final AdviceForMethod adviceForMethod = builder.appendNewBeforeAdvice("Dummy");
            adviceForMethod.addLineForeachParameter("System.out.println($parametername$);");
        }

    }

}
