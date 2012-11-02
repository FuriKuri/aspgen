package de.hbrs.aspgen.generator.builder;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.generator.DynamicPartsInBlocks;
import de.hbrs.aspgen.api.generator.ExtendClassWithMethods;
import de.hbrs.aspgen.api.generator.MethodForClass;
import de.hbrs.aspgen.api.generator.MethodForClassGenerator;
import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Field;

public class DynamicPartExtractorTest {
    @Test
    public void getDynamicPartsForToString() {
        final DynamicPartExtractor extractor = new DynamicPartExtractor(createJavaClass(), new ToStringGen());
        final List<DynamicPartsInBlocks> result = extractor.getDynamicParts();
        assertEquals("1", result.get(0).getId());
        assertEquals("ToString", result.get(0).getBlocks().get(0).getName());
        assertEquals("result += \"name = \" + name + \" \";", result.get(0).getBlocks().get(0).getDynamicParts().get(0).getDynamicLines().get(0));
        assertEquals("result += \"age = \" + age + \" \";", result.get(0).getBlocks().get(0).getDynamicParts().get(1).getDynamicLines().get(0));

    }

    private JavaClass createJavaClass() {
        final Java6Class javaClass = new Java6Class();
        javaClass.setClassName("Person");
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName("de.hbrs.ToString");
        annotation.addAttribute("id", "1");
        javaClass.addAnnotation(annotation);

        final Java6Field field = new Java6Field();
        field.setName("name");
        field.setType("String");
        javaClass.addField(field);

        final Java6Field field2 = new Java6Field();
        field2.setName("age");
        field2.setType("int");
        javaClass.addField(field2);

        return javaClass;
    }

    private static class ToStringGen implements MethodForClassGenerator {

        @Override
        public String getName() {
            return "de.hbrs.ToString";
        }

        @Override
        public void extendJavaClass(final ExtendClassWithMethods methodBuilder, final Map<String, String> properties) {
            final MethodForClass methodForClass = methodBuilder.appendNewMethod("ToString");
            methodForClass.setMethodDeclaration("public String toString()");
            methodForClass.addLine("String result = \"\";");
            methodForClass.addLineForeachField("result += \"$fieldname$ = \" + $fieldname$ + \" \";");
            methodForClass.addLine("return result;");
        }

    }
}
