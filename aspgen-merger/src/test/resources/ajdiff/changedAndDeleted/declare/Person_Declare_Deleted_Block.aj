package de.hbrs;

public privileged aspect Person_NotNull {
    //@Generated(id = 2, name = "softeningCatch1", data = "public:void:setName;String:name;")
    declare soft : MyException : execution(public void  Person.setName(String));
}