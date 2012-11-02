package de.hbrs.aspgen.generator.container;

import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.generator.FieldForMethod;

public class FieldForMethodContainer extends FieldForClassContainer implements FieldForMethod {
    private JavaMethod javaMethod;

    public void setMethod(final JavaMethod javaMethod) {
        this.javaMethod = javaMethod;
    }

    @Override
    protected void setReplacedContent(final String content) {
        final String firstReplaced;
        if (fieldnameStartWithPlaceHolder(content, "classname")) {
            firstReplaced = content.replaceFirst("\\$classname\\$", firstCharAsLowerCase(onType));
        } else if (fieldnameStartWithPlaceHolder(content, "methodname")) {
            firstReplaced = content.replaceFirst("\\$methodname\\$", firstCharAsLowerCase(javaMethod.getName()));
        } else if (fieldnameStartWithPlaceHolder(content, "methodsignature")) {
            firstReplaced = content.replaceFirst("\\$methodsignature\\$", firstCharAsLowerCase(removeMethodChars(javaMethod.getMethodSignature())));
        } else {
            firstReplaced = content;
        }
        this.contentLine = replacer.replaceWithFirstCharUpperCaseAndMethodChars(firstReplaced, onType, javaMethod);
    }

    private String removeMethodChars(final String string) {
        return string.replaceAll("\\(|\\)|@|\\s|,", "");
    }

    @Override
    public String getData() {
        return getMethodData(javaMethod);
    }
}
