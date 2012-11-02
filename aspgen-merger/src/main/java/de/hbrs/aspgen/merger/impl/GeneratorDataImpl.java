package de.hbrs.aspgen.merger.impl;

import de.hbrs.aspgen.api.diff.AnnotationDataContainer;
import de.hbrs.aspgen.api.merge.GeneratorData;

public class GeneratorDataImpl extends AnnotationDataContainer implements GeneratorData {
    private final String annotation;
    private final String classname;

    public GeneratorDataImpl(final String annotation, final String classname) {
        this.annotation = annotation;
        this.classname = classname;
    }

    @Override
    public String getAnnotation() {
        return annotation;
    }

    @Override
    public String getClassname() {
        return classname;
    }

}
