package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

import de.hbrs.aspgen.generator.javabean.JavaBean;
import de.hbrs.aspgen.generator.notnull.NotNull;

public privileged aspect Person_JavaBean {
    @Generated(id = 2, name = "Setter", data = "double:age;")
    public void Person.setAge(double age) {
        this.age = age;
    }

    @Generated(id = 2, name = "Getter", data = "double:age;")
    public double Person.getAge() {
        return age;
    }

    @Generated(id = 3, name = "Setter", data = "String:name;")
    public void Person.setName(String name) {
        this.name = name;
    }

    @Generated(id = 3, name = "Getter", data = "String:name;")
    public String Person.getName() {
        return name;
    }
}