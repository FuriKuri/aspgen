package de.hbrs.aspgen.api.generator;

public interface ExtendClassWithFields extends ExtendClass {
    FieldForClass appendNewField(String name);
}
