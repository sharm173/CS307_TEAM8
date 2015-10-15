package com.example.Scott.myapplication.backend;

/**
 * The object model for the data we are sending through endpoints
 */
public class MyBean {

    private String data;

    private Boolean success;

    public void setData(String stuff) { data = stuff; }

    public String getData() { return data; }

    public Boolean getBool(){ return success; }

    public void setBool(Boolean input){ success = input; }
}