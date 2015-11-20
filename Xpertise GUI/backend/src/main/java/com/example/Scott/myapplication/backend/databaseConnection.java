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
        // Alert user of error with some sort of dialog box?
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
    public static MyBean storeProfile(Profile input) {
        String url = Constants.DATABASE_URL;
        MyBean bean = new MyBean();
        String statement = "";
        int success = 0;
        try {
            // Load GoogleDriver class at runtime
            Class.forName(Constants.GOOGLE_DRIVER);

            // Open connection to database
            Connection conn = DriverManager.getConnection(url);
            try {
                if (input == null) {
                    bean.setBool(false);
                    bean.setData("Sent Database a null Profile");
                    databaseError(Constants.DB_ERROR.BAD_INPUT_ERROR);
                    return bean;
                } else {


                    statement = "INSERT INTO profile (firstName, lastName, password, email, city, lat, lng, description)" +
                            " VALUES ('" +
                            input.getFirstName() + "', '" +
                            input.getLastName() + "', '" +
                            input.getPassword() + "', '" +
                            input.getEmail() + "', '" +
                            input.getCity() + "', " +
                            input.getLat() + ", " +
                            input.getLng() + ", '" +
                            input.getDescription() + "')";
                    PreparedStatement stmt = conn.prepareStatement(statement);

                    // Returns 1 on success, 0 on fail
                    success = stmt.executeUpdate();
                }
            }
            finally {
                // Always make sure to close database connection
                conn.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if (success == 1) {
            bean.setBool(true);
            bean.setData("Successful insert");
        } else {
            databaseError(Constants.DB_ERROR.INSERT_ERROR);
            bean.setBool(false);
            bean.setData("Error: Inserting into database failed");
        }
        return bean;
    }

    // This function will update the row containing the input profile's PID to match the
    // rest of the fields of the input profile.  Returns a MyBean object indicating
    // success or fail
    public static MyBean editProfile(Profile input){
        String url = Constants.DATABASE_URL;
        MyBean bean = new MyBean();
        String statement = "";
        int success = 0;
        try{
            Class.forName(Constants.GOOGLE_DRIVER);
            Connection conn = DriverManager.getConnection(url);

            try{
                if(input == null){
                    bean.setBool(false);
                    bean.setData("Error: Sent database a null profile");
                    return bean;
                }

                statement = "UPDATE profile " +
                        "SET firstName = '" + input.getFirstName() + "'," +
                        " lastName = '" + input.getLastName() + "'," +
                        " password = '" + input.getPassword() + "'," +
                        " email = '" + input.getEmail() + "'," +
                        " city = '" + input.getCity() + "'," +
                        " lat = " + input.getLat() + "," +
                        " lng = " + input.getLng() + "," +
                        " description = '" + input.getDescription() + "'" +
                        " WHERE pid = " + input.getPid();
                PreparedStatement stmt = conn.prepareStatement(statement);
                success = stmt.executeUpdate();


            }
            finally{
                conn.close();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(success == 1){
            // Update successful
            bean.setBool(true);
            bean.setData("Successful update");
        }else{
            // Update failed
            bean.setBool(false);
            bean.setData("Error: Update failed; Statement: " + statement);
        }

        return bean;
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

                String statement = "SELECT * FROM profile WHERE pid=(?)";
                PreparedStatement stmt = conn.prepareStatement(statement);
                stmt.setString(1, Integer.toString(pid));

                ResultSet response = stmt.executeQuery();
                response.next();
                ret.setPid(response.getInt("pid"));
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
        String statement = "";
        try {
            Class.forName(Constants.GOOGLE_DRIVER);
            Connection conn = DriverManager.getConnection(url);
            try {
                statement = "SELECT * FROM profile WHERE email=('" + username + "') AND password=('" + password + "')";
                PreparedStatement stmt = conn.prepareStatement(statement);

                ResultSet response = stmt.executeQuery();
                response.next();
                ret.setPid(response.getInt("pid"));
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

        // If response from database was 'none selected', return null
        if (ret.getFirstName() == null)
            ret = null;
        return ret;
    }

    public static ArrayList<Profile> getProfilesInCity(Profile input, String city) {
        ArrayList<Profile> ret = new ArrayList<Profile>();
        String url = Constants.DATABASE_URL;
        int pid = input.getPid();

        try {
            Class.forName(Constants.GOOGLE_DRIVER);
            Connection conn = DriverManager.getConnection(url);
            try {

                String statement = "SELECT * FROM profile WHERE city='" + city + "' AND pid<>" + pid;
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
                    ret.add(temp);
                }

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

    public static ArrayList<Profile> profilesInRadius(int pid, double dist) {
        ArrayList<Profile> profiles = new ArrayList<Profile>();
        String url = Constants.DATABASE_URL;

        try{
            Class.forName(Constants.GOOGLE_DRIVER);
            Connection conn = DriverManager.getConnection(url);

            try{
                Profile profile = getSpecificProfile(pid);
                MyBean latLngHolder = Search.boundingBox(profile.getLat(), profile.getLng(), dist);
                double hiLat = latLngHolder.getHiLat();
                double loLat = latLngHolder.getLoLat();
                double hiLng = latLngHolder.getHiLng();
                double loLng = latLngHolder.getLoLng();

                //lat, lng
                String statement = "SELECT * FROM profile WHERE lat<=" + hiLat + " AND lat>=" + loLat +
                        " AND lng<=" + hiLng + " AND lng>=" + loLng + " AND pid<>" + pid;
                PreparedStatement stmt = conn.prepareStatement(statement);

                ResultSet response = stmt.executeQuery();
                while(response.next()){
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
                    profiles.add(temp);
                }

            }finally{
                conn.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return profiles;
    }

    //TODO
    public static MyBean postReview(Review input){
        String url = Constants.DATABASE_URL;
        MyBean bean = new MyBean();
        int success = 0;
        String statement = null;
        try{
            Class.forName(Constants.GOOGLE_DRIVER);
            Connection conn = DriverManager.getConnection(url);

            try{

                statement = "INSERT INTO review (aboutID, byID, rating, review) " +
                        "VALUES (" +
                        Integer.toString(input.getReviewer_pid()) + ", " +
                        Integer.toString(input.getReviewee_pid()) + ", " +
                        Integer.toString(input.getStars()) + ", '" +
                        input.getReviewDesc() + "')"; //TODO: Update to store reviewer name when db is updated
                PreparedStatement stmt = conn.prepareStatement(statement);

                success = stmt.executeUpdate();

            }finally{
                conn.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        if(success == 1){
            bean.setBool(true);
            bean.setData("Successful insert");
        }else{
            bean.setBool(false);
            bean.setData("Error: Inserting into database failed. statement: " + statement);
        }

        return bean;
    }

    //TODO
    public static ArrayList<Review> getReviews(int pid){
        String url = Constants.DATABASE_URL;
        ArrayList<Review> reviews = new ArrayList<Review>();

        try {
            Class.forName(Constants.GOOGLE_DRIVER);
            Connection conn = DriverManager.getConnection(url);

            try {

                String statement = "SELECT * FROM review WHERE aboutID=(?)";
                PreparedStatement stmt = conn.prepareStatement(statement);
                stmt.setString(1, Integer.toString(pid));

                ResultSet response = stmt.executeQuery();
                while(response.next()) {
                    // Set data for myBean
                    Review temp = new Review();

                    temp.setReviewDesc(response.getString("review"));
                    temp.setReviewee_pid(response.getInt("aboutID"));
                    temp.setReviewer_pid(response.getInt("byID"));
                    temp.setStars(response.getInt("rating"));
                    //temp.setReviewerName(""); TODO: Confirm new column in database
                    reviews.add(temp);
                }

            }finally{
                conn.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return reviews;
    }

    public static MyBean setTag(int pid, String tag){
        String url = Constants.DATABASE_URL;
        MyBean bean = new MyBean();
        int success = 0;
        String statement = null;

        try{
            Class.forName(Constants.GOOGLE_DRIVER);
            Connection conn = DriverManager.getConnection(url);

            try{

                statement = "INSERT INTO tag (pid, tag) VALUES (" +
                        Integer.toString(pid) + ", '" +
                        tag + "')";
                PreparedStatement stmt = conn.prepareStatement(statement);

                success = stmt.executeUpdate();


            }finally{

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        if(success == 1){
            bean.setBool(true);
            bean.setData("Successful insert");
        }else{
            bean.setBool(false);
            bean.setData("Error: Inserting into database failed. statement: " + statement);
        }

        return bean;
    }

    public static ArrayList<MyBean> getTags(int pid){
        String url = Constants.DATABASE_URL;
        ArrayList<MyBean> beans = new ArrayList<MyBean>();
        String statement = null;

        try{
            Class.forName(Constants.GOOGLE_DRIVER);
            Connection conn = DriverManager.getConnection(url);

            try{

                statement = "SELECT * FROM tag WHERE pid=(" + pid + ")";
                PreparedStatement stmt = conn.prepareStatement(statement);

                ResultSet response = stmt.executeQuery();
                while(response.next()){
                    MyBean temp = new MyBean();

                    temp.setData(response.getString("tag"));
                    temp.setPid(response.getInt("pid"));
                    beans.add(temp);
                }

            }finally{
                conn.close();
            }


        }catch(Exception e){
            e.printStackTrace();
        }
        return beans;
    }

    // TODO: ArrayList<Profile> searchTags(tag)

    // TODO: MyBean setGroup(pid, gid)

    // TODO: ArrayList<Group> getGroups(pid)

    // TODO: MyBean postGroup(name, desc, makerPid)

    // TODO: Group getGroup(gid)

    // TODO: ArrayList<Profile> getGroupMembers(group)

    // TODO: ArrayList<Post> getPosts(group)
}
