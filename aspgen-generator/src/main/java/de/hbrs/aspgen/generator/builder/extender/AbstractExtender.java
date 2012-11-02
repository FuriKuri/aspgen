package de.hbrs.aspgen.generator.builder.extender;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.hbrs.aspgen.api.ast.JavaAnnotation;
import de.hbrs.aspgen.api.generator.Generator;

public abstract class AbstractExtender {
    protected boolean annotationsContainsGeneratorAnnotation(final List<JavaAnnotation> annotations, final Generator generator) {
        for (final JavaAnnotation javaAnnotation : annotations) {
            if (generator.getName().equals(javaAnnotation.getName())) {
                return true;
            }
        }
        return false;
    }

    protected List<String> getDeletedBlockNames(final List<JavaAnnotation> annotations, final Generator generator) {
        final List<String> deletedBlockNames = new LinkedList<>();
        if (getGeneratorAnnotation(annotations, generator) != null) {
            final String deletedAttribut = getGeneratorAnnotation(annotations, generator).getStringAttribute("deleted");
            if (deletedAttribut != null) {
                final String[] deletedNames = deletedAttribut.split(",");
                for (final String string : deletedNames) {
                    deletedBlockNames.add(string);
                }
            }
        }
        return deletedBlockNames;
    }

    protected JavaAnnotation getGeneratorAnnotation(final List<JavaAnnotation> annotations, final Generator generator) {
        for (final JavaAnnotation javaAnnotation : annotations) {
            if (generator.getName().equals(javaAnnotation.getName())) {
                return javaAnnotation;
            }
        }
        return null;
    }

    protected Map<String, String> annotationsProperties(final List<JavaAnnotation> annotations, final Generator generator) {
        for (final JavaAnnotation javaAnnotation : annotations) {
            if (generator.getName().equals(javaAnnotation.getName())) {
                return removeGeneratorKeys(javaAnnotation.getAllAttribute());
            }
        }
        return null;
    }

    protected Map<String, String> removeGeneratorKeys(final Map<String, String> orginal) {
        final Map<String, String> filterdMap = new HashMap<>();
        for (final Entry<String, String> entries : orginal.entrySet()) {
            if (!entries.getKey().equals("id") && !entries.getKey().equals("deleted")
                    && !entries.getKey().equals("modified") && !entries.getKey().equals("exclude")) {
                filterdMap.put(entries.getKey(), entries.getValue());
            }
        }
        return filterdMap;
    }
}
