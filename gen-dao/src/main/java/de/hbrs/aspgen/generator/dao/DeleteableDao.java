package de.hbrs.aspgen.generator.dao;

public @interface DeleteableDao {
    Class<?> entity();

    int id() default 0;
    String modified() default "";
    String deleted() default "";
    String exclude() default "";
}
