package de.hbrs.aspgen.generator.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hbrs.aspgen.api.ast.JavaAnnotation;
import de.hbrs.aspgen.api.ast.JavaBlock;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.generator.Generator;

public class IdCalculator {
    private int nextGroupId;
    private final Map<JavaBlock, Integer> idsForJavaBlocks = new HashMap<>();

    private final Generator generator;

    public IdCalculator(final Generator generator) {
        this.generator = generator;
        nextGroupId = 1;
    }

    private String getId(final JavaBlock javaParameter) {
        if (getGeneratorAnnotation(javaParameter.getAnnotations()) == null) {
            return null;
        } else {
            return getGeneratorAnnotation(javaParameter.getAnnotations()).getAttribute("id");
        }
    }

    public String getNextIdForBlock(final JavaBlock javaBlock) {
        String id;
        final String annotationId = getId(javaBlock);
        if (annotationId != null) {
            id = annotationId;
        } else {
            if (idsForJavaBlocks.containsKey(javaBlock)) {
                id = "{newid" + idsForJavaBlocks.get(javaBlock) + "}";
            } else {
                idsForJavaBlocks.put(javaBlock, nextGroupId);
                id = "{newid" + nextGroupId + "}";
                nextGroupId++;
            }
        }
        return id;
    }

    public String getNextIdForClass(final JavaClass javaClass) {
        String id;
        final String annotationId = getId(javaClass);
        if (annotationId != null) {
            id = annotationId;
        } else {
            id = "{newIdClass}";
        }
        return id;
    }

    public JavaAnnotation getGeneratorAnnotation(final List<JavaAnnotation> annotations) {
        for (final JavaAnnotation javaAnnotation : annotations) {
            if (generator.getName().equals(javaAnnotation.getName())) {
                return javaAnnotation;
            }
        }
        return null;
    }
}
