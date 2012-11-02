package de.hbrs;

public privileged aspect Person_NotNull {
    @Generated(id = {newid1}, name = "NotNull", data = "String:name;public:void:method;String:name,String:amount;")
    before(String name) : execution(public void Person.method(String, String)) && args(name, *) {
        if (name == null) {
            throw new RuntimeException("name is null");
        }
    }
    
    @Generated(id = {newid1}, name = "NotNull2", data = "String:name;public:void:method;String:name,String:amount;")
    before(String name) : execution(public void Person.method(String, String)) && args(name, *) {
        if (name == null) {
            throw new RuntimeException("name is null");
        }
    }
}