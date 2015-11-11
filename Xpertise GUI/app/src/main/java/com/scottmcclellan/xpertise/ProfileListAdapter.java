package com.scottmcclellan.xpertise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.scott.myapplication.backend.xpertiseAPI.model.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoshFoeh on 11/9/15.
 */
public class ProfileListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<Profile> profiles;

    public ProfileListAdapter(Context context, List<Profile> profiles){
        this.profiles = profiles;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        return profiles.size();
    }

    @Override
    public Object getItem(int pos){
        return profiles.get(pos);
    }

    @Override
    public long getItemId(int pos){
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parentGroup){
        View v;
        if(convertView == null){
            v = inflater.inflate(R.layout.user_list, parentGroup, false);
            Profile p = profiles.get(pos);
            //set image
            //set rating
            ((TextView)v.findViewById(R.id.userName)).setText(p.getFirstName() + " " + p.getLastName());
            ((TextView)v.findViewById(R.id.userCity)).setText("Located in " + p.getCity());
            //set tags
        }
        else{
            v = convertView;
        }
        return v;
    }

}
