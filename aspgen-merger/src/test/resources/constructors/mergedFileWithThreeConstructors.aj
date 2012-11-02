package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

public privileged aspect Person_Constructor {
    @Generated(id = 1, name = "Cons1", data = "int:age,String:name,int:count;")
    public Person.new(int age, String name, int count) {
        System.out.println("Init");
        System.out.println("age");
        System.out.println("name");
        System.out.println("count");
    }

    @Generated(id = 1, name = "Cons2", data = "int:age,String:name,int:count;")
    public Person.new(int init) {
        System.out.println("Init");
        System.out.println("default");
    }

    @Generated(id = 1, name = "Cons3", data = "int:age,String:name,int:count;")
    public Person.new(double init) {
        System.out.println("Init");
        System.out.println("default");
    }
}