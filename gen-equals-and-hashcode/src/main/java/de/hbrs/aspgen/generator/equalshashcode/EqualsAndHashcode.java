package de.hbrs.aspgen.generator.equalshashcode;

public @interface EqualsAndHashcode {
    int id() default 0;
    String modified() default "";
    String deleted() default "";
    String exclude() default "";
}
