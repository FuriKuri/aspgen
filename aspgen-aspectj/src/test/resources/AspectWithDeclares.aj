package de.hbrs;


public privileged aspect FileAspect {
    //@Generated("1234")
    declare soft : Exception : execution(public String Person.doSomething());
    
    declare soft : Exception : execution(public String Person.doSomething2());

}
