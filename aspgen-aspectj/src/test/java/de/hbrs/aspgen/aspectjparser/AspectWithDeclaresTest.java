package de.hbrs.aspgen.aspectjparser;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.hbrs.aspgen.ajparser.AspectJ6Parser;
import de.hbrs.aspgen.api.ast.AspectJUnit;

public class AspectWithDeclaresTest {
    private final AspectJ6Parser parser = new AspectJ6Parser();
    private AspectJUnit fileAspect;

    @Before
    public void initFileAspectResult() {
        fileAspect = parser.parse(new File("src/test/resources/AspectWithDeclares.aj"));
    }

    @Test
    public void getAnnotation() {
        assertEquals("@Generated(\"1234\")", fileAspect.getDeclares().get(0).getAnnotations().getContent());
    }

    @Test
    public void getBlock() {
        assertEquals("declare soft : Exception : execution(public String Person.doSomething());", fileAspect.getDeclares().get(0).getBlock().getContent());
    }

    @Test
    public void getPosition() {
        assertEquals(61, fileAspect.getDeclares().get(0).getStartPosition());
        assertEquals(159, fileAspect.getDeclares().get(0).getEndPosition());
    }
}
