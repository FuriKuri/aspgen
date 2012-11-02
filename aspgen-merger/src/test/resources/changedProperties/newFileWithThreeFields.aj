package de.hbrs;

public privileged aspect Person_JavaBean {
    @Generated(id = 1, name = "Logger", data = ";count:2")
    private String Person.logger = "Logger.get(Person.class)";

    @Generated(id = {newid1}, name = "AgeField", data = "public:String:getName;String:name,double:age;count:2")
    private String Person.age2;

    @Generated(id = 3, name = "AgeField", data = "public:String:setName;String:name,double:age;count:2")
    private String Person.age3;
}