package de.hbrs.aspgen.generator.container;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.jparser.type.Java6Method;

public class AdviceForInterfaceTest {
    @Test
    public void createAfterAdviceForInterface() {
        final AfterAdvicePerMethodContainer container = new AfterAdvicePerMethodContainer();
        container.setOnType("Car");
        container.isInterface();
        container.setName("Dummy");
        container.setMethod(createSimpleVoidMethod());
        container.addLine("System.out.println(\"Hello World\");");
        final String expectedResult = "    @Generated(id = 1, name = \"Dummy\", data = \"public:void:simpleMethod;;\")\n    after() : execution(public void Car+.simpleMethod()) {\n"
                + "        System.out.println(\"Hello World\");\n    }";
        assertEquals(expectedResult, container.createBlockContent("1", new HashMap<String, String>()));
    }

    private JavaMethod createSimpleVoidMethod() {
        final Java6Method java6Method = new Java6Method();
        java6Method.setName("simpleMethod");
        java6Method.setType("void");
        java6Method.setAccessType("public");
        return java6Method;
    }
}
