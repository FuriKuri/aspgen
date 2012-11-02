package de.hbrs;

import de.hbrs.Logging;

@Logging
public class Person {
    @Logging(id = 2, modified = "Log")
    public String method( final String a, final Integer b) {
        System.out.println("In Methods");
        return "Value" + counter++;
    }
    
    @Logging(deleted = "Log2")
    public String method2( final String a, final Integer b) {
        System.out.println("In Methods");
        return "Value" + counter++;
    }
}
