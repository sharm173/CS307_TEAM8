package com.scottmcclellan.xpertise;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scott.myapplication.backend.xpertiseAPI.XpertiseAPI;
import com.example.scott.myapplication.backend.xpertiseAPI.model.MyBean;
import com.example.scott.myapplication.backend.xpertiseAPI.model.Profile;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {
    private UserRegisterTask mAuthTask = null;
    private EditText first;
    private EditText last;
    private EditText email;
    private EditText pass;
    private EditText city;
    private EditText description;
    private Button submit;
    int pid;
    //public static Profile myProfile = new Profile();

    private Profile profile;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;

        profile = LoginActivity.loggedInProfile;

        first = (EditText) findViewById(R.id.firstName);
        last = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        city = (EditText) findViewById(R.id.city);
        description = (EditText) findViewById(R.id.description);
        submit = (Button) findViewById(R.id.submit);

        first.setText(profile.getFirstName());
        last.setText(profile.getLastName());
        email.setText(profile.getEmail());
        pass.setText(profile.getPassword());
        city.setText(profile.getCity());
        description.setText(profile.getDescription());
        pid = profile.getPid();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check that all fields have text
                //API call
                //proceed to next activity
                if (first.getText().toString() == null || last.getText().toString() == null ||
                        email.getText().toString() == null || pass.getText().toString() == null ||
                        city.getText().toString() == null) {
                    Toast.makeText(EditProfileActivity.this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
                }
                else {

                    //TODO: API call to store profile object
                    mAuthTask = new UserRegisterTask(profile);
                    mAuthTask.execute((Void) null);



                }
            }
        });
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
            mFirst = first.getText().toString();
            mLast = last.getText().toString();
            mEmail = email.getText().toString();
            mPass = pass.getText().toString();
            mCity = city.getText().toString();
            mLat = p.getLat();// does not edit lat or lng
            mLng = p.getLng();//
            mDes = description.getText().toString();
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

                MyBean b = myApiService.profileEdit(mFirst,mLast,mPass, mEmail, mCity, mLat, mLng, mDes,pid).execute();

                //TODO:change to profile activity if p is not null. P is saved in global profile object- 'user'

                 //Log.e("First name is: ",p.getFirstName() + ", last name is: " + p.getLastName());

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
                Toast.makeText(EditProfileActivity.this, "Your profile has been successfully edited!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditProfileActivity.this, DisplayProfileActivity.class);
                startActivity(intent);


                finish();
                //     Intent i = new Intent(context, DisplayProfileActivity.class);
                //    Bundle bundle = new Bundle();
                //    bundle.putSerializable("profile", user);
                //    i.putExtras(bundle);
                //    startActivity(i);
                //    finish();

            } else {

                Toast.makeText(EditProfileActivity.this, "Profile edit failed!", Toast.LENGTH_SHORT).show();
                //password.setError(getString(R.string.error_incorrect_password));
                //password.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            // showProgress(false);
        }
    }



}
