package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

import de.hbrs.aspgen.generator.javabean.JavaBean;
import de.hbrs.aspgen.generator.notnull.NotNull;

public privileged aspect Person_JavaBean {
    @Generated(id = 2, name = "Getter", data = "int:age;")
    public int Person.getAge() {
        return "changed";
    }

    @Generated(id = 3, name = "Setter", data = "String:name;")
    public void Person.setName(String name) {
        this.name = "changed";
    }
}