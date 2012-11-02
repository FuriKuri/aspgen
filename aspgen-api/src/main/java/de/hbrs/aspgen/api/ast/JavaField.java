package de.hbrs.aspgen.api.ast;

public interface JavaField extends UpdatableBlockForAnnotation {
    String getName();
    String getType();
    boolean isStatic();
    String getAccessType();
}
