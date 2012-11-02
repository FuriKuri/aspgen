package de.hbrs.aspgen.generator.builder;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.hbrs.aspgen.api.ast.JavaAnnotation;
import de.hbrs.aspgen.api.ast.JavaField;
import de.hbrs.aspgen.api.ast.JavaParameter;

public abstract class AbstractBuilder {
    protected final List<AspectJBlock> blocks = new LinkedList<>();
    private final Set<String> imports = new HashSet<>();

    public String createContent(final String idGroup, final Map<String, String> properties, final List<String> deletedBlockNames) {
        final StringBuffer content = new StringBuffer();

        boolean isFirst = true;
        for (final AspectJBlock block : blocks) {
            if (deletedBlockNames.contains(block.getName())) {
                continue;
            }
            if (isFirst) {
                isFirst = false;
            } else {
                content.append("\n\n");
            }
            content.append(block.createBlockContent(idGroup, properties));
        }

        return content.toString();
    }

    public void addImport(final String javaImport) {
        imports.add("import " + javaImport + ";");
    }

    public void addStaticImport(final String javaImport) {
        imports.add("import static " + javaImport + ";");
    }

    public Set<String> getAllImports() {
        return imports;
    }

    protected List<JavaField> filterNotExcludedFieldsForName(final List<JavaField> fields, final String name, final String annotationName) {
        final List<JavaField> notExcludedFields = new LinkedList<>();
        for (final JavaField javaField : fields) {
            final JavaAnnotation annotation = getAnnotation(javaField.getAnnotations(), annotationName);
            if (annotation == null || annotation.getAttribute("exclude") == null) {
                notExcludedFields.add(javaField);
            } else {
                final String[] excludeNames = annotation.getStringAttribute("exclude").split(",\\s*");
                boolean isExcluded = false;
                for (final String excludeName : excludeNames) {
                    if (excludeName.equals(name)) {
                        isExcluded = true;
                    }
                }
                if (!isExcluded) {
                    notExcludedFields.add(javaField);
                }
            }
        }
        return notExcludedFields;
    }

    protected List<JavaParameter> filterNotExcludedParameterForName(final List<JavaParameter> parameters, final String name, final String annotationName) {
        final List<JavaParameter> notExcludedParameters = new LinkedList<>();
        for (final JavaParameter parameter : parameters) {
            final JavaAnnotation annotation = getAnnotation(parameter.getAnnotations(), annotationName);
            if (annotation == null || annotation.getAttribute("exclude") == null) {
                notExcludedParameters.add(parameter);
            } else {
                final String[] excludeNames = annotation.getStringAttribute("exclude").split(",\\s*");
                boolean isExcluded = false;
                for (final String excludeName : excludeNames) {
                    if (excludeName.equals(name)) {
                        isExcluded = true;
                    }
                }
                if (!isExcluded) {
                    notExcludedParameters.add(parameter);
                }
            }
        }
        return notExcludedParameters;
    }

    private JavaAnnotation getAnnotation(final List<JavaAnnotation> annotations, final String annotationName) {
        for (final JavaAnnotation javaAnnotation : annotations) {
            if (annotationName.equals(javaAnnotation.getName())) {
                return javaAnnotation;
            }
        }
        return null;
    }
}
