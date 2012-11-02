package de.hbrs;

public privileged aspect Person_ToString {
    /**
     * My changed JavaDoc
     */
    @Generated(id = 2, name = "Logging", data = "public:void:method;Integer:a,Integer:b,String:msg;")
    before(Integer a, Integer b, String msg) : execution(public void Person.method(Integer, Integer, String)) && args(a, b, msg) {
        System.out.println(a);
        System.out.println(msg);
    }
}