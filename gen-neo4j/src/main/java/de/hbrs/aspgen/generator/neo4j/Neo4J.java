package de.hbrs.aspgen.generator.neo4j;

public @interface Neo4J {
    int id() default 0;
    String modified() default "";
    String deleted() default "";
    String exclude() default "";
}
