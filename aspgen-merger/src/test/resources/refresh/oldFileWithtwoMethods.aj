package de.hbrs;


public privileged aspect Person_JavaBean {
    @Generated(id = 2, name = "Setter", data = "int:age;")
    public void Person.setAge(int age) {
        this.age = age;
    }

    @Generated(id = 2, name = "Getter", data = "int:age;")
    public int Person.getAge() {
        return null;
    }

    @Generated(id = 3, name = "Setter", data = "double:count;")
    public void Person.setCount(double count) {
        this.count = count;
    }

    @Generated(id = 3, name = "Getter", data = "double:count;")
    public double Person.getCount() {
        return null;
    }
}