package ru.otus.groovy.task07;

import java.lang.String;

public class SimpleApplication {
    private String field1;

    public String getField1() {
        return this.field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String toString() {
        return "SimpleApplication{field1 = " + this.field1 + "}";
    }
}
