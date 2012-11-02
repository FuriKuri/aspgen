package de.hbrs;

public privileged aspect Person_JavaBean {
    @Generated(id = 2, name = "Setter", data = "int:age;count:1")
    public void Person.setAge(int age) {
        this.age = age;
    }

    @Generated(id = 2, name = "Getter", data = "int:alter;count:2")
    public int Person.getAlter() {
        return alter;
    }

    @Generated(id = 3, name = "Setter", data = "int:count;count:2")
    public void Person.setCount(int count) {
        this.count = count;
    }

    @Generated(id = 3, name = "Getter", data = "int:count;count:2")
    public int Person.getCount() {
        return count;
    }
}