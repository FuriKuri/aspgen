package de.hbrs;

public privileged aspect Person_NotNull {
    //@Generated(id = 2, name = "softeningCatch1", data = "public:void:setName;String:name;")
    declare soft : Exception : execution(public void  Person.setName(String));
    
    //@Generated(id = 2, name = "softeningCatch2", data = "public:void:setName;String:name;")
    declare soft : RuntimeException : execution(public void  Person.setName(String));
}