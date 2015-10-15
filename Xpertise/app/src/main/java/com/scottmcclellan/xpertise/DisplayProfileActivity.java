package com.scottmcclellan.xpertise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DisplayProfileActivity extends AppCompatActivity {

    private TextView first;
    private TextView last;
    private TextView email;
    private TextView pass;
    private TextView city;
    private TextView lat;
    private TextView lng;
    private TextView desc;
    private Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profile);

        first = (TextView) findViewById(R.id.firstName);
        last = (TextView) findViewById(R.id.lastName);
        email = (TextView) findViewById(R.id.email);
        pass = (TextView) findViewById(R.id.password);
        city = (TextView) findViewById(R.id.city);
        lat = (TextView) findViewById(R.id.lat);
        lng = (TextView) findViewById(R.id.lng);
        desc = (TextView) findViewById(R.id.desc);
        menu = (Button) findViewById(R.id.login);

        first.setText(LoginActivity.loggedInProfile.getFirstName());
        last.setText(LoginActivity.loggedInProfile.getLastName());
        email.setText(LoginActivity.loggedInProfile.getEmail());
        pass.setText(LoginActivity.loggedInProfile.getPassword());
        city.setText(LoginActivity.loggedInProfile.getCity());
        lat.setText(Double.toString(LoginActivity.loggedInProfile.getLat()));
        lng.setText(Double.toString(LoginActivity.loggedInProfile.getLng()));
        desc.setText(LoginActivity.loggedInProfile.getDescription());

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
