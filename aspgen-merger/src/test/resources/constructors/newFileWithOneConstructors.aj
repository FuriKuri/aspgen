package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

import de.hbrs.aspgen.generator.constructor.Constructor;
import de.hbrs.aspgen.generator.javabean.JavaBean;
import de.hbrs.aspgen.generator.logging.Logging;
import de.hbrs.aspgen.generator.tostring.ToString;

public privileged aspect Person_Constructor {
    @Generated(id = {newIdClass}, name = "Cons2", data = "int:age,String:fullname,double:count;")
    public Person.new(int init) {
        System.out.println("Init");
        System.out.println("default");
    }
}