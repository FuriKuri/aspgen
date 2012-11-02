package de.hbrs.aspgen.aspectjparser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.hbrs.aspgen.ajparser.type.AspectJ6Advice;
import de.hbrs.aspgen.ajparser.type.AspectJ6Class;
import de.hbrs.aspgen.ajparser.type.AspectJ6ITDConstructor;
import de.hbrs.aspgen.ajparser.type.AspectJ6ITDField;
import de.hbrs.aspgen.ajparser.type.AspectJ6ITDMethod;

public class AspectJ6ClassTest {
    @Test
    public void getBlocks() {
        final AspectJ6Class ajClass = new AspectJ6Class();
        final AspectJ6Advice advice = new AspectJ6Advice();
        final AspectJ6Advice advice2 = new AspectJ6Advice();
        final AspectJ6ITDConstructor constructor = new AspectJ6ITDConstructor();
        final AspectJ6ITDConstructor constructor2 = new AspectJ6ITDConstructor();
        final AspectJ6ITDField field = new AspectJ6ITDField();
        final AspectJ6ITDField field2 = new AspectJ6ITDField();
        final AspectJ6ITDMethod method = new AspectJ6ITDMethod();
        final AspectJ6ITDMethod method2 = new AspectJ6ITDMethod();

        ajClass.addITDConstructors(constructor);
        ajClass.addITDConstructors(constructor2);

        ajClass.addITDField(field);
        ajClass.addITDField(field2);

        ajClass.addITDMethod(method);
        ajClass.addITDMethod(method2);

        ajClass.addAdvice(advice);
        ajClass.addAdvice(advice2);

        assertEquals(8, ajClass.getAllBlocks().size());
    }
}
