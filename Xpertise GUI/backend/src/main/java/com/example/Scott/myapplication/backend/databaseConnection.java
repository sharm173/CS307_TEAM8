package com.example.Scott.myapplication.backend;

import com.google.appengine.api.utils.SystemProperty;
import com.sun.java.util.jar.pack.*;

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Scott on 10/11/2015.
 */
public class databaseConnection {

    //Contact the database and store a single profile in it
    public static void storeProfile(Profile input) {
        //TODO: Replace table name with the name of the database table that stores the profile information
        String url = "jdbc:google:mysql://xpertiseservergae:database/table-name-here?user=root";
        try {
            Class.forName("com.mysql.jdbc.GoogleDriver");
            Connection conn = DriverManager.getConnection(url);
            try {
                if (input == null); //TODO handle null profile;
                else {
                    int success = -1;
                    //TODO: Change statement string to work with profile table
                    /*
                    String statement = "INSERT INTO entries (name) VALUES(?)";
                    PreparedStatement stmt = conn.prepareStatement(statement);
                    stmt.setString(1, name);
                    success = stmt.executeUpdate();
                    */
                    if (success == 1) {
                        //TODO successfully posted to database
                    }
                    else {
                        //TODO post to database failed
                    }
                }
            }
            finally {
                conn.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //Contact the database and return all profiles in it
    public static ArrayList<Profile> getAllProfiles() {
        //TODO: Replace table name with the name of the database table that stores the profile information
        String url = "jdbc:google:mysql://xpertiseservergae:database/table-name-here?user=root";
        ArrayList<Profile> allProfiles = new ArrayList<Profile>();

        try {
            Class.forName("com.mysql.jdbc.GoogleDriver");
            Connection conn = DriverManager.getConnection(url);
            try {
                //TODO: Change statement string to work with profile table
                /*
                String statement = "SELECT * FROM entries";
                PreparedStatement stmt = conn.prepareStatement(statement);
                ResultSet response = stmt.executeQuery();
                while (response.next()) {
                    Profile temp = new Profile();
                    //TODO: for each of these getString() calls, match to the column names in the database
                    temp.setDescription(response.getString("description"));
                    temp.setFirstName(response.getString("firstName"));
                    temp.setLastName(response.getString("lastName"));
                    temp.setPassword(response.getString("password"));
                    temp.setEmail(response.getString("email"));
                    temp.setCity(response.getString("city"));
                    //TODO: figure out how to get doubles from the ResultSet type
                    temp.setLat(response.???);
                    temp.setLng(response.???);
                    allProfiles.add(temp);
                }
                */
            }
            finally {
                conn.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return allProfiles;
    }

    //Contact the database and return the profile with the given Profile ID (pid)
    public static Profile getSpecificProfile(int pid) {
        //TODO: Replace table name with the name of the database table that stores the profile information
        String url = "jdbc:google:mysql://xpertiseservergae:database/table-name-here?user=root";
        Profile ret = new Profile();

        try {
            Class.forName("com.mysql.jdbc.GoogleDriver");
            Connection conn = DriverManager.getConnection(url);
            try {
                //TODO: Change statement string to work with profile table
                /*
                String statement = "SELECT * FROM entries";
                PreparedStatement stmt = conn.prepareStatement(statement);
                stmt.setString(1, pid);
                ResultSet response = stmt.executeQuery();
                //TODO: for each of these getString() calls, match to the column names in the database
                ret.setDescription(response.getString("description"));
                ret.setFirstName(response.getString("firstName"));
                ret.setLastName(response.getString("lastName"));
                ret.setPassword(response.getString("password"));
                ret.setEmail(response.getString("email"));
                ret.setCity(response.getString("city"));
                //TODO: figure out how to get doubles from the ResultSet type
                ret.setLat(response.???);
                ret.setLng(response.???);
                */
            }
            finally {
                conn.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    //Contact the database and return the profile object that matches the given username and password combo
    //If it cannot find a profile matching the username and password combo, null is returned
    public static Profile findUserPassCombo(String username, String password) {
        //TODO: Replace table name with the name of the database table that stores the profile information
        String url = "jdbc:google:mysql://xpertiseservergae:database/table-name-here?user=root";
        Profile ret = new Profile();
        try {
            Class.forName("com.mysql.jdbc.GoogleDriver");
            Connection conn = DriverManager.getConnection(url);
            try {
                //TODO: Change statement string to work with profile table
                /*
                String statement = "SELECT * FROM entries";
                PreparedStatement stmt = conn.prepareStatement(statement);
                stmt.setString(1, pid);
                ResultSet response = stmt.executeQuery();
                //Check if a profile was found
                if (response == null) return null;
                //TODO: for each of these getString() calls, match to the column names in the database
                ret.setDescription(response.getString("description"));
                ret.setFirstName(response.getString("firstName"));
                ret.setLastName(response.getString("lastName"));
                ret.setPassword(response.getString("password"));
                ret.setEmail(response.getString("email"));
                ret.setCity(response.getString("city"));
                //TODO: figure out how to get doubles from the ResultSet type
                ret.setLat(response.???);
                ret.setLng(response.???);
                */
            }
            finally {
                conn.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
}
