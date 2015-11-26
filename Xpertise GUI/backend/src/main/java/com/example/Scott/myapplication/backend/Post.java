package com.example.Scott.myapplication.backend;

/**
 * Created by Scott on 11/16/2015.
 */
public class Post {

    int gid;
    int pid;
    int postID;
    private String date;
    private String title;
    private String body;

    public void setPid(int set) { pid = set; }

    public int getPid() { return pid; }

    public void setGid(int set) { gid = set; }

    public int getGid() { return gid; }

    public void setPostID(int set) { postID = set; }

    public int getPostID() { return postID; }

    public void setDate(String set) { date = set; }

    public String getDate() { return date; }

    public void setTitle(String set) { title = set; }

    public String getTitle() { return title; }

    public void setBody(String set) { body = set; }

    public String getBody() { return body; }
}
