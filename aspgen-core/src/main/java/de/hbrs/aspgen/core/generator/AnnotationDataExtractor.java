package de.hbrs.aspgen.core.generator;

import java.util.LinkedList;
import java.util.List;

import de.hbrs.aspgen.api.ast.JavaAnnotation;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.diff.AnnotationData;

public class AnnotationDataExtractor implements DataExtractor<List<AnnotationData>> {

    @Override
    public List<AnnotationData> extractDatasFromClass(final JavaClass javaClass) {
        final List<AnnotationData> datas = new LinkedList<>();
        for (final JavaAnnotation annotation : javaClass.getAllUsedAnnotations()) {
            if (annotation.getAttribute("id") != null) {
                final AnnotationData data = new AnnotationData(annotation.getAttribute("id"));
                final String modified = annotation.getStringAttribute("modified");
                if (modified != null) {
                    for (final String modifiedName : modified.split(",")) {
                        data.addModified(modifiedName.trim());
                    }
                }
                final String deleted = annotation.getStringAttribute("deleted");
                if (deleted != null) {
                    for (final String deletedName : deleted.split(",")) {
                        data.addDeleted(deletedName.trim());
                    }
                }
                datas.add(data);
            }
        }
        return datas;
    }

}
