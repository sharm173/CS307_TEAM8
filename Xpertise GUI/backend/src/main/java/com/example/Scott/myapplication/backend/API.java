package com.example.Scott.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    //TODO
    @ApiMethod(name = "profile_get", httpMethod = "get")
    public Profile getProfile(@Named("pid") Integer pid) throws NotFoundException {
        Profile ret = null;
        try {
            ret = new Profile();
            ret = databaseConnection.getSpecificProfile(pid);
            if (ret.getLastName() == null) ret.setFirstName("ERROR");
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundException("Profile not found with an index: " + pid);
        }
        ret.setTags(getTags(ret.getPid()));
        //ret.setGroups(getGroups(ret.getPid())); TODO: Uncomment when groups are done
        return ret;
    }

    //Returns an ArrayList of all the profiles currently stored in the database
    //TODO
    @ApiMethod(name = "profile_listAll")
    public ArrayList<Profile> listProfiles() {
        ArrayList<Profile> profiles = new ArrayList<Profile>();
        profiles = databaseConnection.getAllProfiles();
        //lol gl trying to understand my magical one liners
        for (int i = 0; i < profiles.size(); i++) {
            profiles.get(i).setTags(getTags(profiles.get(i).getPid()));
            //profiles.get(i).setGroups(getGroups(profiles.get(i).getPid())); TODO: Uncomment when groups are done
        }
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
        response.setLat(Math.abs(lat));
        response.setLng(Math.abs(lng));
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
    //TODO
    @ApiMethod(name = "profile_auth", httpMethod = "get")
    public Profile authProfile(@Named("email") String email, @Named("password") String password) {
        //Attempts to find the profile information associated with the provided password and email
        //If found, return a profile object containing the information
        //If not found, return an error
        Profile ret = databaseConnection.findUserPassCombo(email, password);
        if (ret.getFirstName() == null) return null;
        ret.setTags(getTags(ret.getPid()));
        //ret.setGroups(getGroups(ret.getPid())); TODO: Uncomment when groups are done
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
    //TODO
    @ApiMethod(name = "profile_city")
    public ArrayList<Profile> profilesInCity(@Named("pid") Integer pid, @Named("city") String city) {
        ArrayList<Profile> ret = new ArrayList<Profile>();
        Profile main = new Profile();
        try {
            main = getProfile(pid);
        } catch (Exception e) {
            main.setFirstName("ERROR");
            ret.add(main);
            return ret;
        }
        ret = databaseConnection.getProfilesInCity(main, city);

        for (int i = 0; i < ret.size(); i++) {
            ret.get(i).setTags(getTags(ret.get(i).getPid()));
            //ret.get(i).setGroups(getGroups(ret.get(i).getPid())); TODO: uncomment when groups are done
        }
        return ret;
    }

    //Find all profiles within a certain radius in miles
    //TODO
    @ApiMethod(name = "profile_radius")
    public ArrayList<Profile> profilesInRadius(@Named("pid") Integer pid, @Named("miles") double distance) {
        ArrayList<Profile> ret = new ArrayList<Profile>();
        //sad lyfe trevor did everything for me in database class
        ret = databaseConnection.profilesInRadius(pid, distance);

        for (int i = 0; i < ret.size(); i++) {
            ret.get(i).setTags(getTags(ret.get(i).getPid()));
            //ret.get(i).setGroups(getGroups(ret.get(i).getPid())); TODO: uncomment when groups are done
        }
        return ret;
    }

    //Add a review to the database for a profile
    @ApiMethod(name = "profile_postReview")
    public MyBean postReview(@Named("reviewerPid") Integer reviewerPid, @Named("reviewerName") String reviewerName, @Named("revieweePid") Integer revieweePid, @Named("stars") Integer stars, @Named("description") String desc) {//TODO: Add Review fields. or possibly accept review object?
        Review store = new Review();
        MyBean ret = new MyBean();
        store.setReviewer_pid(reviewerPid);
        store.setReviewee_pid(revieweePid);
        store.setStars(stars);
        store.setReviewDesc(desc);
        store.setReviewerName(reviewerName);
        ret = databaseConnection.postReview(store);
        return ret;
    }

    //Get all reviews for a specific profile
    @ApiMethod(name = "profile_getReviews")
    public ArrayList<Review> getReviews(@Named("pid") Integer pid) {
        ArrayList<Review> ret = new ArrayList<Review>();
        ret = databaseConnection.getReviews(pid);
        return ret;
    }

    //Associate a single tag to a profile
    @ApiMethod(name = "profile_setTag")
    public MyBean setTag(@Named("pid") Integer pid, @Named("tag") String tag) {
        MyBean ret = new MyBean();
        try {
            ret = databaseConnection.setTag(pid, tag);
        } catch (Exception e) {
            ret.setData("Failed to call database class method");
            ret.setBool(false);
        }
        return ret;
    }

    //Get all tags associated with a profile
    @ApiMethod(name = "profile_getTags")
    public ArrayList<MyBean> getTags(@Named("pid") Integer pid) {
        ArrayList<MyBean> ret = new ArrayList<MyBean>();
        try {
            ret = databaseConnection.getTags(pid);
        } catch (Exception e) {
            MyBean temp = new MyBean();
            temp.setBool(false);
            temp.setData("Failed to call database class method");
            ret.add(temp);
        }
        return ret;
    }

    //TODO
    //Get all profiles that have a certain tag
    @ApiMethod(name = "profile_searchTag")
    public ArrayList<Profile> searchTags(@Named("tag") String tag) {
        ArrayList<Profile> ret = new ArrayList<Profile>();
        ret = databaseConnection.searchTags(tag);
        for (int i = 0; i < ret.size(); i++) {
            ret.get(i).setTags(getTags(ret.get(i).getPid()));
            //ret.get(i).setGroups(getGroups(ret.get(i).getPid())); TODO: uncomment when groups are done
        }
        return ret;
    }

    //TODO: test
    //Associates a single group with a profile
    @ApiMethod(name = "profile_setGroup")
    public MyBean setGroup(@Named("pid") Integer pid, @Named("gid") Integer gid) {
        MyBean ret = new MyBean();
        ret = databaseConnection.setGroup(pid, gid);
        return ret;
    }

    //TODO: test
    //Gets all groups associated with a profile
    @ApiMethod(name = "profile_getGroups")
    public ArrayList<Group> getGroups(@Named("pid") Integer pid) {
        ArrayList<Group> ret = new ArrayList<Group>();
        ArrayList<Integer> gids = new ArrayList<Integer>();
        gids = databaseConnection.getGroups(pid);
        for (int i = 0; i < gids.size(); i++) {
            Group temp = new Group();
            temp = getGroup(gids.get(i));
        }
        return ret;
    }

    //TODO: test
    //Creates a new group
    @ApiMethod(name = "group_post")
    public MyBean postGroup(@Named("name") String name, @Named("desc") String desc, @Named("makerPid") Integer makerPid) {
        MyBean ret = new MyBean();
        Group insert = new Group();
        insert.setName(name);
        insert.setDesc(desc);
        insert.setCreatorPid(makerPid);
        ret = databaseConnection.postGroup(insert);
        return ret;
    }

    //TODO: test
    //Gets a specific group
    @ApiMethod(name = "group_get")
    public Group getGroup(@Named("gid") Integer gid) {
        Group ret = new Group();
        ret = databaseConnection.getGroup(gid);
        Profile maker = null;
        try {
            maker = getProfile(ret.getCreatorPid());
        } catch (Exception e) {
            maker = new Profile();
            maker.setDescription("Failed to find creator's profile");
        }
        ret.setMembers(getGroupMembers(ret));
        ret.setPosts(getPosts(ret));
        return ret;
    }

    //TODO test
    //Helper method to populate group members arraylist when getting specific group
    public ArrayList<Profile> getGroupMembers(Group group) {
        ArrayList<Profile> ret = new ArrayList<Profile>();
        ArrayList<Integer> pids = new ArrayList<Integer>();
        pids = databaseConnection.getGroupMembers(group);
        for (int i = 0; i < pids.size(); i++) {
            Profile temp = new Profile();
            try {
                temp = getProfile(pids.get(i));
            } catch (Exception e) {
                temp.setDescription("Failed to find profile of pid " + pids.get(i));
            }
            ret.add(temp);
        }
        return ret;
    }

    //TODO: test
    //Helper method to populate group posts arraylist when getting specific group
    public ArrayList<Post> getPosts(Group group) {
        ArrayList<Post> ret = new ArrayList<Post>();
        ret = databaseConnection.getPosts(group);
        Collections.sort(ret, new Comparator<Post>() {
            @Override
            public int compare(Post p1, Post p2) {
                return p1.getPostID() - p2.getPostID(); // Ascending
            }
        });
        return ret;
    }
}
