package de.hbrs;

public privileged aspect Person_Cons {
    @Generated(id = 1, name = "Cons", data = "int:age,String:name,int:count;")
    public Person.new(int age, String name, int count) {
        System.out.println("Init");
        System.out.println("age");
        System.out.println("name");
        System.out.println("count");
    }
}