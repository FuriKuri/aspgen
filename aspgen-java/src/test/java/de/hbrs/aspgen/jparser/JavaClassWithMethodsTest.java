package de.hbrs.aspgen.jparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaMethod;

public class JavaClassWithMethodsTest {
    private final Java6Parser parser = new Java6Parser();

    @Test
    public void getMethodNames() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithMethods.java"));

        final List<JavaMethod> methods = result.getMethods();
        assertEquals("getName", methods.get(0).getName());
        assertEquals("setId", methods.get(1).getName());
        assertEquals("m2", methods.get(2).getName());
        assertEquals("m3", methods.get(3).getName());
    }

    @Test
    public void getAccessType() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithMethods.java"));

        final List<JavaMethod> methods = result.getMethods();
        assertEquals("public", methods.get(0).getAccessType());
        assertEquals("private", methods.get(1).getAccessType());
        assertEquals("protected", methods.get(2).getAccessType());
        assertEquals("", methods.get(3).getAccessType());
    }

    @Test
    public void getMethodTypes() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithMethods.java"));

        final List<JavaMethod> methods = result.getMethods();
        assertEquals("String", methods.get(0).getType());
        assertEquals("void", methods.get(1).getType());
        assertEquals("int", methods.get(2).getType());
        assertEquals("void", methods.get(3).getType());
    }

    @Test
    public void getAnnotations() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithMethods.java"));

        final List<JavaMethod> methods = result.getMethods();
        assertEquals("MethodAnno1", methods.get(0).getAnnotations().get(0).getName());
        assertEquals("1234", methods.get(0).getAnnotations().get(0).getSingleValue());
        assertEquals("MethodAnno1", methods.get(1).getAnnotations().get(0).getName());
        assertEquals("MethodAnno2", methods.get(1).getAnnotations().get(1).getName());
    }

    @Test
    public void isStatic() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithMethods.java"));

        final List<JavaMethod> methods = result.getMethods();
        assertFalse(methods.get(0).isStatic());
        assertTrue(methods.get(2).isStatic());

    }

    @Test
    public void getParameters() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithMethods.java"));

        final List<JavaMethod> methods = result.getMethods();
        assertEquals(0, methods.get(0).getParameters().size());
        assertEquals("int", methods.get(1).getParameters().get(0).getType());
        assertEquals("id", methods.get(1).getParameters().get(0).getName());
        assertEquals("String", methods.get(1).getParameters().get(1).getType());
        assertEquals("newName", methods.get(1).getParameters().get(1).getName());

        assertEquals("int", methods.get(2).getParameters().get(0).getType());
        assertEquals("a", methods.get(2).getParameters().get(0).getName());
        assertEquals("double", methods.get(2).getParameters().get(1).getType());
        assertEquals("b", methods.get(2).getParameters().get(1).getName());

        assertEquals("NotNull", methods.get(2).getParameters().get(1).getAnnotations().get(0).getName());
        assertEquals("Size", methods.get(2).getParameters().get(1).getAnnotations().get(1).getName());
        assertEquals("1", methods.get(2).getParameters().get(1).getAnnotations().get(1).getAttribute("value"));

    }
}
