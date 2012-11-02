package de.hbrs;

import de.hbrs.Logging;

public class Person {
    @NotNull
    public String method(final String a, final Integer b) {
        System.out.println("In Methods");
        return "Value" + counter++;
    }

    public String method2(@NotNull final String a, final Integer b) {
        System.out.println("In Methods");
        return "Value" + counter++;
    }
}
