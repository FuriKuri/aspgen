package de.hbrs.aspgen.api.diff;


public interface ContentUpdater {

    String updatePackageName(String content, String packageName);

    String updateClassName(String content, String className);

    String removeNotModifiedBlocks(String content, AnnotationDataContainer dataContainer);

    String deleteGeneratedBlocks(String content);

}
