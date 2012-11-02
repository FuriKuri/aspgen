package de.hbrs.aspgen.generator.container;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.generator.MethodForField;
import de.hbrs.aspgen.generator.builder.ContentPart;

public class MethodForFieldContainer extends MethodContainer implements MethodForField {
    protected JavaField field;

    public void setJavaField(final JavaField javaField) {
        field = javaField;
    }

    @Override
    public String createBlockContent(final String id, final Map<String, String> properties) {
        // FIXME add annotationProperties
        final String data = field.getType() + ":" + field.getName() + ";" + getPropertiesAsString(properties);

        final StringBuffer blocks = new StringBuffer();
        final String javaDocPart = createJavaDocPart();
        final String annotationPart = createAnnotationPart(id, data);
        final String methodSignatur = TAB + replacePlaceholderForMethodname(field);
        final String blockBegin = SPACE + "{";
        final String content = createMethodBody(field);
        final String blockEnd = NEWLINE + TAB + "}";
        blocks.append(javaDocPart
                + annotationPart
                + methodSignatur
                + blockBegin
                + content
                + blockEnd);

        return blocks.toString();
    }

    private String replacePlaceholderForMethodname(final JavaField javaField) {
        final int indexMethodNameEnd = methodSignatur.indexOf("(");
        final String tillMethodNamePart = replaceReturnType(methodSignatur.substring(0, indexMethodNameEnd), javaField);
        final String fromMethodNamePart = methodSignatur.substring(indexMethodNameEnd);

        final String replacedTillMethodNamePart;
        if (methodSignatur.contains(FIELD_NAME_PLACEHOLDER) && methodNameStartWithPlaceholder(FIELD_NAME_PLACEHOLDER_AS_REGEX)) {
            final String firstReplaceWithFirstCharLowerCase = tillMethodNamePart.replaceFirst(FIELD_NAME_PLACEHOLDER_AS_REGEX, javaField.getName());
            replacedTillMethodNamePart = replacePlaceholderWithFirstCharUpperCase(javaField, firstReplaceWithFirstCharLowerCase);
        } else if (methodSignatur.contains(FIELD_TYPE_PLACEHOLDER) && methodNameStartWithPlaceholder(FIELD_TYPE_PLACEHOLDER_AS_REGEX)) {
            final String firstReplaceWithFirstCharLowerCase = tillMethodNamePart.replaceFirst(FIELD_TYPE_PLACEHOLDER_AS_REGEX, lowerCharAtFirstPos(javaField.getType()));
            replacedTillMethodNamePart = replacePlaceholderWithFirstCharUpperCase(javaField, firstReplaceWithFirstCharLowerCase);
        } else {
            replacedTillMethodNamePart = replacePlaceholderWithFirstCharUpperCase(javaField, tillMethodNamePart);
        }

        return replacedTillMethodNamePart + fromMethodNamePart
                .replace(FIELD_NAME_PLACEHOLDER, javaField.getName())
                .replace(FIELD_TYPE_PLACEHOLDER, javaField.getType());
    }

    private String lowerCharAtFirstPos(final String string) {
        final String charAtBegin = string.substring(0, 1);
        return charAtBegin.toLowerCase() + string.substring(1);
    }

    private String replaceReturnType(final String substring, final JavaField javaField) {
        final Pattern p = Pattern.compile("\\s" + FIELD_TYPE_PLACEHOLDER_AS_REGEX + "\\s+\\w+\\.");
        final Matcher m = p.matcher(substring);
        if (m.find()) {
            return substring.replaceFirst(FIELD_TYPE_PLACEHOLDER_AS_REGEX, javaField.getType());
        } else {
            return substring;
        }
    }

    private boolean methodNameStartWithPlaceholder(final String placeholder) {
        final Pattern p = Pattern.compile("\\s\\w+\\." + placeholder + "((\\$\\w+\\$)*\\w*)*\\([\\s*\\w*\\s*,?]*\\)");
        final Matcher m = p.matcher(methodSignatur);
        return m.find();
    }

    private String replacePlaceholderWithFirstCharUpperCase(final JavaField javaField, final String nameToReplace) {
        final String replaceFieldName = javaField.getName().substring(0, 1).toUpperCase() +
                javaField.getName().substring(1);
        final String replaceFieldtype = javaField.getType().substring(0, 1).toUpperCase() +
                javaField.getType().substring(1);
        return nameToReplace.replace(FIELD_NAME_PLACEHOLDER, replaceFieldName).replace(FIELD_TYPE_PLACEHOLDER, replaceFieldtype);
    }

    private String createMethodBody(final JavaField field) {
        String methodBodyContent = "";
        for (final ContentPart part : contentParts) {
            methodBodyContent += replacer.replace(part.getContent(), onType, field);
        }
        return methodBodyContent;
    }

}
