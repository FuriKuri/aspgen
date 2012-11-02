package de.hbrs;

public privileged aspect Person_NotNull {
    /**
     * JavaDoc
     */
    @Generated(id = 2, name = "NotNull", data = "String:name;public:void:method;String:name,String:amount;")
    before(String name) : execution(public void Person.method(String, String)) && args(name, *) {
        if (age == null) {
            throw new MyException("age is null");
        }
    }
}