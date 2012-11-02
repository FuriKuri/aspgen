public privileged aspect Person_ToString {
    @Generated(id = 1, name = "ToString", data = "int:age,int:counter,String:name;")
    public String Person.toString() {
        String result = "";
        result += "age = " + age + " ";
        result += "counter = " + counter + " ";
        result += "name = " + name + " ";
        return "Changed";
    }
    
    @Generated(id = 1, name = "ToString2", data = "int:age,int:counter,String:name;")
    public String Person.toString() {
        String result = "";
        result += "age = " + age + " ";
        result += "counter = " + counter + " ";
        result += "name = " + name + " ";
        return result;
    }
    
    @Generated(id = 1, name = "ToString3", data = "int:age,int:counter,String:name;")
    public String Person.toString() {
        String result = "";
        result += "age = " + age + " ";
        result += "name = " + name + " ";
        return result;
    }
}