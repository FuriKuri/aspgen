package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

import de.hbrs.aspgen.generator.javabean.JavaBean;

public privileged aspect Person_JavaBean {

    @Generated(id = {newid1}, name = "Setter", data = "double:count;")
    public void Person.setCount(double count) {
        this.count = count;
    }

    @Generated(id = {newid1}, name = "Getter", data = "double:count;")
    public double Person.getCount() {
        return count;
    }
}