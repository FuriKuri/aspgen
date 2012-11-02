package de.hbrs.aspgen.filemanager.scanner;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.osgi.service.log.LogService;

import com.google.inject.Inject;

import de.hbrs.aspgen.api.dir.GeneratedFileRelationshipService;

public class JavaToAspectJRelationshipService implements GeneratedFileRelationshipService {

    private static final String NAME_SEPARATOR = "_";
    private final LogService logService;

    @Inject
    public JavaToAspectJRelationshipService(final LogService logService) {
        this.logService = logService;
    }

    @Override
    public List<File> getAssociatedFiles(final File javaFile, final List<String> suffixNames) {
        final String javaName = getAspectJName(javaFile);
        final File dir = javaFile.getParentFile();
        final List<File> aspectJFiles = new LinkedList<>();
        for (final File file : dir.listFiles()) {
            if (isAspectJFile(file) && belongsToJavaFile(file, javaName) && hasSuffixName(file, suffixNames)) {
                logService.log(LogService.LOG_DEBUG, "Found associted file " + file + " for java file " + javaFile);
                aspectJFiles.add(file);
            }
        }
        if (aspectJFiles.size() == 0) {
            logService.log(LogService.LOG_DEBUG, "No associted file found for " + javaFile);
        }
        return aspectJFiles;
    }

    private boolean isAspectJFile(final File file) {
        return file.isFile() && file.getName().endsWith(".aj");
    }

    private boolean belongsToJavaFile(final File file, final String javaName) {
        final String prefixPart = getAspectJPrefixPart(file);
        return prefixPart.equals(javaName);
    }

    private boolean hasSuffixName(final File file, final List<String> suffixNames) {
        final String aspectJName = getAspectJName(file);
        final String suffixPart = getAspectJSuffixPart(aspectJName);
        return suffixNames.contains(suffixPart);
    }

    @Override
    public File getRootFile(final File aspectJFile) {
        final File javaFile = new File(aspectJFile.getParent(),
                getAspectJPrefixPart(aspectJFile) + ".java");
        if (javaFile.exists()) {
            return javaFile;
        } else {
            logService.log(LogService.LOG_INFO, "Root java file " + javaFile.getName() + " does not exist");
            return null;
        }
    }

    private String getAspectJName(final File file) {
        return file.getName().split("\\.")[0];
    }

    private String getAspectJPrefixPart(final File file) {
        return file.getName().split(NAME_SEPARATOR)[0];
    }

    private String getAspectJSuffixPart(final String aspectJName) {
        return aspectJName.split(NAME_SEPARATOR)[1];
    }
}
