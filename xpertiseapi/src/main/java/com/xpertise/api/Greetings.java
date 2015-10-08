package com.xpertise.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
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
    audiences = {Constants.ANDROID_AUDIENCE}
)
public class Greetings {

  public static ArrayList<Profile> profiles = new ArrayList<Profile>();

  @ApiMethod(name = "profile.get", httpMethod = "get")
  public Profile getProfile(@Named("pid") Integer pid) throws NotFoundException {
    try {
      return profiles.get(pid);
    } 
    catch (IndexOutOfBoundsException e) {
      throw new NotFoundException("Greeting not found with an index: ");
    }
  }

  public ArrayList<Profile> listProfiles() {
    return profiles;
  }

  @ApiMethod(name = "profile.post", httpMethod = "post")
  public Profile insertProfile(@Named("firstName") String firstName, @Named("lastName") String lastName, @Named("password") String password, @Named("email") String email, @Named("city") String city) {
    Profile response = new Profile();
    StringBuilder responseBuilder = new StringBuilder();
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

  /*
  @ApiMethod(name = "greetings.authed", path = "hellogreeting/authed")
  public HelloGreeting authedGreeting(User user) {
    HelloGreeting response = new HelloGreeting("hello " + user.getEmail());
    return response;
  }
  */
}
