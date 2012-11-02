package de.hbrs.aspgen.generator.notnull;

public @interface NotNull {
    int id() default 0;
    String modified() default "";
    String deleted() default "";
    String exclude() default "";
}
