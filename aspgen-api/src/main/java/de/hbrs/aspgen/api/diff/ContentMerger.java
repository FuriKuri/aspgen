package de.hbrs.aspgen.api.diff;

import de.hbrs.aspgen.api.file.GeneratedContent;
import de.hbrs.aspgen.api.merge.GeneratorData;


public interface ContentMerger {

    String mergeFiles(String oldContent, String newContent, GeneratorData datas);

    GeneratedContent setIds(GeneratedContent generatedContent);
}
