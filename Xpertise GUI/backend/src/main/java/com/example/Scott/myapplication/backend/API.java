package com.example.Scott.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.NotFoundException;

import java.util.ArrayList;

import javax.inject.Named;

/**
* Defines v1 of a xpertise API, which provides simple profile methods.
*/
@Api(
    name = "xpertiseAPI",
    version = "v1",
    scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE},
    namespace = @ApiNamespace(ownerDomain = "backend.myapplication.Scott.example.com",
                                ownerName = "backend.myapplication.Scott.example.com",
                                packagePath = ""))

public class API {

    // Returns profile with specified pid
    // If pid not found, returns empty profile object with "ERROR" as firstName
    @ApiMethod(name = "profile_get", httpMethod = "get")
    public Profile getProfile(@Named("pid") Integer pid) throws NotFoundException {
        try {
            Profile ret = new Profile();
            ret = databaseConnection.getSpecificProfile(pid);
            if (ret.getLastName() == null) ret.setFirstName("ERROR");
            return ret;
        }
        catch (IndexOutOfBoundsException e) {
            throw new NotFoundException("Profile not found with an index: " + pid);
        }
    }

    //Returns an ArrayList of all the profiles currently stored in the database
    @ApiMethod (name = "profile_listAll")
    public ArrayList<Profile> listProfiles() {
        ArrayList<Profile> profiles = new ArrayList<Profile>();
        profiles = databaseConnection.getAllProfiles();
        return profiles;
    }

    //Store a profile in the database
    @ApiMethod(name = "profile_post", httpMethod = "post")
    public MyBean insertProfile(@Named("firstName") String firstName, @Named("lastName") String lastName,
                              @Named("password") String password, @Named("email") String email,
                              @Named("city") String city, @Named("lat") Double lat,
                              @Named("lng") Double lng, @Named("description") String description) {
        MyBean ret = new MyBean();
        Profile response = new Profile();
        response.setFirstName(firstName);
        response.setLastName(lastName);
        response.setPassword(password);
        response.setEmail(email);
        response.setCity(city);
        response.setLat(lat);
        response.setLng(lng);
        response.setDescription(description);
        //A Profile must have at least these fields
        if (response.getFirstName() == null || response.getLastName() == null
                || response.getPassword() == null || response.getEmail() == null) {
            ret.setBool(false);
            ret.setData("API Method Failure");
            return ret;
        }
        ret = databaseConnection.storeProfile(response);
        ret.setBool(true);
        return ret;
    }

    //Return the profile object matching the email and password combo, if not found return null
    @ApiMethod(name = "profile_auth", httpMethod = "get")
    public Profile authProfile(@Named("email") String email, @Named("password") String password) {
        //Attempts to find the profile information associated with the provided password and email
        //If found, return a profile object containing the information
        //If not found, return an error
        Profile ret = databaseConnection.findUserPassCombo(email, password);
        if (ret.getFirstName() == null) return null;
        return ret;
    }

    //Edit an existing profile in the database (no err catch so only call after profile_auth)
    @ApiMethod(name = "profile_edit", httpMethod = "post")
    public MyBean editProfile(@Named("firstName") String firstName, @Named("lastName") String lastName,
                              @Named("password") String password, @Named("email") String email,
                              @Named("city") String city, @Named("lat") Double lat,
                              @Named("lng") Double lng, @Named("description") String description,
                                @Named("pid") Integer pid) {
        Profile input = new Profile();
        input.setPid(pid);
        input.setFirstName(firstName);
        input.setLastName(lastName);
        input.setEmail(email);
        input.setPassword(password);
        input.setCity(city);
        input.setLat(lat);
        input.setLng(lng);
        input.setDescription(description);
        MyBean bean;
        bean = databaseConnection.editProfile(input);
        return bean;
    }

    //Find all profiles in the same city
    @ApiMethod(name = "profile_city")
    public ArrayList<Profile> profilesInCity(@Named("pid") Integer pid) {
        ArrayList<Profile> ret = new ArrayList<Profile>();
        try {
            Profile main = getProfile(pid);
        } catch (Exception e) {
            Profile err = ret.get(0);
            err.setFirstName("ERROR");
        }
        //TODO: Database connection call; SELECT * FROM profiles WHERE city=(main.getCity);
        return ret;
    }

    //Find all profiles within a certain radius in miles
    @ApiMethod(name = "profile_radius")
    public ArrayList<Profile> profilesInRadius(@Named("pid") Integer pid, @Named("miles") Integer miles) {
        ArrayList<Profile> ret = new ArrayList<Profile>();
        Profile main = new Profile();
        try {
            main = getProfile(pid);
        } catch (Exception e) {
            Profile err = ret.get(0);
            err.setFirstName("ERROR");
        }
        MyBean radius = Search.findRadius(main, miles);
        //TODO: Database call to retrieve all profiles in the lat and lng range
        return ret;
    }

    //Add a review to the database for a profile
    @ApiMethod(name = "profile_postReview")
    public MyBean postReview(@Named("pid") Integer pid) {//TODO: Add Review fields. or possibly accept review object?
        Profile main = new Profile();
        MyBean ret = new MyBean();
        try {
            main = getProfile(pid);
        } catch (Exception e) {
            MyBean err = new MyBean();
            err.setBool(false);
            err.setData("Failed Fetching Profile in API class");
            return err;
        }
        //TODO: Database call to store review object
        ret.setBool(true);
        return ret;
    }

    //Get all reviews for a specific profile
    @ApiMethod(name = "profile_getReviews")
    public ArrayList<Review> getReviews(@Named("pid") Integer pid) {
        ArrayList<Review> ret = new ArrayList<Review>();
        //TODO: Database call to fetch all review objects WHERE pid=pid
        return ret;
    }
}
