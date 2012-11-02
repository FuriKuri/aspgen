package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.generator.AdviceForParameter;
import de.hbrs.aspgen.api.generator.AdviceForParameterGenerator;
import de.hbrs.aspgen.api.generator.ExtendParameterWithAdvices;
import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Method;
import de.hbrs.aspgen.jparser.type.Java6Parameter;

public class GroupIdForParameterTest {
    @Test
    public void createTwoNewGroupIds() {
        final JavaClassExtender extender = new JavaClassExtender(createClass("de.hbrs.Advice"), new AdviceGenerator());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\n"
                + "public privileged aspect Person_Advice perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"FirstAdvice\", data = \"String:name;private:void:print;String:name,int:age;\")\n"
                + "    before(final String name) : execution(private void Person.print(String, int)) && args(name, *) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid1}, name = \"SecondAdvice\", data = \"String:name;private:void:print;String:name,int:age;\")\n"
                + "    before(final String name) : execution(private void Person.print(String, int)) && args(name, *) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"FirstAdvice\", data = \"int:age;private:void:print;String:name,int:age;\")\n"
                + "    before(final int age) : execution(private void Person.print(String, int)) && args(*, age) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"SecondAdvice\", data = \"int:age;private:void:print;String:name,int:age;\")\n"
                + "    before(final int age) : execution(private void Person.print(String, int)) && args(*, age) {\n"
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
        java6Class.addMethod(java6Method);

        final Java6Parameter java6Parameter = new Java6Parameter();
        java6Parameter.setName("name");
        java6Parameter.setType("String");
        java6Parameter.addAnnotation(annotation);
        java6Method.addParameter(java6Parameter);

        final Java6Parameter java6Parameter2 = new Java6Parameter();
        java6Parameter2.setName("age");
        java6Parameter2.setType("int");
        java6Parameter2.addAnnotation(annotation);
        java6Method.addParameter(java6Parameter2);

        return java6Class;
    }

    @Test
    public void createTwoNewGroupIdsAndOneFixId() {
        final JavaClassExtender extender = new JavaClassExtender(createClassAndMethodWithThreeParameters("de.hbrs.Advice"), new AdviceGenerator());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\n"
                + "public privileged aspect Person_Advice perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"FirstAdvice\", data = \"String:name;private:void:print;String:name,int:age,String:address;\")\n"
                + "    before(final String name) : execution(private void Person.print(String, int, String)) && args(name, *, *) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid1}, name = \"SecondAdvice\", data = \"String:name;private:void:print;String:name,int:age,String:address;\")\n"
                + "    before(final String name) : execution(private void Person.print(String, int, String)) && args(name, *, *) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = 2, name = \"FirstAdvice\", data = \"int:age;private:void:print;String:name,int:age,String:address;\")\n"
                + "    before(final int age) : execution(private void Person.print(String, int, String)) && args(*, age, *) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = 2, name = \"SecondAdvice\", data = \"int:age;private:void:print;String:name,int:age,String:address;\")\n"
                + "    before(final int age) : execution(private void Person.print(String, int, String)) && args(*, age, *) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"FirstAdvice\", data = \"String:address;private:void:print;String:name,int:age,String:address;\")\n"
                + "    before(final String address) : execution(private void Person.print(String, int, String)) && args(*, *, address) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"SecondAdvice\", data = \"String:address;private:void:print;String:name,int:age,String:address;\")\n"
                + "    before(final String address) : execution(private void Person.print(String, int, String)) && args(*, *, address) {\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassAndMethodWithThreeParameters(final String anno) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName(anno);

        final Java6Class java6Class = new Java6Class();
        java6Class.setClassName("Person");

        final Java6Method java6Method = new Java6Method();
        java6Method.setAccessType("private");
        java6Method.setName("print");
        java6Method.setType("void");
        java6Class.addMethod(java6Method);

        final Java6Parameter java6Parameter = new Java6Parameter();
        java6Parameter.setName("name");
        java6Parameter.setType("String");
        java6Parameter.addAnnotation(annotation);
        java6Method.addParameter(java6Parameter);

        final Java6Annotation annotation2 = new Java6Annotation();
        annotation2.setName(anno);
        annotation2.addAttribute("id", "2");
        final Java6Parameter java6Parameter2 = new Java6Parameter();
        java6Parameter2.setName("age");
        java6Parameter2.setType("int");
        java6Parameter2.addAnnotation(annotation2);
        java6Method.addParameter(java6Parameter2);

        final Java6Parameter java6Parameter3 = new Java6Parameter();
        java6Parameter3.setName("address");
        java6Parameter3.setType("String");
        java6Parameter3.addAnnotation(annotation);
        java6Method.addParameter(java6Parameter3);

        return java6Class;
    }

    private static class AdviceGenerator implements AdviceForParameterGenerator {

        @Override
        public String getName() {
            return "de.hbrs.Advice";
        }

        @Override
        public void extendJavaClass(final ExtendParameterWithAdvices builder,
                final Map<String, String> properties) {
            final AdviceForParameter method = builder.appendNewBeforeAdviceForParameter("FirstAdvice");
            method.addLine("System.out.println();");

            final AdviceForParameter method2 = builder.appendNewBeforeAdviceForParameter("SecondAdvice");
            method2.addLine("System.out.println();");
        }

    }
}
