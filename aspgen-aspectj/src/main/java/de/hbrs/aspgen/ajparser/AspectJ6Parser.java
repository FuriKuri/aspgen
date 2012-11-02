package de.hbrs.aspgen.ajparser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.aspectj.org.eclipse.jdt.core.dom.AST;
import org.aspectj.org.eclipse.jdt.core.dom.ASTParser;
import org.aspectj.org.eclipse.jdt.core.dom.CompilationUnit;

import de.hbrs.aspgen.ajparser.type.AspectJ6Class;
import de.hbrs.aspgen.api.ast.AspectJUnit;
import de.hbrs.aspgen.api.parser.AspectJParser;

public class AspectJ6Parser implements AspectJParser {
    @Override
    public AspectJUnit parse(final String source) {
        final ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setCompilerOptions(new HashMap<String, Object>());
        parser.setSource(source.toCharArray());
        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        final AspectJ6Class result = new AspectJ6Class();
        cu.accept(new AspectJVisitor(result, source));
        return result;
    }

    @Override
    public AspectJUnit parse(final File file) {
        try {
            return parse(FileUtils.readFileToString(file));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
