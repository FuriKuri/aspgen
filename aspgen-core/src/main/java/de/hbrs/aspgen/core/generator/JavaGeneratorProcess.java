package de.hbrs.aspgen.core.generator;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.osgi.service.log.LogService;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.ast.JavaAnnotation;
import de.hbrs.aspgen.api.ast.JavaClass;
import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.api.diff.AnnotationDataContainer;
import de.hbrs.aspgen.api.diff.ContentMerger;
import de.hbrs.aspgen.api.diff.ContentUpdater;
import de.hbrs.aspgen.api.dir.GeneratedFileRelationshipService;
import de.hbrs.aspgen.api.dir.GeneratorProcess;
import de.hbrs.aspgen.api.dir.ProjectDirManager;
import de.hbrs.aspgen.api.exception.FileNotExistException;
import de.hbrs.aspgen.api.file.GeneratedContent;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.api.merge.GeneratorData;
import de.hbrs.aspgen.api.merge.GeneratorDataFactory;
import de.hbrs.aspgen.api.notification.PluginNotifierService;
import de.hbrs.aspgen.api.parser.JavaParser;
import de.hbrs.aspgen.core.file.DeletedFileStorage;
import de.hbrs.aspgen.core.file.StorageEntry;
import de.hbrs.aspgen.core.file.StorageEntry.FileContentPair;

public class JavaGeneratorProcess implements GeneratorProcess {
    private final GeneratedFileRelationshipService fileRelationshipService;
    private final GeneratorManager generatorManager;
    private final JavaParser javaParser;
    private final LogService logService;
    private final ProjectDirManager projectDirManager;
    private final PluginNotifierService notifierService;
    private final ContentMerger merger;
    private final DataExtractor<List<AnnotationData>> dataExtractor;

    private final DeletedFileStorage fileStorage = new DeletedFileStorage(3);
    private final ContentUpdater contentUpdater;
    private final GeneratorDataFactory generatorDataFactory;

    @Inject
    public JavaGeneratorProcess(final GeneratorManager generatorManager,
            final JavaParser javaParser,
            final DataExtractor<List<AnnotationData>> dataExtractor,
            final GeneratedFileRelationshipService fileRelationshipService,
            final ProjectDirManager projectDirManager,
            final PluginNotifierService notifierService,
            final ContentMerger merger, final ContentUpdater contentUpdater,
            final GeneratorDataFactory generatorDataFactory,
            final LogService logService) {
        this.generatorManager = generatorManager;
        this.javaParser = javaParser;
        this.dataExtractor = dataExtractor;
        this.fileRelationshipService = fileRelationshipService;
        this.notifierService = notifierService;
        this.merger = merger;
        this.contentUpdater = contentUpdater;
        this.generatorDataFactory = generatorDataFactory;
        this.logService = logService;
        this.projectDirManager = projectDirManager;
    }


    @Override
    public void startGeneratorProcessForUpdatedFile(final File javaFile) {
        try {
            final JavaClass javaClass = javaParser.parse(javaFile);
            final List<GeneratedContent> generatedAspectJContents = generatorManager.gererateFiles(javaClass);
            updateFiles(javaFile, generatedAspectJContents, dataExtractor.extractDatasFromClass(javaClass));
        } catch (final FileNotExistException e) {
            logService.log(LogService.LOG_DEBUG, "File not exist anymore: " + javaFile, e);
            return;
        }
    }
    private void updateFiles(final File javaFile, final List<GeneratedContent> generatedAspectJContents, final List<AnnotationData> annotationDatas) {
        final String dir = javaFile.getParent().replace("\\", "/");
        for (final GeneratedContent generatedContent : generatedAspectJContents) {
            if (projectDirManager.fileExisit(dir + "/" + generatedContent.getFilename())) {
                final File aspectJFile = new File(dir + "/" + generatedContent.getFilename());
                final String aspectJFileContent = projectDirManager.readFileContent(aspectJFile);
                final String newGeneratedContent = generatedContent.getContent();
                final String generatorName = generatedContent.getGeneratorName();
                final String oldClassName = generatedContent.getClassName();

                final GeneratorData data = generatorDataFactory.createGeneratorData(generatorName, oldClassName);
                data.addAll(annotationDatas);
                final String content = merger.mergeFiles(aspectJFileContent, newGeneratedContent, data);
                projectDirManager.updateFile(aspectJFile, content);
            } else {
                final GeneratedContent contentWithIds = merger.setIds(generatedContent);
                projectDirManager.writeFile(dir, contentWithIds);
            }
        }
        final List<File> existingAspectJFiles = fileRelationshipService.getAssociatedFiles(javaFile, generatorManager.getAllGeneratorIds());
        for (final File file : existingAspectJFiles) {
            if (!wasGenerated(file, generatedAspectJContents)) {
                final String newFileContent = contentUpdater.deleteGeneratedBlocks(projectDirManager.readFileContent(file));
                projectDirManager.updateFile(file, newFileContent);

            }
        }
        notifierService.updateFolder(dir);
    }


    private boolean wasGenerated(final File file,
            final List<GeneratedContent> generatedAspectJContents) {
        for (final GeneratedContent generatedContent : generatedAspectJContents) {
            if (generatedContent.getFilename().equals(file.getName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void startGeneratorProcessForDeletedFile(final File javaFile) {
        final List<File> aspectJFiles = fileRelationshipService.getAssociatedFiles(javaFile, generatorManager.getAllGeneratorIds());
        if (aspectJFiles.size() > 0) {
            final StorageEntry entry = new StorageEntry(javaFile);
            for (final File aspectJFile : aspectJFiles) {
                entry.addGeneratedFile(aspectJFile, projectDirManager.readFileContent(aspectJFile));
                projectDirManager.deleteFile(aspectJFile);
            }
            fileStorage.add(entry);
        }
    }


    @Override
    public void startGeneratorProcessForCreatedFile(final File file) {
        final JavaClass javaClass = javaParser.parse(file);
        if (fileStorage.containsFileName(file)) {
            final File dir = file.getParentFile();
            final List<FileContentPair> contentPairs = fileStorage.getFileContentPairsForFileName(file);
            fileStorage.deleteEntry(file);
            for (final FileContentPair fileContentPair : contentPairs) {
                final File aspectjFile = new File(dir, fileContentPair.getFile().getName());
                final String updatedContent = contentUpdater.updatePackageName(fileContentPair.getContent(), javaClass.getPackageName());
                projectDirManager.updateFile(aspectjFile, updatedContent);
            }
        } else if (fileStorage.size() > 0) {
            final List<JavaAnnotation> annotations = javaClass.getAllUsedAnnotations();
            final File orginalFile = fileStorage.getFileForNeededAnnotations(mapAnnotationsToString(annotations));
            if (orginalFile != null) {
                final File dir = file.getParentFile();
                final List<FileContentPair> contentPairs = fileStorage.getFileContentPairsForFileName(orginalFile);
                fileStorage.deleteEntry(orginalFile);
                for (final FileContentPair fileContentPair : contentPairs) {
                    final File oldAspectJFile = new File(dir, fileContentPair.getFile().getName());
                    final String updatedContent = contentUpdater.updateClassName(fileContentPair.getContent(), javaClass.getClassName());
                    final File newAspectJFile = new File(dir, javaClass.getClassName() + "_" + oldAspectJFile.getName().split("_")[1]);
                    final List<AnnotationData> annotationDatas = dataExtractor.extractDatasFromClass(javaClass);
                    final AnnotationDataContainer dataContainer = new AnnotationDataContainer();
                    dataContainer.addAll(annotationDatas);
                    final String removedBlocks = contentUpdater.removeNotModifiedBlocks(updatedContent, dataContainer);
//                    final String updatedClassNames = contentUpdater.updateClassNamesInBlocks(oldJavaName, updatedContent, dataContainer);
//                    final String updatedBlocksWithClassNames = updateBlocksForNewClassName(removedBlocks, annotationDatas, oldJavaName, javaClass);
                    final List<GeneratedContent> generatedAspectJContents = generatorManager.gererateFiles(javaClass);

                    final String aspectJName = oldAspectJFile.getName().split("_")[1].replace(".aj", "");
                    boolean found = false;
                    for (final GeneratedContent generatedContent : generatedAspectJContents) {
                        if (generatedContent.getSimpleGeneratorName().equals(aspectJName)) {
                            final String oldJavaName = orginalFile.getName().replace(".java", "");
                            final GeneratorData data = generatorDataFactory.createGeneratorData(generatedContent.getGeneratorName(), oldJavaName);
                            data.addAll(annotationDatas);
                            final String content = merger.mergeFiles(removedBlocks, generatedContent.getContent(), data);
                            projectDirManager.updateFile(newAspectJFile, content);
                            found = true;
                        }
                    }
                    if (!found) {
                        projectDirManager.updateFile(newAspectJFile, removedBlocks);
                    }

                }
            }
        }
        startGeneratorProcessForUpdatedFile(file);
    }


    // TODO in javaannnotation klasse selber rein tun
    private List<String> mapAnnotationsToString(final List<JavaAnnotation> annotations) {
        final List<String> nameAnnotation = new LinkedList<>();
        for (final JavaAnnotation javaAnnotation : annotations) {
            nameAnnotation.add(javaAnnotation.getName().replace("@", ""));
        }
        return nameAnnotation;
    }
}
