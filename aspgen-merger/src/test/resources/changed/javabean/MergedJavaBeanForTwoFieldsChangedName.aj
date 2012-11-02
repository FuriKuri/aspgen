package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

public privileged aspect Person_JavaBean {
    @Generated(id = 2, name = "Setter", data = "int:age;")
    public void Person.setAge(int age) {
        this.age = age;
    }

    @Generated(id = 2, name = "Getter", data = "int:age;")
    public int Person.getAge() {
        return age;
    }

    @Generated(id = 3, name = "Getter", data = "String:name;")
    public String Person.getFullname() {
        return name;
    }

    @Generated(id = 3, name = "Setter", data = "String:name;")
    public void Person.setName(String name) {
        this.name = name;
    }
}