package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.generator.AdviceForField;
import de.hbrs.aspgen.api.generator.AdviceForFieldGenerator;
import de.hbrs.aspgen.api.generator.ExtendFieldWithAdvices;
import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Field;

public class JavaClassExtenderForFieldsWithAdvicesTest {

    @Test
    public void createAdviceForOneField() {
        final JavaClassExtender extender = new JavaClassExtender(createClassWithClassAnnotation("de.hbrs.Print"), new AdviceGen());
        final String result = extender.createAspectJFileContent();
        final String expectedResult = "import de.hbrs.aspgen.annotation.Generated;\n\npublic privileged aspect Person_Print perthis(this(Person)) {\n"
                + "    @Generated(id = {newid1}, name = \"Dummy\", data = \"String:name;\")\n    before() : execution(public void Person.start()) {\n"
                + "        System.out.println(name);\n"
                + "    }\n"
                + "}";
        assertEquals(expectedResult, result);
    }

    private JavaClass createClassWithClassAnnotation(final String anno) {
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

    private static class AdviceGen implements AdviceForFieldGenerator {
        @Override
        public String getName() {
            return "de.hbrs.Print";
        }

        @Override
        public void extendJavaClass(final ExtendFieldWithAdvices builder,
                final Map<String, String> properties) {
            final AdviceForField adviceForField = builder.appendNewAdvice("Dummy");
            adviceForField.setAdviceDeclaration("before() : execution(public void $classname$.start())");
            adviceForField.addLine("System.out.println($fieldname$);");
        }

    }
}
