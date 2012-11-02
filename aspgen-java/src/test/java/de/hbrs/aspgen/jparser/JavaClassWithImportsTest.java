package de.hbrs.aspgen.jparser;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;

public class JavaClassWithImportsTest {
    private final Java6Parser parser = new Java6Parser();

    @Test
    public void getImports() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithImports.java"));

        assertEquals("java.util.*", result.getImports().get(0));
        assertEquals("java.util.LinkedList", result.getImports().get(1));
    }

    @Test
    public void getStaticImports() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithImports.java"));

        assertEquals("org.junit.Assert.assertEquals", result.getStaticImports().get(0));
        assertEquals("org.junit.Assert.*", result.getStaticImports().get(1));
    }
}
