package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

public privileged aspect Person_ToString {
    /**
     * ToString JavaDoc
     */
    @Generated(id = 1, name = "ToString", data = "String:name,int:count;")
    public String Person.toString() {
        return "Return Value";
    }

    /**
     * ToString JavaDoc
     */
    @Generated(id = 1, name = "Trace", data = "String:name,int:count;")
    private String Person.trace() {
        return "new trace";
    }
}