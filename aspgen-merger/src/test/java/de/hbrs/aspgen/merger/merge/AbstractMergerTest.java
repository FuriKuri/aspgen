package de.hbrs.aspgen.merger.merge;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;

import de.hbrs.aspgen.ajparser.AspectJ6Parser;
import de.hbrs.aspgen.api.generator.GeneratorManager;
import de.hbrs.aspgen.jparser.factory.Java7Factory;
import de.hbrs.aspgen.merger.impl.AspectJAdviceMerger;
import de.hbrs.aspgen.merger.impl.AspectJConstructorMerger;
import de.hbrs.aspgen.merger.impl.AspectJDeclareMerger;
import de.hbrs.aspgen.merger.impl.AspectJFieldMerger;
import de.hbrs.aspgen.merger.impl.AspectJMerger;
import de.hbrs.aspgen.merger.impl.AspectJMethodMerger;
import de.hbrs.aspgen.merger.impl.GeneratorDataImpl;

public abstract class AbstractMergerTest {
    protected AspectJMerger aspectJMerger;
    protected GeneratorManager generatorManager;

    @Before
    public void init() {
        generatorManager = mock(GeneratorManager.class);
        final AspectJ6Parser ajParser = new AspectJ6Parser();
        final Java7Factory javaFactory = new Java7Factory();
        aspectJMerger = new AspectJMerger(ajParser,
                new AspectJMethodMerger(ajParser, generatorManager, javaFactory),
                new AspectJConstructorMerger(ajParser, generatorManager, javaFactory),
                new AspectJAdviceMerger(ajParser, generatorManager, javaFactory),
                new AspectJFieldMerger(ajParser, generatorManager, javaFactory),
                new AspectJDeclareMerger());
    }

    public void mergeFiles(final String oldFile, final String newFile, final String expected, final GeneratorDataImpl generatorData) throws IOException {
        final String oldString = FileUtils.readFileToString(new File(oldFile));
        final String newString = FileUtils.readFileToString(new File(newFile));
        final String expectedString = FileUtils.readFileToString(new File(expected));
        assertEquals(expectedString, aspectJMerger.mergeFiles(oldString, newString, generatorData));
    }
}
