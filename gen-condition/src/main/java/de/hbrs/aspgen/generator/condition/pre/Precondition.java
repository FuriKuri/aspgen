package de.hbrs.aspgen.generator.condition.pre;

public @interface Precondition {
    int id() default 0;
    String modified() default "";
    String deleted() default "";
    String exclude() default "";
}
