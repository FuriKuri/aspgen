package de.hbrs.aspgen.merger;

import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import de.hbrs.aspgen.api.ast.JavaFactory;
import de.hbrs.aspgen.api.diff.ContentMerger;
import de.hbrs.aspgen.api.diff.ContentUpdater;
import de.hbrs.aspgen.api.diff.DiffCreator;
import de.hbrs.aspgen.api.diff.JavaUpdater;
import de.hbrs.aspgen.api.dir.GeneratedFileRelationshipService;
import de.hbrs.aspgen.api.dir.OberservableDirectoryMonitor;
import de.hbrs.aspgen.api.dir.ProjectDirManager;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.merge.AdviceMerger;
import de.hbrs.aspgen.api.merge.ConstructorMerger;
import de.hbrs.aspgen.api.merge.DeclareMerger;
import de.hbrs.aspgen.api.merge.FieldMerger;
import de.hbrs.aspgen.api.merge.GeneratorDataFactory;
import de.hbrs.aspgen.api.merge.MethodMerger;
import de.hbrs.aspgen.api.notification.PluginNotifierService;
import de.hbrs.aspgen.api.parser.AspectJParser;
import de.hbrs.aspgen.api.parser.JavaParser;
import de.hbrs.aspgen.merger.anno.AdviceDiff;
import de.hbrs.aspgen.merger.anno.ConstructorDiff;
import de.hbrs.aspgen.merger.anno.DeclareDiff;
import de.hbrs.aspgen.merger.anno.FieldDiff;
import de.hbrs.aspgen.merger.anno.MethodDiff;
import de.hbrs.aspgen.merger.diff.AdviceDiffManager;
import de.hbrs.aspgen.merger.diff.AspectJDiffCreator;
import de.hbrs.aspgen.merger.diff.ConstructorDiffManager;
import de.hbrs.aspgen.merger.diff.DeclareDiffManager;
import de.hbrs.aspgen.merger.diff.DiffManager;
import de.hbrs.aspgen.merger.diff.FieldDiffManager;
import de.hbrs.aspgen.merger.diff.MethodDiffManager;
import de.hbrs.aspgen.merger.factory.GeneratorDataFactoryImpl;
import de.hbrs.aspgen.merger.impl.AspectJAdviceMerger;
import de.hbrs.aspgen.merger.impl.AspectJConstructorMerger;
import de.hbrs.aspgen.merger.impl.AspectJDeclareMerger;
import de.hbrs.aspgen.merger.impl.AspectJFieldMerger;
import de.hbrs.aspgen.merger.impl.AspectJMerger;
import de.hbrs.aspgen.merger.impl.AspectJMethodMerger;
import de.hbrs.aspgen.merger.impl.AspectJUpdater;
import de.hbrs.aspgen.merger.impl.JavaAnnotationUpdater;
import de.hbrs.aspgen.merger.impl.Merger;

public class MergerModule extends AbstractModule {
    private final BundleContext context;

    public MergerModule(final BundleContext context) {
        this.context = context;
    }

    @Override
    protected void configure() {
        bindOsgiServices();

        bind(ContentMerger.class).to(AspectJMerger.class).in(Scopes.SINGLETON);
        bind(DiffCreator.class).to(AspectJDiffCreator.class).in(Scopes.SINGLETON);
        bind(JavaUpdater.class).to(JavaAnnotationUpdater.class).in(Scopes.SINGLETON);
        bind(ContentUpdater.class).to(AspectJUpdater.class).in(Scopes.SINGLETON);

        bind(GeneratorDataFactory.class).to(GeneratorDataFactoryImpl.class).in(Scopes.SINGLETON);

        bind(Merger.class).annotatedWith(FieldMerger.class).to(AspectJFieldMerger.class).in(Scopes.SINGLETON);
        bind(Merger.class).annotatedWith(MethodMerger.class).to(AspectJMethodMerger.class).in(Scopes.SINGLETON);
        bind(Merger.class).annotatedWith(DeclareMerger.class).to(AspectJDeclareMerger.class).in(Scopes.SINGLETON);
        bind(Merger.class).annotatedWith(ConstructorMerger.class).to(AspectJConstructorMerger.class).in(Scopes.SINGLETON);
        bind(Merger.class).annotatedWith(AdviceMerger.class).to(AspectJAdviceMerger.class).in(Scopes.SINGLETON);

        bind(DiffManager.class).annotatedWith(FieldDiff.class).to(FieldDiffManager.class).in(Scopes.SINGLETON);
        bind(DiffManager.class).annotatedWith(MethodDiff.class).to(MethodDiffManager.class).in(Scopes.SINGLETON);
        bind(DiffManager.class).annotatedWith(DeclareDiff.class).to(DeclareDiffManager.class).in(Scopes.SINGLETON);
        bind(DiffManager.class).annotatedWith(ConstructorDiff.class).to(ConstructorDiffManager.class).in(Scopes.SINGLETON);
        bind(DiffManager.class).annotatedWith(AdviceDiff.class).to(AdviceDiffManager.class).in(Scopes.SINGLETON);
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

    }

}
