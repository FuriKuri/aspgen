package de.hbrs.aspgen.api.ast;

import java.util.Map;

public interface JavaAnnotation {
    String getName();
    String getAttribute(String key);
    String getSingleValue();
    void updateFullQualifedName(String fullQualifedAnnotation);
    Map<String, String> getAllAttribute();
    String getStringAttribute(String string);
    String getAnnotationAsString();
    int getStartPosition();
    int getEndPosition();
    void removeAttribute(String attribute);

}
