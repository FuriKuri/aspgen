package de.hbrs.aspgen.generator.tostring;

public @interface ToString {
    int id() default 0;
    String modified() default "";
    String deleted() default "";
    String exclude() default "";
}
