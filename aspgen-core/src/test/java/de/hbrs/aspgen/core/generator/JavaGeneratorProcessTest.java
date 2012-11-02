package de.hbrs.aspgen.core.generator;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaAnnotation;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Field;

public class JavaGeneratorProcessTest {
    @Test
    public void extractAnnotationData() {
        final List<AnnotationData> result = new AnnotationDataExtractor().extractDatasFromClass(createJavaClass());
        assertEquals("1", result.get(0).getId());
        assertEquals("ToString", result.get(0).getModified().get(0));
        assertEquals("Get", result.get(0).getModified().get(1));
        assertEquals("Set", result.get(0).getDeleted().get(0));

        assertEquals("2", result.get(1).getId());
        assertEquals("ToString2", result.get(1).getModified().get(0));
        assertEquals("Get2", result.get(1).getModified().get(1));
        assertEquals("Set2", result.get(1).getDeleted().get(0));
        assertEquals("Test", result.get(1).getDeleted().get(1));
    }

    private JavaClass createJavaClass() {
        final Java6Class java6Class = new Java6Class();
        java6Class.addAnnotation(createAnnotation("1", "\"ToString, Get\"", "\"Set\""));

        final Java6Field field = new Java6Field();
        java6Class.addField(field);
        field.addAnnotation(createAnnotation("2", "\"ToString2, Get2\"", "\"Set2, Test\""));

        return java6Class;
    }

    private JavaAnnotation createAnnotation(final String id, final String mod, final String deleted) {
        final Java6Annotation annotation = new Java6Annotation();
        annotation.addAttribute("id", id);
        annotation.addAttribute("modified", mod);
        annotation.addAttribute("deleted", deleted);
        return annotation;
    }
}
