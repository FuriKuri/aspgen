package de.hbrs.aspgen.jparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaField;

public class JavaClassWithFieldsTest {
    private final Java6Parser parser = new Java6Parser();

    @Test
    public void getFieldNames() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithFields.java"));

        final List<JavaField> fieldsResult = result.getFields();
        assertEquals("id", fieldsResult.get(0).getName());
        assertEquals("name", fieldsResult.get(1).getName());
        assertEquals("list", fieldsResult.get(2).getName());
        assertEquals("list2", fieldsResult.get(3).getName());

    }

    @Test
    public void getAccesstype() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithFields.java"));

        final List<JavaField> fieldsResult = result.getFields();
        assertEquals("private", fieldsResult.get(0).getAccessType());
        assertEquals("public", fieldsResult.get(1).getAccessType());
        assertEquals("protected", fieldsResult.get(2).getAccessType());

    }

    @Test
    public void getFieldTypes() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithFields.java"));

        final List<JavaField> fieldsResult = result.getFields();
        assertEquals("int", fieldsResult.get(0).getType());
        assertEquals("String", fieldsResult.get(1).getType());
        assertEquals("List<String>", fieldsResult.get(2).getType());
        assertEquals("List<String>", fieldsResult.get(3).getType());
    }

    @Test
    public void getAnnotations() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithFields.java"));

        final List<JavaField> fieldsResult = result.getFields();
        assertEquals(0, fieldsResult.get(0).getAnnotations().size());
        assertEquals("Anno1", fieldsResult.get(1).getAnnotations().get(0).getName());
        assertEquals("\"defaultValue\"", fieldsResult.get(1).getAnnotations().get(0).getSingleValue());
        assertEquals("SingleAnno", fieldsResult.get(1).getAnnotations().get(1).getName());
        assertEquals("Anno2", fieldsResult.get(2).getAnnotations().get(0).getName());
        assertEquals("1", fieldsResult.get(2).getAnnotations().get(0).getAttribute("value1"));
        assertEquals("Second.class", fieldsResult.get(2).getAnnotations().get(0).getAttribute("value2"));

    }

    @Test
    public void isStatic() {
        final JavaClass result = parser.parse(new File("src/test/resources/JavaClassWithFields.java"));

        final List<JavaField> fieldsResult = result.getFields();
        assertTrue(fieldsResult.get(0).isStatic());
        assertFalse(fieldsResult.get(1).isStatic());

    }
}
