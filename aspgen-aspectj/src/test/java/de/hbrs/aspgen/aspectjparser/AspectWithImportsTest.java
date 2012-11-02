package de.hbrs.aspgen.aspectjparser;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.hbrs.aspgen.ajparser.AspectJ6Parser;
import de.hbrs.aspgen.api.ast.AspectJUnit;

public class AspectWithImportsTest {
    private final AspectJ6Parser parser = new AspectJ6Parser();
    private AspectJUnit fileAspect;

    @Before
    public void initFileAspectResult() {
        fileAspect = parser.parse(new File("src/test/resources/AspectWithImports.aj"));
    }

    @Test
    public void getAspectJImports() {
        assertEquals(7, fileAspect.getImports().size());
        assertEquals("import static org.junit.Assert.assertEquals;", fileAspect.getImports().get(0).getContent());
        assertEquals("import static org.junit.Assert.assertNotNull;", fileAspect.getImports().get(1).getContent());
        assertEquals("import static org.junit.Assert.*;", fileAspect.getImports().get(2).getContent());
        assertEquals("import java.io.File;", fileAspect.getImports().get(3).getContent());
        assertEquals("import java.io.BufferedReader;", fileAspect.getImports().get(4).getContent());
        assertEquals("import java.util.*;", fileAspect.getImports().get(5).getContent());
        assertEquals("import java.awt.*;", fileAspect.getImports().get(6).getContent());

    }
}
