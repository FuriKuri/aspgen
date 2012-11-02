package de.hbrs.aspgen.generator.builder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.hbrs.aspgen.api.ast.JavaAnnotation;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.generator.AdviceForMethodGenerator;
import de.hbrs.aspgen.api.generator.ConstructorForClassGenerator;
import de.hbrs.aspgen.api.generator.DynamicPartsInBlocks;
import de.hbrs.aspgen.api.generator.Generator;
import de.hbrs.aspgen.api.generator.MethodForClassGenerator;


public class DynamicPartExtractor {
    private final JavaClass javaClass;
    private final Generator generator;

    public DynamicPartExtractor(final JavaClass javaClass, final Generator generator) {
        this.javaClass = javaClass;
        this.generator = generator;
    }

    public List<DynamicPartsInBlocks> getDynamicParts(final String id) {
        final List<DynamicPartsInBlocks> dynamicBlocks = new LinkedList<>();

        if (generator instanceof AdviceForMethodGenerator) {
//            if (annotationsContainsGeneratorAnnotation(javaClass.getAnnotations())) {
                for (final JavaMethod javaMethod : javaClass.getMethods()) {
                    if (annotationsContainsGeneratorAnnotation(javaMethod.getAnnotations())) {
                        final AdviceForMethodBuilder builder = new AdviceForMethodBuilder(javaClass, javaMethod, generator.getName());
                        ((AdviceForMethodGenerator) generator).extendJavaClass(builder, annotationsProperties(javaMethod.getAnnotations()));
                        final DynamicPartsInBlocks dynamicPartInAdviceForMethod = builder.getDynamicParts(id);
                        dynamicBlocks.add(dynamicPartInAdviceForMethod);

                    }
                }
        }

        return dynamicBlocks;
    }


    public List<DynamicPartsInBlocks> getDynamicParts() {
        final List<DynamicPartsInBlocks> dynamicBlocks = new LinkedList<>();
        if (generator instanceof MethodForClassGenerator) {
            if (annotationsContainsGeneratorAnnotation(javaClass.getAnnotations())) {
                final MethodForClassBuilder builder = new MethodForClassBuilder(javaClass, generator.getName());
                ((MethodForClassGenerator) generator).extendJavaClass(builder, annotationsProperties(javaClass.getAnnotations()));
                final String id = getId(javaClass);
                final DynamicPartsInBlocks dynamicPartInMethodForClass = builder.getDynamicParts(id);
                dynamicBlocks.add(dynamicPartInMethodForClass);
            }
        }
        if (generator instanceof ConstructorForClassGenerator) {
            if (annotationsContainsGeneratorAnnotation(javaClass.getAnnotations())) {
                final ConstructorForClassBuilder builder = new ConstructorForClassBuilder(javaClass, generator.getName());
                ((ConstructorForClassGenerator) generator).extendJavaClass(builder, annotationsProperties(javaClass.getAnnotations()));
                final String id = getId(javaClass);
                final DynamicPartsInBlocks dynamicPartInMethodForClass = builder.getDynamicParts(id);
                dynamicBlocks.add(dynamicPartInMethodForClass);
            }
        }

        return dynamicBlocks;
    }

    private String getId(final JavaClass javaClass) {
        if (getGeneratorAnnotation(javaClass.getAnnotations()) == null) {
            return null;
        } else {
            return getGeneratorAnnotation(javaClass.getAnnotations()).getAttribute("id");
        }
    }

    public JavaAnnotation getGeneratorAnnotation(final List<JavaAnnotation> annotations) {
        for (final JavaAnnotation javaAnnotation : annotations) {
            if (generator.getName().equals(javaAnnotation.getName())) {
                return javaAnnotation;
            }
        }
        return null;
    }

    public boolean annotationsContainsGeneratorAnnotation(final List<JavaAnnotation> annotations) {
        for (final JavaAnnotation javaAnnotation : annotations) {
            if (generator.getName().equals(javaAnnotation.getName())) {
                return true;
            }
        }
        return false;
    }

    private Map<String, String> annotationsProperties(final List<JavaAnnotation> annotations) {
        for (final JavaAnnotation javaAnnotation : annotations) {
            if (generator.getName().equals(javaAnnotation.getName())) {
                return removeGeneratorKeys(javaAnnotation.getAllAttribute());
            }
        }
        return null;
    }

    // TODO testen
    private Map<String, String> removeGeneratorKeys(final Map<String, String> orginal) {
        final Map<String, String> filterdMap = new HashMap<>();
        for (final Entry<String, String> entries : orginal.entrySet()) {
            if (!entries.getKey().equals("id") && !entries.getKey().equals("name")
                    && !entries.getKey().equals("data") && !entries.getKey().equals("exclude")) {
                filterdMap.put(entries.getKey(), entries.getValue());
            }
        }
        return filterdMap;
    }
}
