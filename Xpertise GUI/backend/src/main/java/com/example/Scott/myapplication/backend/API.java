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

    public static ArrayList<Profile> profiles = new ArrayList<Profile>();

    @ApiMethod(name = "profile.get", httpMethod = "get")
    public Profile getProfile(@Named("pid") Integer pid) throws NotFoundException {
        try {
            return profiles.get(pid);
        }
        catch (IndexOutOfBoundsException e) {
            throw new NotFoundException("Profile not found with an index: ");
        }
    }

    @ApiMethod (name = "listProfiles")
    public ArrayList<Profile> listProfiles() {
        return profiles;
    }

    @ApiMethod(name = "profile.post", httpMethod = "post")
    public Profile insertProfile(@Named("firstName") String firstName, @Named("lastName") String lastName, @Named("password") String password, @Named("email") String email, @Named("city") String city) {
        Profile response = new Profile();
        int pid = profiles.size();
        response.setPid(pid);
        response.setFirstName(firstName);
        response.setLastName(lastName);
        response.setPassword(password);
        response.setEmail(email);
        response.setCity(city);
        profiles.add(response);
        return response;
    }

    @ApiMethod(name = "sayHi")
    public MyBean sayHi() {
        MyBean data = new MyBean();
        data.setData("Hello, World!");
        return data;
    }


    @ApiMethod(name = "profile.auth", httpMethod = "auth")
    public Profile authProfile(@Named("email") String email, @Named("password") String password) {
        //Attempts to find the profile information associated with the provided password and email
        //If found, return a profile object containing the information
        //If not found, return an error

        return null;
    }

}
