package de.hbrs;

public privileged aspect Person_Constructor {
    @Generated(id = 1, name = "Cons", data = "int:age,String:name;")
    public Person.new(int age, String name, String a) {
        System.out.println("Init");
        System.out.println("age");
        System.out.println("name");
    }
}