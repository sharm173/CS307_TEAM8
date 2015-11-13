package com.scottmcclellan.xpertise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.scott.myapplication.backend.xpertiseAPI.model.MyBean;
import com.example.scott.myapplication.backend.xpertiseAPI.model.Profile;

import org.w3c.dom.Text;

import java.util.List;

public class DisplayProfileActivity extends AppCompatActivity {

    private TextView first;
    private TextView last;
    private TextView email;
    private TextView pass;
    private TextView city;
//    private TextView lat;
//    private TextView lng;
    private TextView desc;
    private TextView tagsView;
    private Button login;
    private Button edit;
    private Button find;

    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profile);

        profile = LoginActivity.loggedInProfile;
        first = (TextView) findViewById(R.id.firstName);
        last = (TextView) findViewById(R.id.lastName);
        email = (TextView) findViewById(R.id.email);
        pass = (TextView) findViewById(R.id.password);
        city = (TextView) findViewById(R.id.city);
        desc = (TextView) findViewById(R.id.desc);
        tagsView = (TextView) findViewById(R.id.tags);
        login = (Button) findViewById(R.id.login);
        edit = (Button) findViewById(R.id.edit);
        find = (Button) findViewById(R.id.findButton);

        first.setText(profile.getFirstName());
        last.setText(profile.getLastName());
        email.setText(profile.getEmail());
        pass.setText(profile.getPassword());
        city.setText(profile.getCity());
        desc.setText(profile.getDescription());

        List<MyBean> tagBeans = profile.getTags();
        String tags = "";
        for(int j = 0; j < tagBeans.size(); j++){
            tags += tagBeans.get(j).getData();
            if(j != (tagBeans.size() - 1)){
                tags += ", ";
            }
        }
        tagsView.setText(tags);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayProfileActivity.this, ListUsersActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
