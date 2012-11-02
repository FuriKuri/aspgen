package de.hbrs.aspgen.aspectjparser;



import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.hbrs.aspgen.ajparser.AspectJ6Parser;
import de.hbrs.aspgen.api.ast.AspectJUnit;

public class AspectWithITDMethodsTest {
    private final AspectJ6Parser parser = new AspectJ6Parser();
    private AspectJUnit fileAspect;

    @Before
    public void initFileAspectResult() {
        fileAspect = parser.parse(new File("src/test/resources/AspectWithITDMethods.aj"));
    }

    @Test
    public void getJavaDoc() {
        assertEquals("", fileAspect.getItdMethods().get(0).getJavaDoc().getContent());
        assertEquals("/**\r\n     * Das ist ein Test String\r\n     */\r\n    ", fileAspect.getItdMethods().get(2).getJavaDoc().getContent());
    }

    @Test
    public void getAnnotation() {
        assertEquals("@Generated(12345)\r\n    ", fileAspect.getItdMethods().get(0).getAnnotations().getContent());
        assertEquals("@Generated(\"mappingId\")\r\n    ", fileAspect.getItdMethods().get(1).getAnnotations().getContent());
        assertEquals("@Generated(12345)\r\n    @Des\r\n    ", fileAspect.getItdMethods().get(2).getAnnotations().getContent());
        assertEquals("@Generated(\"mappingId\")\r\n    @MyAnno(value=\"123\", id=123)\r\n    ", fileAspect.getItdMethods().get(3).getAnnotations().getContent());
    }

    @Test
    public void getModifer() {
        assertEquals("public ", fileAspect.getItdMethods().get(0).getModifer().getContent());
        assertEquals("", fileAspect.getItdMethods().get(1).getModifer().getContent());
        assertEquals("private final ", fileAspect.getItdMethods().get(2).getModifer().getContent());
        assertEquals("protected static ", fileAspect.getItdMethods().get(3).getModifer().getContent());
    }

    @Test
    public void getType() {
        assertEquals("void ", fileAspect.getItdMethods().get(0).getType().getContent());
        assertEquals("String ", fileAspect.getItdMethods().get(1).getType().getContent());
        assertEquals("Double ", fileAspect.getItdMethods().get(2).getType().getContent());
        assertEquals("String ", fileAspect.getItdMethods().get(3).getType().getContent());
    }

    @Test
    public void getOnType() {
        assertEquals("OneKlasse.method", fileAspect.getItdMethods().get(0).getOnType().getContent());
        assertEquals("TwoKlasse.method2", fileAspect.getItdMethods().get(1).getOnType().getContent());
        assertEquals("OneKlasse.method3", fileAspect.getItdMethods().get(2).getOnType().getContent());
        assertEquals("OneKlasse.method4", fileAspect.getItdMethods().get(3).getOnType().getContent());
    }

    @Test
    public void getParameters() {
        assertEquals("() ", fileAspect.getItdMethods().get(0).getParameters().getContent());
        assertEquals("() ", fileAspect.getItdMethods().get(1).getParameters().getContent());
        assertEquals("(final String value, int integer) ", fileAspect.getItdMethods().get(2).getParameters().getContent());
        assertEquals("(double value) ", fileAspect.getItdMethods().get(3).getParameters().getContent());
    }

    @Test
    public void getBlock() {
        assertEquals("{\r\n        System.out.println(\"Output\");\r\n    }", fileAspect.getItdMethods().get(0).getBlock().getContent());
        assertEquals("{\r\n        return \"returnValue\";\r\n    }", fileAspect.getItdMethods().get(1).getBlock().getContent());
        assertEquals("{\r\n        return 1000.0;\r\n    }", fileAspect.getItdMethods().get(2).getBlock().getContent());
        assertEquals("{\r\n        return \"returnValue\";\r\n    }", fileAspect.getItdMethods().get(3).getBlock().getContent());
    }

    @Test
    public void getPosition() {
        assertEquals(63, fileAspect.getItdMethods().get(0).getStartPosition());
        assertEquals(164, fileAspect.getItdMethods().get(0).getEndPosition());

        assertEquals(176, fileAspect.getItdMethods().get(1).getStartPosition());
        assertEquals(271, fileAspect.getItdMethods().get(1).getEndPosition());
    }
}
