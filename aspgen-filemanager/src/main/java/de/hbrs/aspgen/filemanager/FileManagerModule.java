package de.hbrs.aspgen.filemanager;

import org.osgi.service.log.LogService;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import de.hbrs.aspgen.api.dir.GeneratedFileRelationshipService;
import de.hbrs.aspgen.api.dir.OberservableDirectoryMonitor;
import de.hbrs.aspgen.api.dir.ObservableProjectWriter;
import de.hbrs.aspgen.api.dir.ProjectDirManager;
import de.hbrs.aspgen.api.file.ChangedCache;
import de.hbrs.aspgen.api.file.WrittenCache;
import de.hbrs.aspgen.filemanager.monitor.ProjectMonitor;
import de.hbrs.aspgen.filemanager.project.ProjectDirAccessCordinator;
import de.hbrs.aspgen.filemanager.scanner.JavaToAspectJRelationshipService;
import de.hbrs.aspgen.filemanager.util.FileCache;
import de.hbrs.aspgen.filemanager.util.LastChangedFileCache;
import de.hbrs.aspgen.filemanager.util.LastWrittenFileCache;

public class FileManagerModule extends AbstractModule {
    private static final int CACHE_SIZE = 20;
    private final LogService logService;

    public FileManagerModule(final LogService logService) {
        this.logService = logService;
    }

    @Override
    protected void configure() {
        bind(FileCache.class).annotatedWith(ChangedCache.class).toInstance(new LastChangedFileCache(CACHE_SIZE));
        bind(FileCache.class).annotatedWith(WrittenCache.class).toInstance(new LastWrittenFileCache(CACHE_SIZE));
        bind(LogService.class).toInstance(logService);
        bind(OberservableDirectoryMonitor.class).to(ProjectMonitor.class).in(Scopes.SINGLETON);
        bind(GeneratedFileRelationshipService.class).to(JavaToAspectJRelationshipService.class).in(Scopes.SINGLETON);
        bind(ProjectDirAccessCordinator.class).in(Scopes.SINGLETON);
        bind(ProjectDirManager.class).to(ProjectDirAccessCordinator.class);
        bind(ObservableProjectWriter.class).to(ProjectDirAccessCordinator.class);
    }

}
