package de.hbrs.aspgen.aspectjparser;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import de.hbrs.aspgen.ajparser.AspectJ6Parser;
import de.hbrs.aspgen.api.ast.AspectJUnit;

public class AspectToGetNextWritePositionTest {
    private final AspectJ6Parser parser = new AspectJ6Parser();
    private AspectJUnit fileAspect;

    @Test
    public void getGeneratedBlockPosition() {
        fileAspect = parser.parse(new File("src/test/resources/AspectWithAdvices.aj"));
        assertEquals(1346, fileAspect.getNextBlockPositionToWrite());
    }

    @Test
    public void getBlockPositionInEmptyAspect() {
        fileAspect = parser.parse(new File("src/test/resources/EmptsAspect.aj"));
        assertEquals(126, fileAspect.getNextBlockPositionToWrite());
    }

    @Test
    public void getBlockPositionInAspectWithNoGeneratedBlocks() {
        fileAspect = parser.parse(new File("src/test/resources/AspectWithNoGeneratedBlocks.aj"));
        assertEquals(686, fileAspect.getNextBlockPositionToWrite());
    }
}
