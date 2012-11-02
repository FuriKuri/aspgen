package de.hbrs;


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
    
    /**
     * ToString JavaDoc
     */
    @Generated(id = 1, name = "Trace", data = "String:name,int:count;")
    private String Person.trace() {
        return "new trace";
    }
}