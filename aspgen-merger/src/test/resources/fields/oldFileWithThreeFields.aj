package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

import de.hbrs.aspgen.generator.javabean.JavaBean;

public privileged aspect Person_JavaBean {
    @Generated(id = 1, name = "Logger", data = ";")
    private String Person.logger = "Logger.get(Person.class)";

    @Generated(id = 2, name = "AgeField", data = "public:String:getName;String:name,int:age;")
    private String Person.age;

    @Generated(id = 3, name = "AgeField", data = "public:String:setName;String:name,int:age;")
    private String Person.age;
}