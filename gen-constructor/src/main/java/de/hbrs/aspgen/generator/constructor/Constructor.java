package de.hbrs.aspgen.generator.constructor;

public @interface Constructor {
    int id() default 0;
    String modified() default "";
    String deleted() default "";
    String exclude() default "";
}
