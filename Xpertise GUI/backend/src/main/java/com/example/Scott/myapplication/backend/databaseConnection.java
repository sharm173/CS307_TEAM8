package com.example.Scott.myapplication.backend;

import com.google.appengine.api.utils.SystemProperty;
import com.sun.java.util.jar.pack.*;
//import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Scott on 10/11/2015.
 */
public class databaseConnection {

    // Alert the user of a failure with the database
    private static void databaseError(Constants.DB_ERROR err){

        // TODO: Handle all errors
        switch(err){
            case INSERT_ERROR:

                break;
            case SELECT_ERROR:

                break;
            case UPDATE_ERROR:

                break;
            case BAD_INPUT_ERROR:

                break;
            default:
                // Do nothing
        }

    }

    //Contact the database and store a single profile in it
    public static void storeProfile(Profile input) {
        //TODO: Replace table name with the name of the database table that stores the profile information
        String url = Constants.DATABASE_URL;
        try {
            Class.forName(Constants.GOOGLE_DRIVER);
            Connection conn = DriverManager.getConnection(url);
            try {
                if (input == null) {
                    //TODO handle null profile;
                    databaseError(Constants.DB_ERROR.BAD_INPUT_ERROR);
                } else {
                    int success;

                    String statement = "INSERT INTO profile VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(statement);

                    // Add all values from profile into prepared statement
                    stmt.setInt(1, input.getPid());
                    stmt.setString(2, input.getFirstName());
                    stmt.setString(3, input.getLastName());
                    stmt.setString(4, input.getPassword());
                    stmt.setString(5, input.getEmail());
                    stmt.setString(6, input.getCity());
                    stmt.setDouble(7, input.getLat());
                    stmt.setDouble(8, input.getLng());
                    stmt.setString(9, input.getDescription());

                    // Returns 1 on success, 0 on fail
                    success = stmt.executeUpdate();

                    if (success == 1) {
                        //TODO successfully posted to database
                    }
                    else {
                        //TODO post to database failed
                        databaseError(Constants.DB_ERROR.INSERT_ERROR);
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
        String url = Constants.DATABASE_URL;
        ArrayList<Profile> allProfiles = new ArrayList<Profile>();

        try {
            Class.forName(Constants.GOOGLE_DRIVER);
            Connection conn = DriverManager.getConnection(url);
            try {

                String statement = "SELECT * FROM profile";
                PreparedStatement stmt = conn.prepareStatement(statement);

                ResultSet response = stmt.executeQuery();
                while (response.next()) {
                    Profile temp = new Profile();
                    temp.setPid(response.getInt("pid"));
                    temp.setDescription(response.getString("description"));
                    temp.setFirstName(response.getString("firstName"));
                    temp.setLastName(response.getString("lastName"));
                    temp.setPassword(response.getString("password"));
                    temp.setEmail(response.getString("email"));
                    temp.setCity(response.getString("city"));
                    temp.setLat(response.getDouble("lat"));
                    temp.setLng(response.getDouble("lng"));
                    allProfiles.add(temp);
                }

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
        String url = Constants.DATABASE_URL;
        Profile ret = new Profile();

        try {
            Class.forName(Constants.GOOGLE_DRIVER);
            Connection conn = DriverManager.getConnection(url);
            try {

                String statement = "SELECT * FROM profile WHERE pid = ?";
                PreparedStatement stmt = conn.prepareStatement(statement);
                stmt.setString(1, Integer.toString(pid));

                ResultSet response = stmt.executeQuery();
                ret.setDescription(response.getString("description"));
                ret.setFirstName(response.getString("firstName"));
                ret.setLastName(response.getString("lastName"));
                ret.setPassword(response.getString("password"));
                ret.setEmail(response.getString("email"));
                ret.setCity(response.getString("city"));
                ret.setLat(response.getDouble("lat"));
                ret.setLng(response.getDouble("lng"));

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
        String url = Constants.DATABASE_URL;
        Profile ret = new Profile();
        try {
            Class.forName(Constants.GOOGLE_DRIVER);
            Connection conn = DriverManager.getConnection(url);
            try {
                String statement = "SELECT * FROM profile WHERE username = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(statement);
                stmt.setString(1, username);
                stmt.setString(1, password);

                ResultSet response = stmt.executeQuery();
                ret.setDescription(response.getString("description"));
                ret.setFirstName(response.getString("firstName"));
                ret.setLastName(response.getString("lastName"));
                ret.setPassword(response.getString("password"));
                ret.setEmail(response.getString("email"));
                ret.setCity(response.getString("city"));
                ret.setLat(response.getDouble("lat"));
                ret.setLng(response.getDouble("lng"));

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
