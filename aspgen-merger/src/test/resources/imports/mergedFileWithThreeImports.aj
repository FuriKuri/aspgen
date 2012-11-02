package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;
import static de.hbrs.aspgen.annotation.Assert.assertEquals;
import de.hbrs.aspgen.generator.javabean.*;

public privileged aspect Person_JavaBean {
    @Generated(id = 2, name = "Setter", data = "int:alter;")
    public void Person.setAlter(int alter) {
        assertEquals(1, 2);
        this.alter = alter;
    }

    @Generated(id = 2, name = "Getter", data = "int:alter;")
    public int Person.getAlter() {
        return alter;
    }

    @Generated(id = 3, name = "Setter", data = "int:count;")
    public void Person.setCount(int count) {
        this.count = count;
    }

    @Generated(id = 3, name = "Getter", data = "int:count;")
    public int Person.getCount() {
        return count;
    }
}