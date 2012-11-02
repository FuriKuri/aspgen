package de.hbrs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.*;

import java.io.File;
import java.io.BufferedReader;

import java.util.*;
import java.awt.*; 

public privileged aspect FileAspect {
    /**
     * My JavaDoc
     */
    @Generated("1234")
    after(String value, int number) throws IOException : execution(public void MyClass.doMethod3(String, int)) && args(value) && args(number) {
        System.out.println("Execute doMethod");
    }

}
