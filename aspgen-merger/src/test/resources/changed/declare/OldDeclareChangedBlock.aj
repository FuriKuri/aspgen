package de.hbrs;

public privileged aspect Person_NotNull {
    //@Generated(id = 2, name = "softeningCatch1", data = "public:void:setNames;String:names;")
    declare soft : RuntimeException : execution(public void  Person.setNames(String));
}