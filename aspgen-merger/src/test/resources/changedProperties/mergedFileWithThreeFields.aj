package de.hbrs;

public privileged aspect Person_JavaBean {
    @Generated(id = 1, name = "Logger", data = ";count:1")
    private String Person.logger = "Logger.get(Person.class)";

    @Generated(id = 3, name = "AgeField", data = "public:String:setName;String:name,int:age;count:1")
    private String Person.age;

    @Generated(id = 2, name = "AgeField", data = "public:String:getName;String:name,double:age;count:2")
    private String Person.age2;
}