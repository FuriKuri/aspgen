package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

import de.hbrs.aspgen.generator.javabean.JavaBean;
import de.hbrs.aspgen.generator.notnull.NotNull;

public privileged aspect Person_JavaBean {
    @Generated(id = 2, name = "Setter", data = "int:age;")
    public void Person.setAge(int age) {
        this.age = age;
    }

    @Generated(id = 2, name = "Getter", data = "int:age;")
    public int Person.getAge() {
        return age;
    }

    @Generated(id = 3, name = "Setter", data = "Object:firstname;")
    public void Person.setFirstname(Object firstname) {
        this.firstname = firstname;
    }

    @Generated(id = 3, name = "Getter", data = "Object:firstname;")
    public Object Person.getFullname() {
        return firstname;
    }
}