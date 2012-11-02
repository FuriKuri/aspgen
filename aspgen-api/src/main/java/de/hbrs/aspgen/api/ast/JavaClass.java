package de.hbrs.aspgen.api.ast;

import java.util.List;
import java.util.Set;


public interface JavaClass extends UpdatableBlockForAnnotation {
    String getClassName();
    String getPackageName();
    List<String> getImports();
    List<String> getStaticImports();
    List<JavaField> getFields();
    List<JavaMethod> getMethods();
    List<JavaAnnotation> getAllUsedAnnotations();
    void replaceWithFullQualifiedAnnotations(final Set<String> fullQualifedAnnotations);
    boolean isInterface();
    void removeAnnotationAttribute(String attribute);
}
