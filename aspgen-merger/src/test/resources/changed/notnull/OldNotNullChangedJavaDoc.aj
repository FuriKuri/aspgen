package de.hbrs;

public privileged aspect Person_NotNull {
    /**
     * Changed
     */
    @Generated(id = 2, name = "NotNull", data = "String:age;public:void:method;String:age,String:amount,int:a;")
    before(String age) : execution(public void Person.method(String, String, int)) && args(age, *, *) {
        if (age == null) {
            throw new RuntimeException("age is null");
        }
    }
}