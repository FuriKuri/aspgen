package de.hbrs.aspgen.api.parser;

import java.io.File;

import de.hbrs.aspgen.api.ast.JavaClass;


public interface JavaParser {
    JavaClass parse(File file);

    JavaClass parse(String javaContent);
}
