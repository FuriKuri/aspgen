package de.hbrs;

import de.hbrs.Logging;

public class Person {
    @NotNull
    public String method(@NotNull(id = 2, modified = "Sec") final String a, @NotNull(deleted = "First") final Integer b) {
        System.out.println("In Methods");
        return "Value" + counter++;
    }

    public String method2(@NotNull(id = 4, modified = "First") final String a, final Integer b) {
        System.out.println("In Methods");
        return "Value" + counter++;
    }
}
