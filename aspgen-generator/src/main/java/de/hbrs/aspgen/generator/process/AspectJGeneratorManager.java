package de.hbrs.aspgen.generator.process;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.osgi.service.log.LogService;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.JavaAnnotation;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.file.GeneratedContent;
import de.hbrs.aspgen.api.generator.DynamicPartsInBlocks;
import de.hbrs.aspgen.api.generator.Generator;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.registry.GeneratorRegistry;
import de.hbrs.aspgen.generator.builder.DynamicPartExtractor;
import de.hbrs.aspgen.generator.builder.JavaClassExtender;

public class AspectJGeneratorManager implements GeneratorManager {

    private final GeneratorRegistry registry;
    private final LogService logService;

    @Inject
    public AspectJGeneratorManager(final GeneratorRegistry registry, final LogService logService) {
        this.registry = registry;
        this.logService = logService;
    }

    @Override
    public List<GeneratedContent> gererateFiles(final JavaClass javaClass) {
        javaClass.replaceWithFullQualifiedAnnotations(registry.getRegistredGeneratorNames());
        final Set<String> annotationsInJavaClass = getRegisteredAnnotationsInJavaClass(javaClass);
        logService.log(LogService.LOG_DEBUG, "Found Generator Annotations: " + annotationsInJavaClass);
        final List<GeneratedContent> virtualFiles = new LinkedList<>();
        for (final String generatorAnnotation : annotationsInJavaClass) {
            final Generator generator = registry.getGenerator(generatorAnnotation);
            final JavaClassExtender builder = new JavaClassExtender(javaClass, generator);
            logService.log(LogService.LOG_DEBUG, "Start generator: " + builder);
            virtualFiles.add(new GeneratedContent(builder.getAspectJName(), builder.createAspectJFileContent(), generatorAnnotation));
        }
        return virtualFiles;
    }

    public Set<String> getRegisteredAnnotationsInJavaClass(final JavaClass javaClass) {
        final Set<String> annotationsInRegistry = registry.getRegistredGeneratorNames();
        final List<JavaAnnotation> allUsedAnnotations = javaClass.getAllUsedAnnotations();
        final Set<String> generatorAnnotationsInJavaClass = new HashSet<>();
        for (final String generatorAnnotation : annotationsInRegistry) {
            for (final JavaAnnotation annotationInClass : allUsedAnnotations) {
                if (annotationInClass.getName().equals(generatorAnnotation)) {
                    generatorAnnotationsInJavaClass.add(generatorAnnotation);
                }
            }
        }
        return generatorAnnotationsInJavaClass;
    }

    @Override
    public List<String> getAllGeneratorIds() {
        final List<String> generatorIds = new LinkedList<>();
        final Set<String> generatorNames = registry.getRegistredGeneratorNames();
        for (final String generatorName : generatorNames) {
            generatorIds.add(extractGeneratorId(generatorName));
        }
        return generatorIds;
    }

    // TODO doppelt refactor
    private String extractGeneratorId(final String generatorName) {
        final String[] splitedGeneratorPath = generatorName.split("\\.");
        return splitedGeneratorPath[splitedGeneratorPath.length - 1];
    }

    // TODO testen
    @Override
    public String generateContentForGenerator(final JavaClass javaClass,
            final String generatorAnnotation) {
        javaClass.replaceWithFullQualifiedAnnotations(registry.getRegistredGeneratorNames());

        final Generator generator = registry.getGenerator(generatorAnnotation);
        final JavaClassExtender builder = new JavaClassExtender(javaClass, generator);
        logService.log(LogService.LOG_DEBUG, "Start generator for merging: " + builder);
        return builder.createAspectJFileContent();
    }

    @Override
    public List<DynamicPartsInBlocks> getDynamicParts(final JavaClass javaClass,
            final String generatorAnnotation) {
//        javaClass.replaceWithFullQualifiedAnnotations(registry.getRegistredGeneratorNames());

        final Generator generator = registry.getGenerator(generatorAnnotation);
        final DynamicPartExtractor extractor = new DynamicPartExtractor(javaClass, generator);
        logService.log(LogService.LOG_DEBUG, "Start extracating dynamic parts from generator: " + extractor);

        return extractor.getDynamicParts();
    }

    @Override
    public List<DynamicPartsInBlocks> getDynamicParts(final JavaClass javaClass,
            final String generatorAnnotation, final String annotationId) {
//        javaClass.replaceWithFullQualifiedAnnotations(registry.getRegistredGeneratorNames());

        final Generator generator = registry.getGenerator(generatorAnnotation);
        final DynamicPartExtractor extractor = new DynamicPartExtractor(javaClass, generator);
        logService.log(LogService.LOG_DEBUG, "Start extracating dynamic parts from generator: " + extractor);

        return extractor.getDynamicParts(annotationId);
    }

    @Override
    public String getFullGeneratorName(final String name) {
        for (final String generatorName : registry.getRegistredGeneratorNames()) {
            if (extractGeneratorId(generatorName).equals(name)) {
                return generatorName;
            }
        }
        return null;
    }
}
