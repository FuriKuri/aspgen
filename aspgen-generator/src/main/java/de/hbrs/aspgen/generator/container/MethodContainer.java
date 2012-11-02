package de.hbrs.aspgen.generator.container;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hbrs.aspgen.generator.builder.AspectJBlock;
import de.hbrs.aspgen.generator.builder.ContentPart;
import de.hbrs.aspgen.generator.builder.StaticLine;


public abstract class MethodContainer extends AspectJContainer implements AspectJBlock {
    protected static final String FIELD_NAME_PLACEHOLDER = "$fieldname$";
    protected static final String FIELD_TYPE_PLACEHOLDER = "$fieldtype$";
    protected static final String CLASS_NAME_PLACEHOLDER = "$classname$";
    protected static final String FIELD_NAME_PLACEHOLDER_AS_REGEX = "\\$fieldname\\$";
    protected static final String FIELD_TYPE_PLACEHOLDER_AS_REGEX = "\\$fieldtype\\$";


    protected final List<ContentPart> contentParts = new LinkedList<>();
    protected String methodSignatur;

    public void addLine(final String staticPart) {
        contentParts.add(new StaticLine(staticPart));
    }

    public void setMethodDeclaration(final String methodSignatur) {
        final int indexOfMethodName = getStartIndexFromMethodName(methodSignatur);
        final String methodSignaturWithOnType = methodSignatur.substring(0, indexOfMethodName)
                + onType + "."
                + methodSignatur.substring(indexOfMethodName);
        this.methodSignatur = methodSignaturWithOnType;
    }

    protected int getStartIndexFromMethodName(final String methodSignaturWithoutOnType) {
        final Pattern p = Pattern.compile("\\s\\w*((\\$\\w+\\$)?\\w*)*\\([\\s*\\w*(\\$\\w+\\$)?\\w*\\s*,?]*\\)");
        final Matcher m = p.matcher(methodSignaturWithoutOnType);
        if (m.find()) {
            return m.start() + 1;
        } else {
            throw new RuntimeException("Error while try to find methodname");
        }
    }
}
