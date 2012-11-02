package de.hbrs;

public privileged aspect Person_ToString {
    @Generated(id = {newid1}, name = "Logging", data = "public:void:method;Integer:a,Integer:b,String:c;")
    before(Integer a, Integer b, String c) : execution(public void Person.method(Integer, Integer, String)) && args(a, b, c) {
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
    
    @Generated(id = {newid1}, name = "Logging2", data = "public:void:method;Integer:a,Integer:b,String:c;")
    before(Integer a, Integer b, String c) : execution(public void Person.method(Integer, Integer, String)) && args(a, b, c) {
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
    
    @Generated(id = {newid2}, name = "Logging", data = "public:void:method2;Integer:a,Integer:b,String:c;")
    before(Integer a, Integer b, String c) : execution(public void Person.method(Integer, Integer, String)) && args(a, b, c) {
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
    
    @Generated(id = {newid2}, name = "Logging2", data = "public:void:method2;Integer:a,Integer:b,String:c;")
    before(Integer a, Integer b, String c) : execution(public void Person.method(Integer, Integer, String)) && args(a, b, c) {
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
}