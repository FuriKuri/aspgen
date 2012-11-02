package de.hbrs.aspgen.generator.cache;

public @interface Cache {
    int id() default 0;
    String modified() default "";
    String deleted() default "";
    String exclude() default "";
}
