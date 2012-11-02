package de.hbrs.aspgen.core.generator;

import java.io.File;

import org.osgi.service.log.LogService;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.diff.AspectJDiff;
import de.hbrs.aspgen.api.diff.DiffCreator;
import de.hbrs.aspgen.api.diff.JavaUpdater;
import de.hbrs.aspgen.api.dir.GeneratedFileRelationshipService;
import de.hbrs.aspgen.api.dir.GeneratorProcess;
import de.hbrs.aspgen.api.dir.ProjectDirManager;

public class AspectJProcessGenerator implements GeneratorProcess {
    private final LogService logService;
    private final DiffCreator diffManager;
    private final GeneratedFileRelationshipService fileRelationshipService;
    private final ProjectDirManager projectDirManager;
    private final JavaUpdater javaUpdater;

    @Inject
    public AspectJProcessGenerator(final DiffCreator diffManager,
            final GeneratedFileRelationshipService fileRelationshipService,
            final ProjectDirManager projectDirManager,
            final JavaUpdater javaUpdater,
            final LogService logService) {
        this.diffManager = diffManager;
        this.fileRelationshipService = fileRelationshipService;
        this.projectDirManager = projectDirManager;
        this.javaUpdater = javaUpdater;
        this.logService = logService;
    }

    @Override
    public void startGeneratorProcessForUpdatedFile(final File aspectJFile) {
        logService.log(LogService.LOG_DEBUG, "Start aspectj process");
        final String aspectJContent = projectDirManager.readFileContent(aspectJFile);
        final File javaFile = fileRelationshipService.getRootFile(aspectJFile);
        if (aspectJContent != null && javaFile != null) {
            final String javaContent = projectDirManager.readFileContent(javaFile);
            final AspectJDiff aspectJDiff = diffManager.createDiff(aspectJContent, javaContent);
            final String newJavaCOntent = javaUpdater.updateJavaContent(javaContent, aspectJDiff);
            projectDirManager.updateFile(javaFile, newJavaCOntent);
        }
    }

    @Override
    public void startGeneratorProcessForDeletedFile(final File aspectJFile) {
        logService.log(LogService.LOG_DEBUG, "Start aspectj process for deleted file");
        final File javaFile = fileRelationshipService.getRootFile(aspectJFile);
        if (javaFile != null) {
            final String javaContent = projectDirManager.readFileContent(javaFile);
            final String aspectJName = aspectJFile.getName().split("_")[1].replace(".aj", "");
            final String newJavaContent = javaUpdater.removeAnnotations(javaContent, aspectJName);
            projectDirManager.updateFile(javaFile, newJavaContent);

        }
        // TODO delete annotations in java file
    }

    @Override
    public void startGeneratorProcessForCreatedFile(final File file) {

    }

}
