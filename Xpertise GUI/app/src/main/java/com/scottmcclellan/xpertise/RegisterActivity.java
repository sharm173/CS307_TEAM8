package com.scottmcclellan.xpertise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scott.myapplication.backend.xpertiseAPI.model.Profile;

public class RegisterActivity extends AppCompatActivity {

    private EditText first;
    private EditText last;
    private EditText email;
    private EditText pass;
    private EditText city;
    private Button submit;
    public static Profile myProfile = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        first = (EditText) findViewById(R.id.firstName);
        last = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        city = (EditText) findViewById(R.id.city);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check that all fields have text
                //API call
                //proceed to next activity
                if (first.getText().toString() == null || last.getText().toString() == null ||
                        email.getText().toString() == null || pass.getText().toString() == null ||
                        city.getText().toString() == null) {
                    Toast.makeText(RegisterActivity.this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    myProfile.setFirstName(first.getText().toString());
                    myProfile.setLastName(last.getText().toString());
                    myProfile.setEmail(email.getText().toString());
                    myProfile.setPassword(pass.getText().toString());
                    myProfile.setCity(city.getText().toString());
                    myProfile.setLat(0.00);
                    myProfile.setLng(0.00);
                    myProfile.setDescription("None");
                    //TODO: API call to store profile object
                    Toast.makeText(RegisterActivity.this, "Your profile has been successfully created!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
