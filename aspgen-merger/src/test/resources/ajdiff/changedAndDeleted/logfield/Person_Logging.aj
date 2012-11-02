package de.hbrs;

public privileged aspect Person_NotNull {
    @Generated(id = 1, name = "Logger", data = ";")
    private String Person.logger = "Logger.get(Person.class)";
    
    @Generated(id = 1, name = "Logger2", data = ";")
    private String Person.logger = "Logger.get(Person.class)";
}