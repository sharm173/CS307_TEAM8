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

    @ApiMethod(name = "profile_get", httpMethod = "get")
    public Profile getProfile(@Named("pid") Integer pid) throws NotFoundException {
        try {
            Profile ret = databaseConnection.getSpecificProfile(pid);
            if (ret == null) return null;
            else return ret;
        }
        catch (IndexOutOfBoundsException e) {
            throw new NotFoundException("Profile not found with an index: " + pid);
        }
    }

    @ApiMethod (name = "profile_listAll")
    public ArrayList<Profile> listProfiles() {
        ArrayList<Profile> profiles = new ArrayList<Profile>();
        profiles = databaseConnection.getAllProfiles();
        return profiles;
    }

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
            return ret;
        }
        ret.setBool(true);
        databaseConnection.storeProfile(response);
        return ret;
    }

    @ApiMethod(name = "profile_auth")
    public Profile authProfile(@Named("email") String email, @Named("password") String password) {
        //Attempts to find the profile information associated with the provided password and email
        //If found, return a profile object containing the information
        //If not found, return an error
        Profile ret = new Profile();
        ret = databaseConnection.findUserPassCombo(email, password);
        if (ret == null) return null;
        return ret;
    }


}
