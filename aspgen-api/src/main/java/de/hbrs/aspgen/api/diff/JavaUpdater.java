package de.hbrs.aspgen.api.diff;



public interface JavaUpdater {

    String updateJavaContent(String javaContent, AspectJDiff aspectJDiff);

    String removeAnnotations(String javaContent, String aspectJName);

}
