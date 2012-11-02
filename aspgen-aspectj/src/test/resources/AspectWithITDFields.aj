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
    @Generated(1234)
    private String OneKlasse.sField;
    @Generated("mappingId")
    public final int TwoKlasse.intWithValue = 4;
    @Generated("1234")
    @MyAnno
    protected static String OneKlasse.staticField = "init";
    @Generated("1234")
    Double OneKlasse.number = 4.0;
}
