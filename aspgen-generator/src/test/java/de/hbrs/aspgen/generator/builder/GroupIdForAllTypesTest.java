package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.generator.AdviceForMethod;
import de.hbrs.aspgen.api.generator.AdviceForMethodGenerator;
import de.hbrs.aspgen.api.generator.AdviceForParameter;
import de.hbrs.aspgen.api.generator.AdviceForParameterGenerator;
import de.hbrs.aspgen.api.generator.ExtendFieldWithMethods;
import de.hbrs.aspgen.api.generator.ExtendMethodWithAdvices;
import de.hbrs.aspgen.api.generator.ExtendParameterWithAdvices;
import de.hbrs.aspgen.api.generator.MethodForField;
import de.hbrs.aspgen.api.generator.MethodForFieldGenerator;
import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Field;
import de.hbrs.aspgen.jparser.type.Java6Method;
import de.hbrs.aspgen.jparser.type.Java6Parameter;

public class GroupIdForAllTypesTest {

    @Test
    public void createThreeNewGroupIds() {
        final JavaClassExtender extender = new JavaClassExtender(createClass("de.hbrs.Alltypes"), new AllGenerator());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\n"
                + "public privileged aspect Person_Alltypes perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"Setter\", data = \"String:name;\")\n"
                + "    public void Person.setName(String name) {\n"
                + "        this.name = name;\n"
                + "    }\n\n"
                + "    @Generated(id = {newid2}, name = \"MAdvice\", data = \"private:void:print;String:name;\")\n"
                + "    after(final String name) : execution(private void Person.print(String)) && args(name) {\n"
                + "        System.out.println();\n"
                + "    }\n\n"
                + "    @Generated(id = {newid3}, name = \"PAdvice\", data = \"String:name;private:void:print;String:name;\")\n"
                + "    before(final String name) : execution(private void Person.print(String)) && args(name) {\n"
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

        final Java6Parameter java6Parameter = new Java6Parameter();
        java6Parameter.setName("name");
        java6Parameter.setType("String");
        java6Parameter.addAnnotation(annotation);
        java6Method.addParameter(java6Parameter);

        return java6Class;
    }

    private static class AllGenerator implements MethodForFieldGenerator, AdviceForParameterGenerator, AdviceForMethodGenerator {

        @Override
        public String getName() {
            return "de.hbrs.Alltypes";
        }

        @Override
        public void extendJavaClass(final ExtendMethodWithAdvices builder,
                final Map<String, String> properties) {
            final AdviceForMethod method = builder.appendNewAfterAdvice("MAdvice");
            method.addLine("System.out.println();");
        }

        @Override
        public void extendJavaClass(final ExtendParameterWithAdvices builder,
                final Map<String, String> properties) {
            final AdviceForParameter method = builder.appendNewBeforeAdviceForParameter("PAdvice");
            method.addLine("System.out.println();");

        }

        @Override
        public void extendJavaClass(final ExtendFieldWithMethods builder,
                final Map<String, String> properties) {
            final MethodForField setterMethod = builder.appendNewMethod("Setter");
            setterMethod.setMethodDeclaration("public void set$fieldname$($fieldtype$ $fieldname$)");
            setterMethod.addLine("this.$fieldname$ = $fieldname$;");

        }

    }
}
