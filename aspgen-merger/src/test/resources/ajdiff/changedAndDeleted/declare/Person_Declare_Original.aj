package de.hbrs;

public privileged aspect Person_NotNull {

    //@Generated(id = {newid1}, name = "softeningCatch1", data = "public:void:setName;String:name;")
    declare soft : Exception : execution(public void  Person.setName(String));
    
    //@Generated(id = {newid1}, name = "softeningCatch2", data = "public:void:setName;String:name;")
    declare soft : RuntimeException : execution(public void  Person.setName(String));
}