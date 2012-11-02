package de.hbrs.aspgen.generator.container;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hbrs.aspgen.api.generator.FieldForClass;
import de.hbrs.aspgen.generator.builder.AspectJBlock;

public class FieldForClassContainer extends AspectJContainer implements AspectJBlock, FieldForClass {
    protected String contentLine;

    @Override
    public void setContent(final String content) {
        final int indexOfFieldName = getStartIndexFromFieldName(content);
        final String contentWithOnTypeBeforeField = content.substring(0, indexOfFieldName)
                + onType + "."
                + content.substring(indexOfFieldName);
        setReplacedContent(contentWithOnTypeBeforeField);
    }

    protected void setReplacedContent(final String content) {
        final String firstReplaced;
        if (fieldnameStartWithPlaceHolder(content, "classname")) {
            firstReplaced = content.replaceFirst("\\$classname\\$", firstCharAsLowerCase(onType));
        } else {
            firstReplaced = content;
        }
        this.contentLine = replacer.replace(firstReplaced, onType);
    }

    protected String firstCharAsLowerCase(final String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }

    protected int getStartIndexFromFieldName(final String content) {
        final Pattern p = Pattern.compile("\\s(\\w*\\$\\w+\\$\\w*|\\w+)+\\s*=\\s*.*;|\\s(\\w*\\$\\w+\\$\\w*|\\w+)+\\s*;");
        final Matcher m = p.matcher(content);
        if (m.find()) {
            return m.start() + 1;
        } else {
            throw new RuntimeException("Error while try to find field name");
        }
    }

    protected boolean fieldnameStartWithPlaceHolder(final String content, final String placeholder) {
        final Pattern p = Pattern.compile("\\.\\$" + placeholder + "\\$\\w*\\s*=\\s*.*;|\\.\\$" + placeholder + "\\$\\w*\\s*;");
        final Matcher m = p.matcher(content);
        return m.find();
    }

    public String getData() {
        return "";
    }

    @Override
    public String createBlockContent(final String id, final Map<String, String> properties) {
        final String javaDocPart = createJavaDocPart();
        // FIXME add annotationProperties
        final String data = getData() + ";" + getPropertiesAsString(properties);
        final String annotationPart = createAnnotationPart(id, data);
        return  javaDocPart
                + annotationPart
                + TAB + contentLine;
    }

}
