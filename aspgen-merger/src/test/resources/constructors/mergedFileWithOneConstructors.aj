package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

public privileged aspect Person_Constructor {
    @Generated(id = 1, name = "Cons2", data = "int:age,String:fullname,double:count;")
    public Person.new(int init) {
        System.out.println("Init");
        System.out.println("default");
    }
}