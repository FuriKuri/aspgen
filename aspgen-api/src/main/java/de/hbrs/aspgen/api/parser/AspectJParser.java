package de.hbrs.aspgen.api.parser;

import java.io.File;

import de.hbrs.aspgen.api.ast.AspectJUnit;


public interface AspectJParser {
    AspectJUnit parse(final String source);
    AspectJUnit parse(final File file);
}
