package de.hbrs.aspgen.jparser;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaAnnotation;
import de.hbrs.aspgen.api.ast.JavaClass;

public class JavaClassWithAnnotationsTest {
    private final Java6Parser parser = new Java6Parser();

    @Test
    public void getFieldNames() {
        final JavaClass result = parser.parse(new File("src/test/resources/ClassWithAnnotations.java"));

        final JavaAnnotation annotation1 = result.getAnnotations().get(0);
        final JavaAnnotation annotation2 = result.getAnnotations().get(1);
        final JavaAnnotation annotation3 = result.getAnnotations().get(2);

        assertEquals("JavaBean", annotation1.getName());
        assertEquals("ToString", annotation2.getName());
        assertEquals("true", annotation2.getSingleValue());
        assertEquals("NotNull", annotation3.getName());
        assertEquals("true", annotation3.getAttribute("exclude"));
        assertEquals("String.class", annotation3.getAttribute("someClass"));
        assertEquals("\"value\"", annotation3.getAttribute("stringValue"));
        assertEquals("value", annotation3.getStringAttribute("stringValue"));

    }

    @Test
    public void getUsedAnnotations() {
        final JavaClass result = parser.parse(new File("src/test/resources/ClassWithAnnotations.java"));
        assertEquals(9, result.getAllUsedAnnotations().size());
        assertEquals("JavaBean", result.getAllUsedAnnotations().get(0).getName());
        assertEquals("ToString", result.getAllUsedAnnotations().get(1).getName());
        assertEquals("NotNull", result.getAllUsedAnnotations().get(2).getName());
        assertEquals("Anno1", result.getAllUsedAnnotations().get(3).getName());
        assertEquals("SingleAnno", result.getAllUsedAnnotations().get(4).getName());
        assertEquals("MethodAnno1", result.getAllUsedAnnotations().get(5).getName());
        assertEquals("MethodAnno2", result.getAllUsedAnnotations().get(6).getName());
        assertEquals("NotNull", result.getAllUsedAnnotations().get(7).getName());
        assertEquals("Size", result.getAllUsedAnnotations().get(8).getName());
    }

    @Test
    public void getStringValue() {
        final JavaClass result = parser.parse(new File("src/test/resources/ClassWithAnnotations.java"));
        final JavaAnnotation annotation1 = result.getAnnotations().get(0);
        final JavaAnnotation annotation2 = result.getAnnotations().get(1);
        final JavaAnnotation annotation3 = result.getAnnotations().get(2);
        assertEquals("@NotNull(exclude=true, someClass=String.class, stringValue=\"value\")", annotation3.getAnnotationAsString());
        assertEquals("@ToString(true)", annotation2.getAnnotationAsString());
        assertEquals("@JavaBean", annotation1.getAnnotationAsString());

    }

}
