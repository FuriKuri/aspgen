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
    after(String value, int number) throws IOException : execution(public void MyClass.doMethod3(String, int)) && args(value) && args(number) {
        System.out.println("Execute doMethod");
    }

    
    String around(String value) : execution(public void MyClass.doMethod2(String)) && args(value) {
        System.out.println("Execute doMethod");
        return "";
    }
}
