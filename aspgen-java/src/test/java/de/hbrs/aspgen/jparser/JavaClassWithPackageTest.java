package de.hbrs.aspgen.jparser;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;

public class JavaClassWithPackageTest {
    private final Java6Parser parser = new Java6Parser();

    @Test
    public void getPackageName() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithPackage.java"));

        assertEquals("de.hbrs", result.getPackageName());
    }

    @Test
    public void getClassName() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithPackage.java"));

        assertEquals("JavaClassWithPackage", result.getClassName());
    }

}
