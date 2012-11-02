package de.hbrs.aspgen.generator.placeholder;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;

public interface Replacer {

    String replace(String content, String onType, JavaField field);

    String replace(String content, String onType);

    String replace(String content, String onType, JavaMethod method,
            JavaParameter parameter);

    String replace(String content, String onType, JavaMethod method);

    String replaceWithFirstCharUpperCaseAndMethodChars(String firstReplaced, String onType,
            JavaMethod javaMethod);

}
