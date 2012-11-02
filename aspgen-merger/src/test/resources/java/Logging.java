package de.hbrs;

import de.hbrs.Logging;

@Logging
public class Person {
    public String method( final String a, final Integer b) {
        System.out.println("In Methods");
        return "Value" + counter++;
    }
    
    public String method2( final String a, final Integer b) {
        System.out.println("In Methods");
        return "Value" + counter++;
    }
}
