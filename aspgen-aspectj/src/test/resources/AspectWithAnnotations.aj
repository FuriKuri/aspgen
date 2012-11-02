package de.hbrs;

public privileged aspect FileAspect {
    @Generated(id = 1, name = "Setter", data = "String:name")
    public void OneKlasse.method() {
        System.out.println("Output");
    }
    
    /**
     * My JavaDoc
     */
    @Generated(id = 2, name = "Getter", data = "public:void:print;String:name")
    after(String value, int number) throws IOException : execution(public void MyClass.doMethod3(String, int)) && args(value) && args(number) {
        System.out.println("Execute doMethod");
    }
    
    @Generated(id = 1, name = "Setter", data = "int:age")
    public OneKlasse.new() {
        System.out.println("Output");
    }
    
    @Generated(id = 1, name = "Setter", data = "String:address")
    public final int TwoKlasse.intWithValue = 4;
    
    @Generated(id = 1, name = "Setter", data = "String:address;info:\"test\"")
    public final int TwoKlasse.intWithValue2 = 4;
}
