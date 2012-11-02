package de.hbrs.aspgen.jparser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import de.hbrs.aspgen.jparser.type.Java6Annotation;
import de.hbrs.aspgen.jparser.type.Java6Class;
import de.hbrs.aspgen.jparser.type.Java6Field;
import de.hbrs.aspgen.jparser.type.Java6Method;
import de.hbrs.aspgen.jparser.type.Java6Parameter;

public class JavaClassWithAnnotationsAndImports {
    @Test
    public void replaceClassAnnotations() {
        final Java6Class java6Class = new Java6Class();
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName("ToString");
        java6Class.addAnnotation(annotation);
        final Java6Annotation annotation2 = new Java6Annotation();
        annotation2.setName("JavaBean");
        java6Class.addAnnotation(annotation2);
        final Java6Annotation annotation3 = new Java6Annotation();
        annotation3.setName("de.package.HashCode");
        java6Class.addAnnotation(annotation3);
        java6Class.addImport("de.hbrs.ToString");
        java6Class.addImport("de.pack.*");

        java6Class.replaceWithFullQualifiedAnnotations(new HashSet<>(Arrays.asList("de.hbrs.ToString", "de.package.HashCode", "de.pack.JavaBean")));

        assertEquals("de.hbrs.ToString", java6Class.getAnnotations().get(0).getName());
        assertEquals("de.pack.JavaBean", java6Class.getAnnotations().get(1).getName());
        assertEquals("de.package.HashCode", java6Class.getAnnotations().get(2).getName());
    }

    @Test
    public void replaceClassAnnotationsWithImports() {
        final Java6Class java6Class = new Java6Class();
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName("ToString");
        java6Class.addAnnotation(annotation);
        final Java6Annotation annotation2 = new Java6Annotation();
        annotation2.setName("JavaBean");
        java6Class.addAnnotation(annotation2);
        final Java6Annotation annotation3 = new Java6Annotation();
        annotation3.setName("de.package.HashCode");
        java6Class.addAnnotation(annotation3);
        java6Class.addImport("de.hbrs.ToString");

        java6Class.replaceWithFullQualifiedAnnotations(new HashSet<>(Arrays.asList("de.hbrs.ToString", "de.package.HashCode", "de.pack.JavaBean")));

        assertEquals("de.hbrs.ToString", java6Class.getAnnotations().get(0).getName());
        assertEquals("JavaBean", java6Class.getAnnotations().get(1).getName());
        assertEquals("de.package.HashCode", java6Class.getAnnotations().get(2).getName());
    }

    @Test
    public void replaceClassAndFieldAnnotations() {
        final Java6Class java6Class = new Java6Class();
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName("ToString");
        java6Class.addAnnotation(annotation);
        final Java6Annotation annotation2 = new Java6Annotation();
        annotation2.setName("JavaBean");
        java6Class.addAnnotation(annotation2);
        final Java6Annotation annotation3 = new Java6Annotation();
        annotation3.setName("de.package.HashCode");
        java6Class.addAnnotation(annotation3);

        final Java6Field java6Field = new Java6Field();
        final Java6Annotation annotation4= new Java6Annotation();
        annotation4.setName("FieldAnno1");
        java6Field.addAnnotation(annotation4);
        java6Class.addField(java6Field);

        final Java6Field java6Field2 = new Java6Field();
        final Java6Annotation annotation5= new Java6Annotation();
        annotation5.setName("FieldAnno2");
        java6Field2.addAnnotation(annotation5);
        java6Class.addField(java6Field2);

        java6Class.addImport("de.hbrs.ToString");
        java6Class.addImport("de.pack.*");
        java6Class.addImport("de.field.*");

        java6Class.replaceWithFullQualifiedAnnotations(new HashSet<>(Arrays.asList(
                "de.hbrs.ToString",
                "de.package.HashCode",
                "de.pack.JavaBean",
                "de.field.FieldAnno1",
                "de.fieldwithoutimport.FieldAnno2")));

        assertEquals("de.hbrs.ToString", java6Class.getAnnotations().get(0).getName());
        assertEquals("de.pack.JavaBean", java6Class.getAnnotations().get(1).getName());
        assertEquals("de.package.HashCode", java6Class.getAnnotations().get(2).getName());
        assertEquals("de.field.FieldAnno1", java6Class.getFields().get(0).getAnnotations().get(0).getName());
        assertEquals("FieldAnno2", java6Class.getFields().get(1).getAnnotations().get(0).getName());
    }

    @Test
    public void replaceClassAndMethodWithParameterAnnotations() {
        final Java6Class java6Class = new Java6Class();
        final Java6Annotation annotation = new Java6Annotation();
        annotation.setName("ToString");
        java6Class.addAnnotation(annotation);
        final Java6Annotation annotation2 = new Java6Annotation();
        annotation2.setName("JavaBean");
        java6Class.addAnnotation(annotation2);
        final Java6Annotation annotation3 = new Java6Annotation();
        annotation3.setName("de.package.HashCode");
        java6Class.addAnnotation(annotation3);

        final Java6Method java6Method = new Java6Method();
        final Java6Annotation annotation4 = new Java6Annotation();
        annotation4.setName("Anno1");
        java6Method.addAnnotation(annotation4);
        java6Class.addMethod(java6Method);

        final Java6Parameter java6Parameter = new Java6Parameter();
        final Java6Annotation annotation5 = new Java6Annotation();
        annotation5.setName("Anno2");
        java6Parameter.addAnnotation(annotation5);
        java6Method.addParameter(java6Parameter);

        final Java6Method java6Method2 = new Java6Method();
        final Java6Annotation annotation6 = new Java6Annotation();
        annotation6.setName("Anno3");
        java6Method2.addAnnotation(annotation6);
        java6Class.addMethod(java6Method2);

        java6Class.addImport("de.hbrs.ToString");
        java6Class.addImport("de.pack.*");
        java6Class.addImport("de.methodandparameter.*");

        java6Class.replaceWithFullQualifiedAnnotations(new HashSet<>(Arrays.asList("de.hbrs.ToString", "de.package.HashCode", "de.pack.JavaBean",
                "de.notimported.Anno1", "de.methodandparameter.Anno2", "de.methodandparameter.Anno3")));

        assertEquals("de.hbrs.ToString", java6Class.getAnnotations().get(0).getName());
        assertEquals("de.pack.JavaBean", java6Class.getAnnotations().get(1).getName());
        assertEquals("de.package.HashCode", java6Class.getAnnotations().get(2).getName());

        assertEquals("Anno1", java6Class.getMethods().get(0).getAnnotations().get(0).getName());
        assertEquals("de.methodandparameter.Anno2", java6Class.getMethods().get(0).getParameters().get(0).getAnnotations().get(0).getName());
        assertEquals("de.methodandparameter.Anno3", java6Class.getMethods().get(1).getAnnotations().get(0).getName());
    }
}
