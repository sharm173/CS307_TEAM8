package com.scottmcclellan.xpertise;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scott.myapplication.backend.xpertiseAPI.XpertiseAPI;
import com.example.scott.myapplication.backend.xpertiseAPI.model.Profile;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.LinkedHashMap;

public class RegisterActivity extends AppCompatActivity {
    private UserRegisterTask mAuthTask = null;
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
                } else {
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

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mFirst;
        private final String mLast;
        private final String mCity;
        private final Double mLat;
        private final Double mLng;
        private final String mDes;


        UserRegisterTask () {
            mFirst = myProfile.getFirstName();
            mLast  = myProfile.getLastName();
            mEmail = myProfile.getEmail();
            mPassword = myProfile.getPassword();
            mCity = myProfile.getCity();
            mLat = myProfile.getLat();
            mLng = myProfile.getLng();
            mDes = myProfile.getDescription();

        }



      //  private void attemptRegister() {
       //     if(mFirst == null || mLast == null || mEmail == null || mPassword == null || mCity == null ||)
        //}

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Context context;
            XpertiseAPI myApiService = null;

            if(myApiService == null) {  // Only do this once
                XpertiseAPI.Builder builder = new XpertiseAPI.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://xpertiseservergae.appspot.com/_ah/api")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });

                myApiService = builder.build();
            }


            try {
                //Simulate network access.
                //Thread.sleep(2000);
                com.example.scott.myapplication.backend.xpertiseAPI.model.Profile p =  myApiService.profileAuth(mEmail, mPassword).execute();
                // List<com.example.scott.myapplication.backend.xpertiseAPI.model.Profile> profiles = myApiService.profileListAll().execute().getItems();

                //TODO:change to profile activity if p is not null. P is saved in global profile object- 'user'

              //  if(p != null) user = p;

                return p != null;

            } catch (IOException e) {
                return false;
            }


            /*
            for (Profile p : profiles) {
                String[] pieces = credential.split(":");
                if (p.getEmail().equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return p.getPassword().equals(mPassword);
                }
            }
            */

            // TODO: register the new account here.
            //Need profile details to register, switch to new activity to register
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            //mAuthTask = null;
            //showProgress(false);

           // if (success) {
                finish();
            //} else {
              //  mPasswordView.setError(getString(R.string.error_incorrect_password));
               // mPasswordView.requestFocus();
           // }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //showProgress(false);
        }
    }
}
