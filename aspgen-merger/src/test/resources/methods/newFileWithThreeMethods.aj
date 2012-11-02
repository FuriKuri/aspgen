package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

import de.hbrs.aspgen.generator.javabean.JavaBean;

public privileged aspect Person_JavaBean {
    @Generated(id = {newid1}, name = "Setter", data = "int:age;")
    public void Person.setAge(int age) {
        this.age = age;
    }

    @Generated(id = {newid1}, name = "Getter", data = "int:age;")
    public int Person.getAge() {
        return age;
    }

    @Generated(id = {newid2}, name = "Setter", data = "double:count;")
    public void Person.setCount(double count) {
        this.count = count;
    }

    @Generated(id = {newid2}, name = "Getter", data = "double:count;")
    public double Person.getCount() {
        return count;
    }
    
    @Generated(id = {newid3}, name = "Setter", data = "String:name;")
    public void Person.setName(String name) {
        this.name = name;
    }

    @Generated(id = {newid3}, name = "Getter", data = "String:name;")
    public String Person.getName() {
        return name;
    }
}