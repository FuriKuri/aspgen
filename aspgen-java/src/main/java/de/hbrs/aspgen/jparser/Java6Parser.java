package de.hbrs.aspgen.jparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.aspectj.org.eclipse.jdt.core.dom.AST;
import org.aspectj.org.eclipse.jdt.core.dom.ASTParser;
import org.aspectj.org.eclipse.jdt.core.dom.CompilationUnit;

import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.exception.FileNotExistException;
import de.hbrs.aspgen.api.parser.JavaParser;
import de.hbrs.aspgen.jparser.type.Java6Class;

public class Java6Parser implements JavaParser {
    public JavaClass parse(final String source) {
        final ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setCompilerOptions(new HashMap<String, Object>());
        parser.setSource(source.toCharArray());
        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        final Java6Class result = new Java6Class();
        cu.accept(new Java6Visitor(result, source));
        return result;
    }

    @Override
    public JavaClass parse(final File file) {
        try {
            final String javaFileAsString = tryToReadFile(file, 4);
            return parse(javaFileAsString);
        } catch (final IOException e) {
            if (e instanceof FileNotFoundException) {
                throw new FileNotExistException("File not exist anymore", e);
            }
            throw new RuntimeException(e);
        }
    }

    // TODO auch f@r ajparser???
    private String tryToReadFile(final File file, final int trysLeft) throws IOException {
        final String result = FileUtils.readFileToString(file);
        if (!result.isEmpty()) {
            return result;
        } else if (trysLeft == 0) {
            return "";
        } else {
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            return tryToReadFile(file, trysLeft - 1);
        }
    }
}
