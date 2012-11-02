package de.hbrs.aspgen.jparser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;

public class JavaInterfaceTest {
    private final Java6Parser parser = new Java6Parser();

    @Test
    public void javaFileisClass() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithFields.java"));
        assertFalse(result.isInterface());
    }


    @Test
    public void javaFileIsInterface() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaInterfaceWithImports.java"));
        assertTrue(result.isInterface());
    }
}
