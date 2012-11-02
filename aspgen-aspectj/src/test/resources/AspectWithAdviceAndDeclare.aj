package de.hbrs;

import de.hbrs.aspgen.annotation.Generated;

import de.hbrs.aspgen.generator.errorhandling.HandleException;

public privileged aspect Person_HandleException perthis(this(Person)) {
    //@Generated(id = {newid1}, name = "softeningCatch1", data = "public:String:doSomething5;;")
    declare soft : Exception : execution(public String Person.doSomething5());

    //@Generated(id = {newid1}, name = "softeningCatch1", data = "public:String:doSomething5;;")
    declare soft : Exception : execution(public String Person.doSomething6());
    
    @Generated(id = {newid1}, name = "Catch", data = "public:String:doSomething5;;")
    String around() : execution(public String Person.doSomething5()) {
        try {
            return proceed();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}