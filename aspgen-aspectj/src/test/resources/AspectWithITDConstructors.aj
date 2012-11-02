package de.hbrs;

public privileged aspect FileAspect {
    @Generated(12345)
    public OneKlasse.new() {
        System.out.println("Output");
    }
    
    @Generated("mappingId")
    TwoKlasse.new() {
        System.out.println("Output");
    }
    
    /**
     * Das ist ein Test String
     */
    @Generated(12345)
    @Des
    private OneKlasse.new(final String value, int integer) {
        System.out.println("Output");
    }
}
