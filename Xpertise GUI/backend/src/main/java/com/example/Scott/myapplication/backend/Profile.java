package com.example.Scott.myapplication.backend;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by JoshFoeh on 10/6/15.
 */
public class Profile implements java.io.Serializable {

    private static final long serialVersionUID = -7060210544600464481L;

    int pid;
    String firstName;
    String lastName;
    String password;
    String email; //must  be distinct
    String city;
    double lat;
    double lng;
    String description;
    ArrayList<MyBean> tags;
    ArrayList<Group> groups;

    public Profile() {
        tags = new ArrayList<MyBean>();
        groups = new ArrayList<Group>();
    };

    public String getDescription() { return description; }

    public void setDescription(String set) { description = set; }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double set) { lng = set; }

    public void setTags(ArrayList<MyBean> set) { tags = set; }

    public ArrayList<MyBean> getTags() { return tags; }

    public void setGroups(ArrayList<Group> set) { groups = set; }

    public  ArrayList<Group> getGroups() { return groups; }

}
