package de.hbrs.aspgen.api.ast;

import java.util.List;

public interface JavaMethod extends UpdatableBlockForAnnotation {
    String getName();
    String getType();
    List<JavaParameter> getParameters();
    boolean isStatic();
    String getAccessType();
    String getMethodSignature();
}
