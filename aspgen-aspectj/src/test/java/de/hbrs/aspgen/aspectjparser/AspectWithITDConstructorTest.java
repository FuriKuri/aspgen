package de.hbrs.aspgen.aspectjparser;



import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.hbrs.aspgen.ajparser.AspectJ6Parser;
import de.hbrs.aspgen.api.ast.AspectJUnit;

public class AspectWithITDConstructorTest {
    private final AspectJ6Parser parser = new AspectJ6Parser();
    private AspectJUnit fileAspect;

    @Before
    public void initFileAspectResult() {
        fileAspect = parser.parse(new File("src/test/resources/AspectWithITDConstructors.aj"));
    }

    @Test
    public void getJavaDoc() {
        assertEquals("", fileAspect.getItdConstructors().get(0).getJavaDoc().getContent());
        assertEquals("/**\r\n     * Das ist ein Test String\r\n     */\r\n    ", fileAspect.getItdConstructors().get(2).getJavaDoc().getContent());
    }

    @Test
    public void getAnnotation() {
        assertEquals("@Generated(12345)\r\n    ", fileAspect.getItdConstructors().get(0).getAnnotations().getContent());
        assertEquals("@Generated(\"mappingId\")\r\n    ", fileAspect.getItdConstructors().get(1).getAnnotations().getContent());
        assertEquals("@Generated(12345)\r\n    @Des\r\n    ", fileAspect.getItdConstructors().get(2).getAnnotations().getContent());
    }

    @Test
    public void getModifer() {
        assertEquals("public ", fileAspect.getItdConstructors().get(0).getModifer().getContent());
        assertEquals("", fileAspect.getItdConstructors().get(1).getModifer().getContent());
        assertEquals("private ", fileAspect.getItdConstructors().get(2).getModifer().getContent());
    }
    @Test
    public void getOnType() {
        assertEquals("OneKlasse.new", fileAspect.getItdConstructors().get(0).getOnType().getContent());
        assertEquals("TwoKlasse.new", fileAspect.getItdConstructors().get(1).getOnType().getContent());
        assertEquals("OneKlasse.new", fileAspect.getItdConstructors().get(2).getOnType().getContent());
    }

    @Test
    public void getParameters() {
        assertEquals("() ", fileAspect.getItdConstructors().get(0).getParameters().getContent());
        assertEquals("() ", fileAspect.getItdConstructors().get(1).getParameters().getContent());
        assertEquals("(final String value, int integer) ", fileAspect.getItdConstructors().get(2).getParameters().getContent());
    }

    @Test
    public void getBlock() {
        assertEquals("{\r\n        System.out.println(\"Output\");\r\n    }", fileAspect.getItdConstructors().get(0).getBlock().getContent());
        assertEquals("{\r\n        System.out.println(\"Output\");\r\n    }", fileAspect.getItdConstructors().get(1).getBlock().getContent());
        assertEquals("{\r\n        System.out.println(\"Output\");\r\n    }", fileAspect.getItdConstructors().get(2).getBlock().getContent());
    }

    @Test
    public void getPosition() {
        assertEquals(63, fileAspect.getItdConstructors().get(0).getStartPosition());
        assertEquals(156, fileAspect.getItdConstructors().get(0).getEndPosition());

        assertEquals(168, fileAspect.getItdConstructors().get(1).getStartPosition());
        assertEquals(260, fileAspect.getItdConstructors().get(1).getEndPosition());
    }
}
