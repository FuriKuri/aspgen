package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

import de.hbrs.aspgen.generator.javabean.JavaBean;

public privileged aspect Person_JavaBean {
    @Generated(id = {newIdClass}, name = "Logger", data = ";")
    private String Person.logger = "Logger.get(Person.class)";

    @Generated(id = {newIdClass}, name = "Logger2", data = ";")
    private String Person.logger1 = "Logger.get(Person.class)";

    @Generated(id = {newid1}, name = "AgeField", data = "public:String:getName;String:name,int:age;")
    private String Person.age;

    @Generated(id = {newid2}, name = "AgeField", data = "public:String:setName;String:name,int:age;")
    private String Person.age;

    @Generated(id = {newid3}, name = "AgeField", data = "public:String:setAge;String:name,int:age;")
    private String Person.age;
}