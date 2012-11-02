package de.hbrs.aspgen.core.file;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.osgi.service.log.LogService;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.dir.DirectoryObserver;
import de.hbrs.aspgen.api.dir.GeneratedFileRelationshipService;
import de.hbrs.aspgen.api.dir.GeneratorProcess;
import de.hbrs.aspgen.api.dir.ProjectDirManager;
import de.hbrs.aspgen.api.file.AspectJProcess;
import de.hbrs.aspgen.api.file.JavaProcess;
import de.hbrs.aspgen.api.generator.GeneratorManager;

public class FileObserver implements DirectoryObserver {

    private final Map<String, GeneratorProcess> filetypeProcessMapping;
    private final LogService logService;
    private final GeneratedFileRelationshipService fileRelationshipService;
    private final GeneratorManager generatorManager;
    private final ProjectDirManager projectDirManager;

    @Inject
    public FileObserver(final LogService logService,
            @JavaProcess final GeneratorProcess javaProcess,
            @AspectJProcess final GeneratorProcess aspectJProcess,
            final GeneratedFileRelationshipService fileRelationshipService,
            final GeneratorManager generatorManager,
            final ProjectDirManager projectDirManager) {
        this.fileRelationshipService = fileRelationshipService;
        this.generatorManager = generatorManager;
        this.projectDirManager = projectDirManager;
        filetypeProcessMapping = new HashMap<>();
        filetypeProcessMapping.put("java", javaProcess);
        filetypeProcessMapping.put("aj", aspectJProcess);
        this.logService = logService;
    }

    @Override
    public void updateFileForChanges(final List<File> files) {
        logService.log(LogService.LOG_DEBUG, "Nofityed about updated files");
        final List<File> sortedFiles = getSortedFilesList(files);
        for (final File file : sortedFiles) {
            final String filetype = getFiletype(file);
            try {
                filetypeProcessMapping.get(filetype).startGeneratorProcessForUpdatedFile(file);
            } catch (final Exception e) {
                logService.log(LogService.LOG_ERROR, "Error while update genereted code for updated file " + file, e);
            }
        }
    }

    private String getFiletype(final File file) {
        final String[] fileNameSepartedByDots  = file.getName().split("\\.");
        final String filetype = fileNameSepartedByDots[fileNameSepartedByDots.length - 1];
        return filetype;
    }

    private List<File> getSortedFilesList(final List<File> files) {
        final List<File> saveCopy = new LinkedList<>(files);
        Collections.sort(saveCopy, new Comparator<File>() {
            @Override
            public int compare(final File o1, final File o2) {
                return (int) (o1.lastModified() - o2.lastModified());
            }
        });
        return saveCopy;
    }

    @Override
    public void updateFileForCreations(final List<File> files) {
        logService.log(LogService.LOG_DEBUG, "Nofityed about created files");
        final List<File> sortedFiles = getSortedFilesList(files);
        for (final File file : sortedFiles) {
            final String filetype = getFiletype(file);
            try {
                filetypeProcessMapping.get(filetype).startGeneratorProcessForCreatedFile(file);
            } catch (final Exception e) {
                logService.log(LogService.LOG_ERROR, "Error while update genereted code for created file " + file, e);
            }
        }
    }

    @Override
    public void updateFileForDeletions(final List<File> files) {
        logService.log(LogService.LOG_DEBUG, "Nofityed about deleted files");
        final List<File> sortedFiles = getSortedFilesList(files);
        for (final File file : sortedFiles) {
            final String filetype = getFiletype(file);
            try {
                filetypeProcessMapping.get(filetype).startGeneratorProcessForDeletedFile(file);
            } catch (final Exception e) {
                logService.log(LogService.LOG_ERROR, "Error while update genereted code for deleted file " + file, e);
            }
        }
    }

}
