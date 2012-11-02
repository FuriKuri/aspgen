package de.hbrs.aspgen.aspectjparser;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.hbrs.aspgen.ajparser.AspectJ6Parser;
import de.hbrs.aspgen.api.ast.AspectJUnit;

public class AspectWithAdvicesTest {
    private final AspectJ6Parser parser = new AspectJ6Parser();
    private AspectJUnit fileAspect;

    @Before
    public void initFileAspectResult() {
        fileAspect = parser.parse(new File("src/test/resources/AspectWithAdvices.aj"));
    }

    @Test
    public void getJavaDoc() {
        assertEquals("/**\r\n     * My JavaDoc\r\n     */\r\n    ", fileAspect.getAdvices().get(0).getJavaDoc().getContent());
        assertEquals("", fileAspect.getAdvices().get(2).getJavaDoc().getContent());
    }

    @Test
    public void getAnnotation() {
        assertEquals("@Generated(\"1234\")", fileAspect.getAdvices().get(0).getAnnotations().getContent());
        assertEquals("@Generated(\"mappingId\")", fileAspect.getAdvices().get(2).getAnnotations().getContent());
    }

    @Test
    public void getAdviceHead() {
        assertEquals("\r\n    after(String value, int number) throws IOException : ", fileAspect.getAdvices().get(0).getAdviceHead().getContent());
        assertEquals("\r\n    after() : ", fileAspect.getAdvices().get(1).getAdviceHead().getContent());
        assertEquals("\r\n    before(String value, int number) throws IOException : ", fileAspect.getAdvices().get(2).getAdviceHead().getContent());
        assertEquals("\r\n    void around(String value, int number) throws IOException : ", fileAspect.getAdvices().get(3).getAdviceHead().getContent());
        assertEquals("\r\n    String around(String value) : ", fileAspect.getAdvices().get(4).getAdviceHead().getContent());

    }

    @Test
    public void getPointcut() {
        assertEquals("execution(public void MyClass.doMethod3(String, int)) && args(value) && args(number) ", fileAspect.getAdvices().get(0).getPointcut().getContent());
        assertEquals("execution(public void MyClass.doMethod()) ", fileAspect.getAdvices().get(1).getPointcut().getContent());
        assertEquals("execution(public void MyClass.doMethod3(String, int)) && args(value) && args(number) ", fileAspect.getAdvices().get(2).getPointcut().getContent());
        assertEquals("execution(public void MyClass.doMethod3(String, int)) && args(value) && args(number) ", fileAspect.getAdvices().get(3).getPointcut().getContent());
        assertEquals("execution(public void MyClass.doMethod2(String)) && args(value) ", fileAspect.getAdvices().get(4).getPointcut().getContent());
    }

    @Test
    public void getBlock() {
        assertEquals("{\r\n        System.out.println(\"Execute doMethod\");\r\n    }", fileAspect.getAdvices().get(0).getBlock().getContent());
        assertEquals("{\r\n        System.out.println(\"Execute doMethod\");\r\n        return \"\";\r\n    }", fileAspect.getAdvices().get(4).getBlock().getContent());
    }

    @Test
    public void getPosition() {
        assertEquals(294, fileAspect.getAdvices().get(0).getStartPosition());
        assertEquals(550, fileAspect.getAdvices().get(0).getEndPosition());

        assertEquals(562, fileAspect.getAdvices().get(1).getStartPosition());
        assertEquals(696, fileAspect.getAdvices().get(1).getEndPosition());
    }
}
