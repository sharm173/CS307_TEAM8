package com.example.Scott.myapplication.backend;

/**
 * The object model for the data we are sending through endpoints
 */
public class MyBean {

    private int pid;
    private int gid;

    private String data;

    private Boolean success;

    private double hiLat;
    private double loLat;
    private double hiLng;
    private double loLng;

    public double getHiLat() { return hiLat; }

    public void setHiLat(double set) { hiLat = set; }

    public double getLoLat() { return loLat; }

    public void setLoLat(double set) { loLat = set; }

    public double getHiLng() { return hiLng; }

    public void setHiLng(double set) { hiLng = set; }

    public double getLoLng() { return loLng; }

    public void setLoLng(double set) { loLng = set; }

    public void setData(String stuff) { data = stuff; }

    public String getData() { return data; }

    public Boolean getBool(){ return success; }

    public void setBool(Boolean input){ success = input; }

    public void setPid(int set) { pid = set; }

    public int getPid() { return pid; }

    public void setGid(int set) { gid = set; }

    public int getGid() { return gid; }
}