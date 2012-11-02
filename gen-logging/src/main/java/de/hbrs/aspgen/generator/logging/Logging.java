package de.hbrs.aspgen.generator.logging;

public @interface Logging {
    int id() default 0;
    String modified() default "";
    String deleted() default "";
    String exclude() default "";
}
