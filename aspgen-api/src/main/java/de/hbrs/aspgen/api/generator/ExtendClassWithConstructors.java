package de.hbrs.aspgen.api.generator;

public interface ExtendClassWithConstructors extends ExtendClass {
    ConstructorForClass appendNewConstructor(String name);
}
