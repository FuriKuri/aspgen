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

public class JavaClassExtenderForParameterTest {
    @Test
    public void createMethodWithExcludedField() {
        final JavaClassExtender extender = new JavaClassExtender(createClass("de.hbrs.Print"), new ParameterGen());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Print perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"String:name;private:void:print;String:name,int:age;\")\n    before(final String name) : execution(private void Person.print(String, int)) && args(name, *) {\n"
                + "        System.out.println(name);\n"
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
        java6Method.addParameter(java6Parameter2);

        return java6Class;
    }

    @Test
    public void createAdviceWithAnnoAtMethod() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithAnnoAtMethod("de.hbrs.Print"), new ParameterGen());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Print perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"String:name;private:void:print;String:name,int:age;\")\n    before(final String name) : execution(private void Person.print(String, int)) && args(name, *) {\n"
                + "        System.out.println(name);\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"Dummy\", data = \"int:age;private:void:print;String:name,int:age;\")\n    before(final int age) : execution(private void Person.print(String, int)) && args(*, age) {\n"
                + "        System.out.println(age);\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithAnnoAtMethod(final String anno) {
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

        final Java6Parameter java6Parameter = new Java6Parameter();
        java6Parameter.setName("name");
        java6Parameter.setType("String");
        java6Method.addParameter(java6Parameter);

        final Java6Parameter java6Parameter2 = new Java6Parameter();
        java6Parameter2.setName("age");
        java6Parameter2.setType("int");
        java6Method.addParameter(java6Parameter2);

        return java6Class;
    }

    private static class ParameterGen implements AdviceForParameterGenerator {
        @Override
        public String getName() {
            return "de.hbrs.Print";
        }

        @Override
        public void extendJavaClass(final ExtendParameterWithAdvices builder,
                final Map<String, String> properties) {
            final AdviceForParameter adviceForParameter = builder.appendNewBeforeAdviceForParameter("Dummy");
            adviceForParameter.addLine("System.out.println($parametername$);");
        }
    }
}
