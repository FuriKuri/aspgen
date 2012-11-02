package de.hbrs.aspgen.merger.merge;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import de.hbrs.aspgen.api.diff.AnnotationData;
import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;


public class AspectJMergerForDeletedBlocks extends AbstractMergerTest {

    @Test
    public void mergeFileWithNoChangedFieldsAndTwoNewField() throws IOException {
        final String oldFile = FileUtils.readFileToString(new File("src/test/resources/deleted/OldToString.aj"));
        final String newFile = FileUtils.readFileToString(new File("src/test/resources/deleted/NewToString.aj"));
        final String expected = FileUtils.readFileToString(new File("src/test/resources/deleted/MergedToString.aj"));
        assertEquals(expected, aspectJMerger.mergeFiles(oldFile, newFile, getGeneratorDatasForToString()));
    }

    public GeneratorDataImpl getGeneratorDatasForToString() {
        final AnnotationData data = new AnnotationData("1");
        data.addDeleted("ToString");
        final GeneratorDataImpl dataList = new GeneratorDataImpl("Person", "de.hbrs.aspgen.generator.tostring.ToString");
        dataList.addGeneratorData(data);
        return dataList;
    }
}
