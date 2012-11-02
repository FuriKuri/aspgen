package de.hbrs.aspgen.aspectjparser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.hbrs.aspgen.ajparser.AspectJ6Parser;
import de.hbrs.aspgen.api.ast.AspectJUnit;

public class AspectWithUsedTypesAndMethodCallsTest {
    private final AspectJ6Parser parser = new AspectJ6Parser();
    private AspectJUnit fileAspect;

    @Before
    public void initFileAspectResult() {
        fileAspect = parser.parse(new File("src/test/resources/AspectWithUsedTypes.aj"));
    }

    @Test
    public void getJavaDoc() {
        final Set<String> result = fileAspect.getUsedTypesAndMethods();;
        assertEquals(16, fileAspect.getUsedTypesAndMethods().size());
        assertTrue("Double", result.contains("Double"));
        assertTrue(result.contains("Check"));
        assertTrue(result.contains("Asd"));
        assertTrue(result.contains("Generated2"));
        assertTrue(result.contains("IOException"));
        assertTrue(result.contains("Generated"));
        assertTrue(result.contains("Eab2"));
        assertTrue(result.contains("assertEquals"));
        assertTrue(result.contains("println"));
        assertTrue(result.contains("String"));
        assertTrue(result.contains("ClassA"));
        assertTrue(result.contains("ClassB"));
        assertTrue(result.contains("BigDecimal"));
        assertTrue(result.contains("Eab"));
        assertTrue(result.contains("HashCodeUtil"));
        assertTrue(result.contains("System"));
    }

}
