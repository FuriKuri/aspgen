package de.hbrs;


public privileged aspect Person_ToString {
    /**
     * ToString JavaDoc
     */
    @Generated(id = 1, name = "Trace", data = "String:name,int:count;")
    public String Person.toString() {
        return "Trace New";
    }
}