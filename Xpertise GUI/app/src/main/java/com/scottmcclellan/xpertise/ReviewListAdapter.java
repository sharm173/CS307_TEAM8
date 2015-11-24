package com.scottmcclellan.xpertise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.scott.myapplication.backend.xpertiseAPI.model.Profile;
import com.example.scott.myapplication.backend.xpertiseAPI.model.Review;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by tusharsharma on 11/13/15.
 */
public class ReviewListAdapter extends BaseAdapter {
    Profile reviewer;
    Profile reviewee;
    LayoutInflater inflater;
    List<Review> reviews;

    public ReviewListAdapter(Context context, List<Review> reviews){
        this.reviews = reviews;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        int a = 0;
        try {
            a = reviews.size();
        }
        catch (Exception e){
            a = 0;
        }
        finally {
            return a;
        }
    }

    @Override
    public Object getItem(int pos){
        return reviews.get(pos);
    }

    @Override
    public long getItemId(int pos){
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parentGroup){
        View v;
        if(convertView == null){
            v = inflater.inflate(R.layout.review_list, parentGroup, false);
            Review r = reviews.get(pos);
            //set image
            //set rating
            ((TextView)v.findViewById(R.id.userName2)).setText(r.getReviewerName());//change to reviewer's name
           // ((TextView)v.findViewById(R.id.userRating2)).setText(r.getStars());
            ((RatingBar)v.findViewById(R.id.ratingBar2)).setRating(r.getStars());
            ((EditText)v.findViewById(R.id.userReview2)).setText(r.getReviewDesc());
            //set tag


        }
        else{
            v = convertView;
        }
        return v;
    }

}
