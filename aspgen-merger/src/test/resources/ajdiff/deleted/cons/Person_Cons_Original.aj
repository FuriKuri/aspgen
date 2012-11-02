package de.hbrs;

public privileged aspect Person_Cons {
    @Generated(id = {newIdClass}, name = "Cons", data = "int:age,String:name,int:count;")
    public Person.new(int age, String name, int count) {
        System.out.println("Init");
        System.out.println("age");
        System.out.println("name");
        System.out.println("count");
    }
    
    @Generated(id = {newIdClass}, name = "Cons2", data = "int:age,String:name,int:count;")
    public Person.new(int age, String name, int count) {
        System.out.println("Init");
        System.out.println("age");
        System.out.println("name");
        System.out.println("count");
    }
}