package de.hbrs;

import de.hbrs.JavaBean;

@JavaBean
public class Person {
    @JavaBean(id = 2, modified = "Getter, Setter")
    private String asd;
    @JavaBean(id = 3, modified = "Setter")
    private int age;
    int counter = 1;
    private String name2;
}
