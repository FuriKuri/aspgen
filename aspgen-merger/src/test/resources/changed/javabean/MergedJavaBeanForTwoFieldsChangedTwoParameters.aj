package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

public privileged aspect Person_JavaBean {
    @Generated(id = 2, name = "Setter", data = "double:age;")
    public void Person.setAge(int newAge) {
        this.age = newAge;
    }

    @Generated(id = 3, name = "Setter", data = "String:name;")
    public void Person.setName(String firstname) {
        this.firstname = firstname;
    }

    @Generated(id = 2, name = "Getter", data = "double:age;")
    public double Person.getAge() {
        return age;
    }

    @Generated(id = 3, name = "Getter", data = "String:name;")
    public String Person.getName() {
        return name;
    }
}