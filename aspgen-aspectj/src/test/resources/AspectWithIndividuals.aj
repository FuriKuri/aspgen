package de.hbrs;


public privileged aspect FileAspect {
    private String OneKlasse.sField;
    
    after(String value, int number) throws IOException : execution(public void MyClass.doMethod3(String, int)) && args(value) && args(number) {
        System.out.println("Execute doMethod");
    }
    
    private final Double OneKlasse.method3(final String value, int integer) {
        return 1000.0;
    }
}
