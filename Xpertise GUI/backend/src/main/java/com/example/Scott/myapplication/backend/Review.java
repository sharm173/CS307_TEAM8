package com.example.Scott.myapplication.backend;

/**
 * Created by TheMaster on 10/28/2015.
 */
public class Review {

    private int reviewer_pid;
    private int reviewee_pid;
    private int stars; // should be a range of 0-5
    private String reviewDesc;

    public int getReviewer_pid(){
        return reviewer_pid;
    }

    public void setReviewer_pid(int p){
        reviewer_pid = p;
    }

    public int getReviewee_pid(){
        return reviewee_pid;
    }

    public void setReviewee_pid(int p){
        reviewee_pid = p;
    }

    public int getStars(){
        return stars;
    }

    public void setStars(int s){
        stars = s;
    }

    public String getReviewDesc(){
        return reviewDesc;
    }

    public void setReviewDesc(String s){
        reviewDesc = s;
    }
}
