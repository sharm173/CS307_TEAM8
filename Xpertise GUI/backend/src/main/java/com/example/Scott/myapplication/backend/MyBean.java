package com.example.Scott.myapplication.backend;

/**
 * The object model for the data we are sending through endpoints
 */
public class MyBean {

    private Boolean success;

    public Boolean getBool(){ return success; }

    public void setBool(Boolean input){ success = input; }
}