package de.hbrs;

import de.hbrs.JavaBean;

@JavaBean
public class Person {
    @JavaBean(id = 2, modified = "Getter, Setter", deleted = "Getter2")
    private String asd;
    @JavaBean(id = 3, modified = "Setter")
    private int age;
    @JavaBean(deleted = "Getter2")
    int counter = 1;
    private String name2;
}
