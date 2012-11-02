package de.hbrs.aspgen.aspectjparser;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.hbrs.aspgen.ajparser.AspectJ6Parser;
import de.hbrs.aspgen.api.ast.AspectJUnit;

public class AspectWithAdviceAndDeclareTest {
    private final AspectJ6Parser parser = new AspectJ6Parser();
    private AspectJUnit fileAspect;

    @Before
    public void initFileAspectResult() {
        fileAspect = parser.parse(new File("src/test/resources/AspectWithAdviceAndDeclare.aj"));
    }

    @Test
    public void getAnnotation() {
        assertEquals("@Generated(id = {newid1}, name = \"softeningCatch1\", data = \"public:String:doSomething5;;\")", fileAspect.getDeclares().get(0).getAnnotations().getContent());
    }

    @Test
    public void getPosition() {
        assertEquals(561, fileAspect.getAdvices().get(0).getStartPosition());
        assertEquals(847, fileAspect.getAdvices().get(0).getEndPosition());
    }
}
