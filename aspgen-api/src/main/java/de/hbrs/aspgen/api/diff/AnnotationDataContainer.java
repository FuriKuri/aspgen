package de.hbrs.aspgen.api.diff;

import java.util.LinkedList;
import java.util.List;


public class AnnotationDataContainer {
    private final List<AnnotationData> datas = new LinkedList<>();

    public void addGeneratorData(final AnnotationData data) {
        datas.add(data);
    }

    public boolean containsId(final String annotationId) {
        for (final AnnotationData data : datas) {
            if (data.getId().equals(annotationId)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsIdWithChangedName(final String annotationId,
            final String annotationName) {
        for (final AnnotationData data : datas) {
            if (data.getId().equals(annotationId) && data.getModified().contains(annotationName)) {
                return true;
            }
        }
        return false;
    }


    public void addAll(final List<AnnotationData> annotationDatas) {
        datas.addAll(annotationDatas);
    }

    public boolean containsIdWithDeletedName(final String annotationId, final String annotationName) {
        for (final AnnotationData data : datas) {
            if (data.getId().equals(annotationId) && data.getDeleted().contains(annotationName)) {
                return true;
            }
        }
        return false;
    }
}
