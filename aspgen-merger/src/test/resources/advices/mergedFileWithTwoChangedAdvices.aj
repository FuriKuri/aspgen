package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

public privileged aspect Person_JavaBean {
    @Generated(id = 2, name = "Logging", data = "private:int:calc;int:h,String:name;")
    before(int h, String name) : execution(public int Person.calc(int, String)) && args(h, name) {
        System.out.println(h);
        System.out.println(name);
    }

    @Generated(id = 3, name = "Logging", data = "private:void:doSomething2;String:name,String:amount,String:bla;")
    before(String name, String amount, String bla) : execution(public void Person.doSomething2(String, String, String)) && args(name, amount, bla) {
        System.out.println(name);
        System.out.println(amount);
        System.out.println(bla);
    }
}