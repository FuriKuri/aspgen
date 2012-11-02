public privileged aspect Person_ToString {
    @Generated(id = {newIdClass}, name = "ToString", data = "int:age,int:counter,String:name;")
    public String Person.toString() {
        String result = "";
        result += "age = " + age + " ";
        result += "counter = " + counter + " ";
        result += "name = " + name + " ";
        return result;
    }
    
    @Generated(id = {newIdClass}, name = "ToString2", data = "int:age,int:counter,String:name;")
    public String Person.toString() {
        String result = "";
        result += "age = " + age + " ";
        result += "counter = " + counter + " ";
        result += "name = " + name + " ";
        return result;
    }
}