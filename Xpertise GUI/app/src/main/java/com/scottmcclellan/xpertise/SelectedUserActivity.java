package com.scottmcclellan.xpertise;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scott.myapplication.backend.xpertiseAPI.XpertiseAPI;
import com.example.scott.myapplication.backend.xpertiseAPI.model.MyBean;
import com.example.scott.myapplication.backend.xpertiseAPI.model.Profile;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by tusharsharma on 11/11/15.
 */
public class SelectedUserActivity extends AppCompatActivity {
    private Profile profile;

    private TextView first;
    private TextView last;
    private TextView email;
    private TextView pass;
    private TextView city;
    //    private TextView lat;
//    private TextView lng;
    private TextView desc;
    private RatingBar rating;
    private Button login;
    private Button edit;
    private Button find;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        first  = (TextView) findViewById(R.id.firstName);
        last = (TextView) findViewById(R.id.lastName);
        email = (TextView) findViewById(R.id.email);
        pass = (TextView) findViewById(R.id.password);
        city = (TextView) findViewById(R.id.city);

        desc = (TextView) findViewById(R.id.desc);
        rating = (RatingBar) findViewById(R.id.ratingBar);
       // login = (Button) findViewById(R.id.login);

        first.setText(profile.getFirstName());
        last.setText(profile.getLastName());
        email.setText(profile.getEmail());
        pass.setText(profile.getPassword());
        city.setText(profile.getCity());
        desc.setText(profile.getDescription());
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
        private final String mFirst;
        private final String mLast;
        private final String mEmail;
        private final String mPass;
        private final String mCity;
        private final Double mLat;
        private final Double mLng;
        private final String mDes;


        UserRegisterTask(Profile p) {
            mFirst = p.getFirstName();
            mLast = p.getLastName();
            mEmail = p.getEmail();
            mPass = p.getPassword();
            mCity = p.getCity();
            mLat = p.getLat();
            mLng = p.getLng();
            mDes = p.getDescription();
        }

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
                        .setRootUrl("https://xpertiseservergae.appspot.com/_ah/api")
                        .setApplicationName("xpertise")
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
                MyBean b = myApiService.profilePost(mFirst,mLast,mPass, mEmail, mCity, mLat, mLng, mDes).execute();

                //TODO:change to profile activity if p is not null. P is saved in global profile object- 'user'

                // Log.e("First name is: ", p.getFirstName() + ", last name is: " + p.getLastName());

                //  if(b.getBool()) {

                // }

                return b.getBool();

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            // TODO: register the new account here.
            //Need profile details to register, switch to new activity to register
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            // mAuthTask = null;
            // showProgress(false);

            Log.e("Succes is: ", Boolean.toString(success));
//LoginActivity.this
            if (success) {
                //LinkedHashMap<String, Object> obj = new LinkedHashMap<String, Object>();
                // obj.put("hashmapkey", user);
                // Intent i = new Intent(context, LoginActivity.class);
                //       Bundle b = new Bundle();
                //     b.putSerializable("bundleobj", user);
                //   i.putExtra("profile",b);
                // startActivity(i);
            //    Toast.makeText(RegisterActivity.this, "Your profile has been successfully created!", Toast.LENGTH_SHORT).show();
              //  Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
              //  startActivity(intent);


                finish();
                //     Intent i = new Intent(context, DisplayProfileActivity.class);
                //    Bundle bundle = new Bundle();
                //    bundle.putSerializable("profile", user);
                //    i.putExtras(bundle);
                //    startActivity(i);
                //    finish();

            } else {
                //password.setError(getString(R.string.error_incorrect_password));
                //password.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
//            // showProgress(false);
        }


    }

}
