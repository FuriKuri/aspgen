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
    @Generated(id = 1, name = "ToString", data = "String:name,int:count;")
    public String Person.toString() {
        String result = "";
        result += "name = " + name + " ";
        result += "count = " + count + " ";
        return result;
    }
}