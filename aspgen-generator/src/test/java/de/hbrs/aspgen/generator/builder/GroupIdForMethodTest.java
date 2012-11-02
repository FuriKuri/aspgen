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

public class GroupIdForMethodTest {
    @Test
    public void createTwoNewGroupIds() {
        final JavaClassExtender extender = new JavaClassExtender(createClass("de.hbrs.Advice"), new AdviceGenerator());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\n"
                + "public privileged aspect Person_Advice perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"FirstAdvice\", data = \"private:void:print;;\")\n"
                + "    after() : execution(private void Person.print()) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid1}, name = \"SecondAdvice\", data = \"private:void:print;;\")\n"
                + "    after() : execution(private void Person.print()) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"FirstAdvice\", data = \"public:String:do2;;\")\n"
                + "    after() returning(String returnValue) : execution(public String Person.do2()) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"SecondAdvice\", data = \"public:String:do2;;\")\n"
                + "    after() returning(String returnValue) : execution(public String Person.do2()) {\n"
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
    public void createTwoNewGroupIdsWithClassAnno() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithAnno("de.hbrs.Advice"), new AdviceGenerator());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\n"
                + "public privileged aspect Person_Advice perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"FirstAdvice\", data = \"private:void:print;;\")\n"
                + "    after() : execution(private void Person.print()) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid1}, name = \"SecondAdvice\", data = \"private:void:print;;\")\n"
                + "    after() : execution(private void Person.print()) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"FirstAdvice\", data = \"public:String:do2;;\")\n"
                + "    after() returning(String returnValue) : execution(public String Person.do2()) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"SecondAdvice\", data = \"public:String:do2;;\")\n"
                + "    after() returning(String returnValue) : execution(public String Person.do2()) {\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithAnno(final String anno) {
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

    @Test
    public void createOneNewGroupIdsWithClassAnno() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithClassAndMethodAnno("de.hbrs.Advice"), new AdviceGenerator());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\n"
                + "public privileged aspect Person_Advice perthis(this(Person)) {\n"
                + "    @Generated(id = 5, name = \"FirstAdvice\", data = \"private:void:print;;\")\n"
                + "    after() : execution(private void Person.print()) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = 5, name = \"SecondAdvice\", data = \"private:void:print;;\")\n"
                + "    after() : execution(private void Person.print()) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid1}, name = \"FirstAdvice\", data = \"public:String:do2;;\")\n"
                + "    after() returning(String returnValue) : execution(public String Person.do2()) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid1}, name = \"SecondAdvice\", data = \"public:String:do2;;\")\n"
                + "    after() returning(String returnValue) : execution(public String Person.do2()) {\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithClassAndMethodAnno(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");
        java6Class.addAnnotation(annotation);

        final Java6Annotation annotation2 = new Java6Annotation();
        annotation2.setName(anno);
        annotation2.addAttribute("id", "5");
        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType("private");
        java6Method.setName("print");
        java6Method.setType("void");
        java6Method.addAnnotation(annotation2);
        java6Class.addMethod(java6Method);

        final Java6Method java6Method2 = new Java6Method();
        java6Method2.setAccessType("public");
        java6Method2.setName("do2");
        java6Method2.setType("String");
        java6Class.addMethod(java6Method2);

        return java6Class;
    }

    @Test
    public void createTwoNewGroupIdsForAdviceAndField() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithClassAnd3MethodAnno("de.hbrs.Advice"), new AdviceAndFieldGenerator());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\n"
                + "public privileged aspect Person_Advice perthis(this(Person)) {\n"
                + "    @Generated(id = 5, name = \"FirstField\", data = \"private:void:print;;\")\n"
                + "    public int Person.a = 3;\n\n"
                + "    @Generated(id = 5, name = \"SecondField\", data = \"private:void:print;;\")\n"
                + "    public int Person.b = 3;\n\n"
                + "    @Generated(id = {newid1}, name = \"FirstField\", data = \"public:String:do2;;\")\n"
                + "    public int Person.a = 3;\n\n"
                + "    @Generated(id = {newid1}, name = \"SecondField\", data = \"public:String:do2;;\")\n"
                + "    public int Person.b = 3;\n\n"
                + "    @Generated(id = {newid2}, name = \"FirstField\", data = \"public:String:do3;;\")\n"
                + "    public int Person.a = 3;\n\n"
                + "    @Generated(id = {newid2}, name = \"SecondField\", data = \"public:String:do3;;\")\n"
                + "    public int Person.b = 3;\n\n"
                + "    @Generated(id = 5, name = \"FirstAdvice\", data = \"private:void:print;;\")\n"
                + "    after() : execution(private void Person.print()) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = 5, name = \"SecondAdvice\", data = \"private:void:print;;\")\n"
                + "    after() : execution(private void Person.print()) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid1}, name = \"FirstAdvice\", data = \"public:String:do2;;\")\n"
                + "    after() returning(String returnValue) : execution(public String Person.do2()) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid1}, name = \"SecondAdvice\", data = \"public:String:do2;;\")\n"
                + "    after() returning(String returnValue) : execution(public String Person.do2()) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"FirstAdvice\", data = \"public:String:do3;;\")\n"
                + "    after() returning(String returnValue) : execution(public String Person.do3()) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"SecondAdvice\", data = \"public:String:do3;;\")\n"
                + "    after() returning(String returnValue) : execution(public String Person.do3()) {\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithClassAnd3MethodAnno(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");
        java6Class.addAnnotation(annotation);

        final Java6Annotation annotation2 = new Java6Annotation();
        annotation2.setName(anno);
        annotation2.addAttribute("id", "5");
        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType("private");
        java6Method.setName("print");
        java6Method.setType("void");
        java6Method.addAnnotation(annotation2);
        java6Class.addMethod(java6Method);

        final Java6Method java6Method2 = new Java6Method();
        java6Method2.setAccessType("public");
        java6Method2.setName("do2");
        java6Method2.setType("String");
        java6Class.addMethod(java6Method2);

        final Java6Method java6Method3 = new Java6Method();
        java6Method3.setAccessType("public");
        java6Method3.setName("do3");
        java6Method3.setType("String");
        java6Class.addMethod(java6Method3);

        return java6Class;
    }

    private static class AdviceGenerator implements AdviceForMethodGenerator {

        @Override
        public String getName() {
            return "de.hbrs.Advice";
        }

        @Override
        public void extendJavaClass(final ExtendMethodWithAdvices builder,
                final Map<String, String> properties) {
            final AdviceForMethod method = builder.appendNewAfterAdvice("FirstAdvice");
            method.addLine("System.out.println();");

            final AdviceForMethod method2 = builder.appendNewAfterAdvice("SecondAdvice");
            method2.addLine("System.out.println();");
        }

    }


    private static class AdviceAndFieldGenerator implements AdviceForMethodGenerator, FieldForMethodGenerator {

        @Override
        public String getName() {
            return "de.hbrs.Advice";
        }

        @Override
        public void extendJavaClass(final ExtendMethodWithAdvices builder,
                final Map<String, String> properties) {
            final AdviceForMethod method = builder.appendNewAfterAdvice("FirstAdvice");
            method.addLine("System.out.println();");

            final AdviceForMethod method2 = builder.appendNewAfterAdvice("SecondAdvice");
            method2.addLine("System.out.println();");
        }

        @Override
        public void extendJavaClass(final ExtendMethodWithFields builder,
                final Map<String, String> properties) {
            final FieldForMethod fieldForMethod = builder.appendNewField("FirstField");
            fieldForMethod.setContent("public int a = 3;");

            final FieldForMethod fieldForMethod2 = builder.appendNewField("SecondField");
            fieldForMethod2.setContent("public int b = 3;");
        }

    }
}
