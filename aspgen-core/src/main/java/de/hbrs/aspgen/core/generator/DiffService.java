package de.hbrs.aspgen.core.generator;

import java.util.List;

import de.hbrs.aspgen.api.file.GeneratedContent;
import de.hbrs.aspgen.api.file.FilesDiffContainer;

public interface DiffService {

    FilesDiffContainer calculateDiff(
            List<GeneratedContent> generatedAspectJContent,
            List<GeneratedContent> existingAspectJContent);

}
