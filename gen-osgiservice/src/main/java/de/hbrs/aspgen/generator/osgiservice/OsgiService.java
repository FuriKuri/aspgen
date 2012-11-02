package de.hbrs.aspgen.generator.osgiservice;

public @interface OsgiService {
    int id() default 0;
    String modified() default "";
    String deleted() default "";
    String exclude() default "";
}
