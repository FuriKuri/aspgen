package de.hbrs.aspgen.generator.time;

public @interface LogTime {
    int id() default 0;
    String modified() default "";
    String deleted() default "";
    String exclude() default "";
}
