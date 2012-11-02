package de.hbrs;


public privileged aspect Person_Constructor {
    @Generated(id = 1, name = "Cons1", data = "int:age,String:name,int:count;count:1")
    public Person.new(int age, String name, int count) {
        System.out.println("Init");
        System.out.println("age");
        System.out.println("name");
        System.out.println("count");
    }

    @Generated(id = 1, name = "Cons2", data = "int:age,String:fullname,double:count;count:2")
    public Person.new(int init) {
        System.out.println("Init");
        System.out.println("default");
    }
}