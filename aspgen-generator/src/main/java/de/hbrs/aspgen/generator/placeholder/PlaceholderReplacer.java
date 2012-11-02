package de.hbrs.aspgen.generator.placeholder;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;

public class PlaceholderReplacer implements Replacer {
    private static final String FIELD_NAME_PLACEHOLDER = "$fieldname$";
    private static final String FIELD_TYPE_PLACEHOLDER = "$fieldtype$";
    private static final String CLASS_NAME_PLACEHOLDER = "$classname$";
    private static final String THIS_CLASS_NAME_PLACEHOLDER = "$thisclassname$";
    private static final String RAW_METHOD_SIGNATURE_PLACEHOLDER = "$rawmethodsignature$";
    private static final String METHOD_SIGNATURE_PLACEHOLDER = "$methodsignature$";
    private static final String METHOD_NAME_PLACEHOLDER = "$methodname$";
    private static final String METHOD_TYPE_PLACEHOLDER = "$methodtype$";
    private static final String PARAMETER_NAME_PLACEHOLDER = "$parametername$";
    private static final String PARAMETER_TYPE_PLACEHOLDER = "$parametertype$";

    @Override
    public String replace(final String stringToBeReplaced, final String javaName, final JavaField javaField) {
        return replace(replace(stringToBeReplaced, javaName), javaField);
    }

    @Override
    public String replace(final String stringToBeReplaced, final String javaName, final JavaMethod method,
            final JavaParameter parameter) {
        return replace(replace(replace(stringToBeReplaced, javaName), method), parameter);
    }

    @Override
    public String replace(final String stringToBeReplaced, final String javaName, final JavaMethod method) {
        return replace(replace(stringToBeReplaced, javaName), method);
    }

    @Override
    public String replace(final String stringToBeReplaced, final String javaName) {
        return stringToBeReplaced.replace(CLASS_NAME_PLACEHOLDER, javaName)
                .replace(THIS_CLASS_NAME_PLACEHOLDER, getStringWithFirstCharLowerCase(javaName));

    }

    public String replace(final String stringToBeReplaced, final JavaField javaField) {
        return stringToBeReplaced
                .replace(FIELD_NAME_PLACEHOLDER, javaField.getName())
                .replace(FIELD_TYPE_PLACEHOLDER, javaField.getType());
    }

    public String replace(final String stringToBeReplaced, final JavaMethod javaMethod) {
        return stringToBeReplaced
                .replace(METHOD_NAME_PLACEHOLDER, getStringWithFirstCharUpperCase(javaMethod.getName()))
                .replace(METHOD_TYPE_PLACEHOLDER, getStringWithFirstCharUpperCase(javaMethod.getType()))
                .replace(RAW_METHOD_SIGNATURE_PLACEHOLDER, javaMethod.getMethodSignature())
                .replace(METHOD_SIGNATURE_PLACEHOLDER, getStringWithFirstCharLowerCase(removeMethodChars(javaMethod.getMethodSignature())));
    }

    public String replace(final String stringToBeReplaced, final JavaParameter javaParameter) {
        return stringToBeReplaced
                .replace(PARAMETER_NAME_PLACEHOLDER, javaParameter.getName())
                .replace(PARAMETER_TYPE_PLACEHOLDER, javaParameter.getType());
    }

    @Override
    public String replaceWithFirstCharUpperCaseAndMethodChars(final String stringToBeReplaced,
            final String onType, final JavaMethod javaMethod) {
        return stringToBeReplaced
                .replace(CLASS_NAME_PLACEHOLDER, getStringWithFirstCharUpperCase(onType))
                .replace(METHOD_NAME_PLACEHOLDER, getStringWithFirstCharUpperCase(javaMethod.getName()))
                .replace(METHOD_TYPE_PLACEHOLDER, getStringWithFirstCharUpperCase(javaMethod.getType()))
                .replace(METHOD_SIGNATURE_PLACEHOLDER, getStringWithFirstCharLowerCase(removeMethodChars(javaMethod.getMethodSignature())));
    }

    private String removeMethodChars(final String string) {
        return string.replaceAll("\\(|\\)|@|\\s|,", "");
    }


    private String getStringWithFirstCharUpperCase(final String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    private String getStringWithFirstCharLowerCase(final String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }
}
