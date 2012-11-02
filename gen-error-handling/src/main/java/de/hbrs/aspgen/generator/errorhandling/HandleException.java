package de.hbrs.aspgen.generator.errorhandling;


public @interface HandleException {
    Class<? extends Throwable>[] exception() default {Exception.class};
    int id() default 0;
    String modified() default "";
    String deleted() default "";
    String exclude() default "";
}
