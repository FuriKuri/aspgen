package de.hbrs.aspgen.api.merge;

import java.util.List;

import de.hbrs.aspgen.api.diff.AnnotationData;

public interface GeneratorData {
    String getAnnotation();

    String getClassname();

    boolean containsIdWithChangedName(String annotationId, String annotationName);

    boolean containsIdWithDeletedName(String annotationId, String annotationName);

    void addAll(List<AnnotationData> annotationDatas);
}
