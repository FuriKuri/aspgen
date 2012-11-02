package de.hbrs;

public privileged aspect Person_Constructor {
    @Generated(id = 1, name = "Cons", data = "int:age,String:name;")
    public Person.new(int age, String name) {
        System.out.println("Init own");
    }
}