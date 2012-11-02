package de.hbrs;

public privileged aspect FileAspect {
    @Generated(12345)
    public void OneKlasse.method() {
        System.out.println("Output");
    }
    
    @Generated("mappingId")
    String TwoKlasse.method2() {
        return "returnValue";
    }
    
    /**
     * Das ist ein Test String
     */
    @Generated(12345)
    @Des
    private final Double OneKlasse.method3(final String value, int integer) {
        return 1000.0;
    }
    
    @Generated("mappingId")
    @MyAnno(value="123", id=123)
    protected static String OneKlasse.method4(double value) {
        return "returnValue";
    }
}
