package de.hbrs.aspgen.annotation;

public abstract @interface GeneratorAnnotation {

    int id() default 0;
    String modified() default "";
    String deleted() default "";
    String exclude() default "";

}
