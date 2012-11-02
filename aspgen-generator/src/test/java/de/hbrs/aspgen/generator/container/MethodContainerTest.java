package de.hbrs.aspgen.generator.container;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class MethodContainerTest {

    @Test
    public void findMethodname() {
        final MethodContainer container = new MethodContainer() {

            @Override
            public String createBlockContent(final String id, final Map<String, String> map) {
                return "";
            }
        };

        assertEquals(12, container.getStartIndexFromMethodName("public void getName()"));
        assertEquals(13, container.getStartIndexFromMethodName("private void get$fieldname$()"));
        assertEquals(14, container.getStartIndexFromMethodName("public String $fieldname$To$fieldtype$()"));
    }

    @Test
    public void setClassname() {
        final MethodContainer container = new MethodContainer() {

            @Override
            public String createBlockContent(final String id, final Map<String, String> map) {
                return "";
            }
        };
        container.setOnType("Person");
        container.setMethodDeclaration("public void getName()");
        assertEquals("public void Person.getName()", container.methodSignatur);
    }

    @Test(expected=RuntimeException.class)
    public void findInvalidMethodname() {
        final MethodContainer container = new MethodContainer() {

            @Override
            public String createBlockContent(final String id, final Map<String, String> map) {
                return "";
            }
        };

        container.getStartIndexFromMethodName("public void get.()Name");
    }
}
