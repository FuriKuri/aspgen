package de.hbrs.aspgen.generator.container;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.generator.placeholder.PlaceholderReplacer;
import de.hbrs.aspgen.generator.placeholder.Replacer;

public abstract class AspectJContainer {
    public final static String TAB = "    ";
    public final static String SPACE = " ";
    public final static String NEWLINE = "\n";
    protected String onType;
    protected String javaDoc;
    protected final List<String> annotations = new LinkedList<>();

    protected Replacer replacer = new PlaceholderReplacer();
    private String name;
    private String id;

    public void setOnType(final String onType) {
        this.onType = onType;
    }

    public void setJavaDoc(final String javaDoc) {
        this.javaDoc = javaDoc;
    }

    public void addAnnotation(final String annotation) {
        annotations.add(annotation);
    }

    protected String createAnnotationPart(final String id, final String data) {
        addGeneratorAnnotation(id, data);
        String annotationPart = "";
        for (final String annotation : annotations) {
            annotationPart += TAB + annotation + NEWLINE;
        }
        return annotationPart;
    }

    protected String createAnnotationPartForSoftening(final String id, final String data, final String prefix) {
        return "@Generated(id = " + id + ", name = \""  + prefix + name  + "$counterplaceholder$" + "\", data = \"" + data + "\")";
    }

    private void addGeneratorAnnotation(final String id, final String data) {
        final String generatorAnnotation = "@Generated(id = " + id + ", name = \""  + name + "\", data = \"" + data + "\")";
        if (!annotations.contains(generatorAnnotation)) {
            annotations.add(generatorAnnotation);
        }
    }

    protected String createJavaDocPart() {
        if (javaDoc == null) {
            return "";
        } else {
            String javaDocPart = TAB + "/**";
            final String[] javaDocLines = javaDoc.split(NEWLINE);
            for (final String javaDocLine : javaDocLines) {
                javaDocPart += NEWLINE + TAB + " * " + javaDocLine;
            }
            javaDocPart += NEWLINE + TAB + " */" + NEWLINE;
            return javaDocPart;
        }
    }

    protected String getPropertiesAsString(final Map<String, String> properties) {
        final StringBuffer sb = new StringBuffer();
        boolean isFirst = true;
        for (final Entry<String, String> entry : properties.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(",");
            }
            sb.append(entry.getKey() + ":" + entry.getValue().replace("\"", "\\\""));
        }
        return sb.toString();
    }

    protected String getFieldList(final List<JavaField> fields) {
        final StringBuffer buffer = new StringBuffer();
        boolean isFirst = true;
        for (final JavaField field : fields) {
            if (isFirst) {
                isFirst = false;
            } else {
                buffer.append(",");
            }
            buffer.append(field.getType() + ":" + field.getName());
        }
        return buffer.toString();
    }


    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    protected String getMethodData(final JavaMethod method) {
        return method.getAccessType() + ":" + method.getType() + ":" + method.getName()
                + ";" + getMethodParameter(method.getParameters());
    }

    protected String getMethodParameter(final List<JavaParameter> parameters) {
        final StringBuffer sb = new StringBuffer();
        boolean isFirst = true;
        for (final JavaParameter parameter : parameters) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(",");
            }
            sb.append(parameter.getType() + ":" + parameter.getName());
        }

        return sb.toString();
    }

}
