package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

import de.hbrs.aspgen.generator.javabean.JavaBean;

public privileged aspect Person_JavaBean {
    @Generated(id = {newid1}, name = "Logging", data = "public:void:doSomething2;String:name,String:amount,String:bla;")
    before(String name, String amount, String bla) : execution(public void Person.doSomething2(String, String, String)) && args(name, amount, bla) {
        System.out.println(name);
        System.out.println(amount);
        System.out.println(bla);
    }

    @Generated(id = {newid2}, name = "Logging", data = "public:int:calc;int:h,String:name;")
    before(int h, String name) : execution(public int Person.calc(int, String)) && args(h, name) {
        System.out.println(h);
        System.out.println(name);
    }

    @Generated(id = {newid3}, name = "Logging", data = "public:double:calc;String:name;")
    before(String name) : execution(public double Person.calc(String)) && args(name) {
        System.out.println(name);
    }
}