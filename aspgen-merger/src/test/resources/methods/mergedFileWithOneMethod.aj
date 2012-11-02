package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

public privileged aspect Person_JavaBean {
    @Generated(id = 3, name = "Setter", data = "double:count;")
    public void Person.setCount(double count) {
        this.count = count;
    }

    @Generated(id = 3, name = "Getter", data = "double:count;")
    public double Person.getCount() {
        return count;
    }
}