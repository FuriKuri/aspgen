package de.hbrs;


public privileged aspect Person_ToString {
    /**
     * ToString JavaDoc
     */
    @Generated(id = 1, name = "Trace", data = "int:age,String:name,int:count,String:hellos;")
    public String Person.toString() {
        return "Trace";
    }
}