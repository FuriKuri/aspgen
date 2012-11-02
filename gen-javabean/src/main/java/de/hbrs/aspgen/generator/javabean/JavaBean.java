package de.hbrs.aspgen.generator.javabean;

public @interface JavaBean {
    int id() default 0;
    String modified() default "";
    String deleted() default "";
    String exclude() default "";
}
