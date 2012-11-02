package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

import de.hbrs.aspgen.generator.constructor.Constructor;
import de.hbrs.aspgen.generator.javabean.JavaBean;
import de.hbrs.aspgen.generator.logging.Logging;
import de.hbrs.aspgen.generator.notnull.NotNull;
import de.hbrs.aspgen.generator.tostring.ToString;

public privileged aspect Person_ToString {
    /**
     * ToString JavaDoc
     */
    @Generated(id = 1, name = "ToString", data = "int:age,String:name,int:count,String:hellos;")
    public String Person.changedString() {
        String result = "";
        result += "age = " + age + " ";
        result += "name = " + name + " ";
        result += "count = " + count + " ";
        result += "hellos = " + hellos + " ";
        return result;
    }
}