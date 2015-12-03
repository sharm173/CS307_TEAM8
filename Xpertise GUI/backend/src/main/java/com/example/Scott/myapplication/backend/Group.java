package com.example.Scott.myapplication.backend;

import java.util.ArrayList;

/**
 * Created by Scott on 11/16/2015.
 */
public class Group {
    int gid;
    private int creatorPid;
    private Profile creator;
    private ArrayList<Profile> members;
    private String name;
    private String desc;
    private ArrayList<Post> posts;
    private ArrayList<MyBean> tags;

    public void Group() {
        members = new ArrayList<Profile>();
        posts = new ArrayList<Post>();
        creator = new Profile();
        tags = new ArrayList<MyBean>();
    }

    public void setGid(int set) { gid = set; }

    public int getGid() { return gid; }

    public void setCreatorPid(int set) { creatorPid = set; }

    public int getCreatorPid() { return creatorPid; }

    public void setCreator(Profile set) { creator = set; }

    public Profile getCreator() { return creator; }

    public void setMembers(ArrayList<Profile> set) { members = set; }

    public ArrayList<Profile> getMembers() { return members; }

    public void setName(String set) { name = set; }

    public String getName() { return name; }

    public void setPosts(ArrayList<Post> set) { posts = set; }

    public ArrayList<Post> getPosts() { return posts; }

    public void setDesc(String set) { desc = set; }

    public String getDesc() { return desc; }

    public ArrayList<MyBean> getTags() { return tags; }

    public void setTags(ArrayList<MyBean> set) { tags = set; }
}