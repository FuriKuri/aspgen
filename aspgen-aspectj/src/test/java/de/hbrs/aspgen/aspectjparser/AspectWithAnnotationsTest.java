package de.hbrs.aspgen.aspectjparser;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.hbrs.aspgen.ajparser.AspectJ6Parser;
import de.hbrs.aspgen.api.ast.AspectJUnit;

public class AspectWithAnnotationsTest {
    private final AspectJ6Parser parser = new AspectJ6Parser();
    private AspectJUnit fileAspect;

    @Before
    public void initFileAspectResult() {
        fileAspect = parser.parse(new File("src/test/resources/AspectWithAnnotations.aj"));
    }

    @Test
    public void getData() {
        assertEquals("String:name", fileAspect.getItdMethods().get(0).getAnnotationData());
        assertEquals("public:void:print;String:name", fileAspect.getAdvices().get(0).getAnnotationData());
        assertEquals("int:age", fileAspect.getItdConstructors().get(0).getAnnotationData());
        assertEquals("String:address", fileAspect.getItdFields().get(0).getAnnotationData());
        final String result = fileAspect.getItdFields().get(1).getAnnotationData();
        assertEquals("String:address;info:\"test\"", fileAspect.getItdFields().get(1).getAnnotationData());
    }

    @Test
    public void getName() {
        assertEquals("Setter", fileAspect.getItdMethods().get(0).getAnnotationName());
        assertEquals("Getter", fileAspect.getAdvices().get(0).getAnnotationName());
    }

    @Test
    public void getId() {
        assertEquals("1", fileAspect.getItdMethods().get(0).getAnnotationId());
        assertEquals("2", fileAspect.getAdvices().get(0).getAnnotationId());
    }
}
