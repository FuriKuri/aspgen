package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

public privileged aspect Person_ToString {
    /**
     * My JavaDoc
     */
    @Generated(id = 1, name = "ToString", data = "String:name,int:count;")
    public String Person.toString() {
        String result = "";
        result += "name = " + name + " ";
        result += "count = " + count + " ";
        return result;
    }
}