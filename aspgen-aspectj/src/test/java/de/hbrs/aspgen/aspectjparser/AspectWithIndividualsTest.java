package de.hbrs.aspgen.aspectjparser;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.hbrs.aspgen.ajparser.AspectJ6Parser;
import de.hbrs.aspgen.api.ast.AspectJUnit;

public class AspectWithIndividualsTest {
    private final AspectJ6Parser parser = new AspectJ6Parser();
    private AspectJUnit fileAspect;

    @Before
    public void initFileAspectResult() {
        fileAspect = parser.parse(new File("src/test/resources/AspectWithIndividuals.aj"));
    }

    @Test
    public void getClassname() {
        assertEquals("FileAspect", fileAspect.getClassname());
    }

    @Test
    public void dontAddIndividualBlocks() {
        assertEquals(0, fileAspect.getAdvices().size());
        assertEquals(0, fileAspect.getItdFields().size());
        assertEquals(0, fileAspect.getItdMethods().size());
    }
}
