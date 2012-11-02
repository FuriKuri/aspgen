package de.hbrs;

public privileged aspect Person_Constructor {
    @Generated(id = 1, name = "Cons", data = "int:age,String:name;")
    private Person.new(int age, String name) {
        System.out.println("Init");
        System.out.println("age");
        System.out.println("name");
    }
}