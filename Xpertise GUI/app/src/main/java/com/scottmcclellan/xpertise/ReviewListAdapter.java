package com.scottmcclellan.xpertise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
        return reviews.size();
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
            ((TextView)v.findViewById(R.id.userName2)).setText(r.getReviewerPid());//change to reviewer's name
            ((TextView)v.findViewById(R.id.userRating2)).setText(r.getStars());
            ((EditText)v.findViewById(R.id.userReview2)).setText(r.getReviewDesc());
            //set tags
        }
        else{
            v = convertView;
        }
        return v;
    }

}
