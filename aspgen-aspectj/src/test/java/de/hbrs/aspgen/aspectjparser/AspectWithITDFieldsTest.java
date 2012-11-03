package de.hbrs.aspgen.aspectjparser;



import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.hbrs.aspgen.ajparser.AspectJ6Parser;
import de.hbrs.aspgen.api.ast.AspectJUnit;

public class AspectWithITDFieldsTest {
    private final AspectJ6Parser parser = new AspectJ6Parser();
    private AspectJUnit fileAspect;

    @Before
    public void initFileAspectResult() {
        fileAspect = parser.parse(new File("src/test/resources/AspectWithITDFields.aj"));
    }

    @Test
    public void getJavaDoc() {
        assertEquals("/**\n     * My JavaDoc\n     */\n    ", fileAspect.getItdFields().get(0).getJavaDoc().getContent());
        assertEquals("", fileAspect.getItdFields().get(2).getJavaDoc().getContent());
    }

    @Test
    public void getAnnotation() {
        assertEquals("@Generated(1234)\n    ", fileAspect.getItdFields().get(0).getAnnotations().getContent());
        assertEquals("@Generated(\"mappingId\")\n    ", fileAspect.getItdFields().get(1).getAnnotations().getContent());
        assertEquals("@Generated(\"1234\")\n    @MyAnno\n    ", fileAspect.getItdFields().get(2).getAnnotations().getContent());
        assertEquals("@Generated(\"1234\")\n    ", fileAspect.getItdFields().get(3).getAnnotations().getContent());
    }

    @Test
    public void getModifer() {
        assertEquals("private ", fileAspect.getItdFields().get(0).getModifer().getContent());
        assertEquals("public final ", fileAspect.getItdFields().get(1).getModifer().getContent());
        assertEquals("protected static ", fileAspect.getItdFields().get(2).getModifer().getContent());
        assertEquals("", fileAspect.getItdFields().get(3).getModifer().getContent());
    }

    @Test
    public void getType() {
        assertEquals("String ", fileAspect.getItdFields().get(0).getType().getContent());
        assertEquals("int ", fileAspect.getItdFields().get(1).getType().getContent());
        assertEquals("String ", fileAspect.getItdFields().get(2).getType().getContent());
        assertEquals("Double ", fileAspect.getItdFields().get(3).getType().getContent());
    }

    @Test
    public void getOnType() {
        assertEquals("OneKlasse.sField", fileAspect.getItdFields().get(0).getOnType().getContent());
        assertEquals("TwoKlasse.intWithValue", fileAspect.getItdFields().get(1).getOnType().getContent());
        assertEquals("OneKlasse.staticField", fileAspect.getItdFields().get(2).getOnType().getContent());
        assertEquals("OneKlasse.number", fileAspect.getItdFields().get(3).getOnType().getContent());
    }


    @Test
    public void getBlock() {
        assertEquals(";", fileAspect.getItdFields().get(0).getBlock().getContent());
        assertEquals(" = 4;", fileAspect.getItdFields().get(1).getBlock().getContent());
        assertEquals(" = \"init\";", fileAspect.getItdFields().get(2).getBlock().getContent());
        assertEquals(" = 4.0;", fileAspect.getItdFields().get(3).getBlock().getContent());
    }

    @Test
    public void getPosition() {
        assertEquals(280, fileAspect.getItdFields().get(0).getStartPosition());
        assertEquals(367, fileAspect.getItdFields().get(0).getEndPosition());

        assertEquals(372, fileAspect.getItdFields().get(1).getStartPosition());
        assertEquals(444, fileAspect.getItdFields().get(1).getEndPosition());
    }
}
