package de.hbrs;

public privileged aspect Person_NotNull {
    //@Generated(id = 2, name = "softeningCatch1", data = "public:void:setNames;String:name;")
    declare soft : Exception : execution(public void  Person.setName(String));
}