package de.hbrs.aspgen.api.diff;



public interface DiffCreator {
    AspectJDiff createDiff(String aspectJContent, String javaContent);
}
