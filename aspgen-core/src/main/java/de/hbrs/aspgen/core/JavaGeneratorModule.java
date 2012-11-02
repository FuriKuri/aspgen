package de.hbrs.aspgen.core;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;

import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.api.diff.ContentMerger;
import de.hbrs.aspgen.api.diff.ContentUpdater;
import de.hbrs.aspgen.api.diff.DiffCreator;
import de.hbrs.aspgen.api.diff.JavaUpdater;
import de.hbrs.aspgen.api.dir.DirectoryObserver;
import de.hbrs.aspgen.api.dir.GeneratedFileRelationshipService;
import de.hbrs.aspgen.api.dir.GeneratorProcess;
import de.hbrs.aspgen.api.dir.OberservableDirectoryMonitor;
import de.hbrs.aspgen.api.dir.ProjectDirManager;
import de.hbrs.aspgen.api.file.AspectJProcess;
import de.hbrs.aspgen.api.file.JavaProcess;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.merge.GeneratorDataFactory;
import de.hbrs.aspgen.api.notification.PluginNotifierService;
import de.hbrs.aspgen.api.parser.AspectJParser;
import de.hbrs.aspgen.api.parser.JavaParser;
import de.hbrs.aspgen.core.file.FileObserver;
import de.hbrs.aspgen.core.generator.AnnotationDataExtractor;
import de.hbrs.aspgen.core.generator.AspectJProcessGenerator;
import de.hbrs.aspgen.core.generator.DataExtractor;
import de.hbrs.aspgen.core.generator.JavaGeneratorProcess;

public class JavaGeneratorModule extends AbstractModule {

    private final BundleContext context;

    public JavaGeneratorModule(final BundleContext context) {
        this.context = context;
    }

    @Override
    protected void configure() {
        bindOsgiServices();


        bind(GeneratorProcess.class).annotatedWith(JavaProcess.class).to(JavaGeneratorProcess.class).in(Scopes.SINGLETON);
        bind(GeneratorProcess.class).annotatedWith(AspectJProcess.class).to(AspectJProcessGenerator.class).in(Scopes.SINGLETON);
        bind(DirectoryObserver.class).to(FileObserver.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<DataExtractor<List<AnnotationData>>>() { }).to(AnnotationDataExtractor.class).in(Scopes.SINGLETON);

    }

    private void bindOsgiServices() {
        final LogService logService = (LogService) context.getService(
                context.getServiceReference(LogService.class.getName()));
        bind(LogService.class).toInstance(logService);
        final JavaParser parser = (JavaParser) context.getService(
                context.getServiceReference(JavaParser.class.getName()));
        bind(JavaParser.class).toInstance(parser);
        final GeneratorManager generatorManager = (GeneratorManager) context.getService(
                context.getServiceReference(GeneratorManager.class.getName()));
        bind(GeneratorManager.class).toInstance(generatorManager);
        final GeneratedFileRelationshipService relationshipService = (GeneratedFileRelationshipService) context.getService(
                context.getServiceReference(GeneratedFileRelationshipService.class.getName()));
        bind(GeneratedFileRelationshipService.class).toInstance(relationshipService);
        final ProjectDirManager projectDirManager = (ProjectDirManager) context.getService(
                context.getServiceReference(ProjectDirManager.class.getName()));
        bind(ProjectDirManager.class).toInstance(projectDirManager);
        final PluginNotifierService notifierService = (PluginNotifierService) context.getService(
                context.getServiceReference(PluginNotifierService.class.getName()));
        bind(PluginNotifierService.class).toInstance(notifierService);
        final AspectJParser ajParser = (AspectJParser) context.getService(
                context.getServiceReference(AspectJParser.class.getName()));
        bind(AspectJParser.class).toInstance(ajParser);
        final OberservableDirectoryMonitor directory = (OberservableDirectoryMonitor) context.getService(
                context.getServiceReference(OberservableDirectoryMonitor.class.getName()));
        bind(OberservableDirectoryMonitor.class).toInstance(directory);
        final JavaFactory javaFactory = (JavaFactory) context.getService(
                context.getServiceReference(JavaFactory.class.getName()));
        bind(JavaFactory.class).toInstance(javaFactory);
        final ContentMerger contentMerger = (ContentMerger) context.getService(
                context.getServiceReference(ContentMerger.class.getName()));
        bind(ContentMerger.class).toInstance(contentMerger);
        final DiffCreator diffCreator = (DiffCreator) context.getService(
                context.getServiceReference(DiffCreator.class.getName()));
        bind(DiffCreator.class).toInstance(diffCreator);
        final JavaUpdater javaUpdater = (JavaUpdater) context.getService(
                context.getServiceReference(JavaUpdater.class.getName()));
        bind(JavaUpdater.class).toInstance(javaUpdater);
        final ContentUpdater contentUpdater = (ContentUpdater) context.getService(
                context.getServiceReference(ContentUpdater.class.getName()));
        bind(ContentUpdater.class).toInstance(contentUpdater);

        final GeneratorDataFactory generatorDataFactory = (GeneratorDataFactory) context.getService(
                context.getServiceReference(GeneratorDataFactory.class.getName()));
        bind(GeneratorDataFactory.class).toInstance(generatorDataFactory);

    }

}
