public privileged aspect Person_ToString {
    @Generated(id = 1, name = "ToString", data = "int:age,int:counter,String:name;")
    public String Person.toString() {
        String result = "";
        result += "age = " + age + " ";
        result += "name = " + name + " ";
        return result;
    }
}