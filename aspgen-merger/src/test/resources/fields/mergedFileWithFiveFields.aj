package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

public privileged aspect Person_JavaBean {
    @Generated(id = 1, name = "Logger", data = ";")
    private String Person.logger = "Logger.get(Person.class)";

    @Generated(id = 2, name = "AgeField", data = "public:String:getName;String:name,int:age;")
    private String Person.age;

    @Generated(id = 3, name = "AgeField", data = "public:String:setName;String:name,int:age;")
    private String Person.age;

    @Generated(id = 1, name = "Logger2", data = ";")
    private String Person.logger1 = "Logger.get(Person.class)";

    @Generated(id = 4, name = "AgeField", data = "public:String:setAge;String:name,int:age;")
    private String Person.age;
}