package de.hbrs;

public privileged aspect Person_ToString {
    /**
     * My changed JavaDoc
     */
    @Generated(id = 2, name = "Logging", data = "public:void:method;Integer:a,Integer:b;")
    before(Integer a, Integer b) : execution(public void Person.method(Integer, Integer)) && args(a, b) {
        System.out.println(a);
        System.out.println(b);
    }
}