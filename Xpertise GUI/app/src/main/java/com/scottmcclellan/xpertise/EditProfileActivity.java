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
import com.example.scott.myapplication.backend.xpertiseAPI.model.MyBeanCollection;
import com.example.scott.myapplication.backend.xpertiseAPI.model.Profile;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {
    private UserUpdateTask mAuthTask = null;
    private EditText first;
    private EditText last;
    private EditText email;
    private EditText pass;
    private EditText city;
    private EditText description;
    //public EditText editTags;
    private Button submit;
    //public static Profile myProfile = new Profile();
    private int pid;
    private Profile profile;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        context = this;

        final EditText tagEdit;

        profile = LoginActivity.loggedInProfile;

        first = (EditText) findViewById(R.id.firstName);
        last = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        city = (EditText) findViewById(R.id.city);
        description = (EditText) findViewById(R.id.description);
        tagEdit = (EditText) findViewById(R.id.tag);
        submit = (Button) findViewById(R.id.submit);

        first.setText(profile.getFirstName());
        last.setText(profile.getLastName());
        email.setText(profile.getEmail());
        pass.setText(profile.getPassword());
        city.setText(profile.getCity());
        description.setText(profile.getDescription());


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check that all fields have text
                //API call
                //proceed to next activity
                if ((first.getText().toString().length() == 0) && (last.getText().toString().length() == 0) &&
                        (email.getText().toString().length() == 0) && (pass.getText().toString().length() == 0) &&
                        (city.getText().toString().length() == 0) && (tagEdit.getText().toString().length() == 0)) {
                    Toast.makeText(EditProfileActivity.this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    profile.setFirstName(first.getText().toString());
                    profile.setLastName(last.getText().toString());
                    profile.setEmail(email.getText().toString());
                    profile.setPassword(pass.getText().toString());
                    profile.setCity(city.getText().toString());
                    profile.setDescription(description.getText().toString());
                    pid = profile.getPid();
                    if(tagEdit.getText().toString().length() != 0) {
                        String newTag = tagEdit.getText().toString();
                        if (newTag.length() != 0) {
                            setTagsTask tagsTask = new setTagsTask(newTag);
                            tagsTask.execute((Void) null);
                        }
                    }
                    else {
                        mAuthTask = new UserUpdateTask(profile);
                        mAuthTask.execute((Void) null);
                    }

                }
            }
        });
    }

    public class setTagsTask extends AsyncTask<Void, Void, Boolean> {

        String tag;

        setTagsTask(String tag) {
            this.tag = tag;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            XpertiseAPI myApiService = null;

            if(myApiService == null) {  // Only do this once
                XpertiseAPI.Builder builder = new XpertiseAPI.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
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
                myApiService.profileSetTag(LoginActivity.loggedInProfile.getPid(), tag).execute();

                return true;

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            getTagsTask tagsTask = new getTagsTask();
            tagsTask.execute((Void) null);
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            // showProgress(false);
        }
    }

    public class getTagsTask extends AsyncTask<Void, Void, Boolean> {

        getTagsTask() {}

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            XpertiseAPI myApiService = null;

            if(myApiService == null) {  // Only do this once
                XpertiseAPI.Builder builder = new XpertiseAPI.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
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
                MyBeanCollection beanList = myApiService.profileGetTags(profile.getPid()).execute();
                List<MyBean> beans = beanList.getItems();
                profile.setTags(beans);

                return true;

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = new UserUpdateTask(profile);
            mAuthTask.execute((Void) null);
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            // showProgress(false);
        }
    }

    public class UserUpdateTask extends AsyncTask<Void, Void, Boolean> {

        private final String mFirst;
        private final String mLast;
        private final String mEmail;
        private final String mPass;
        private final String mCity;
        private final Double mLat;
        private final Double mLng;
        private final String mDes;
       // private final int pid;


        UserUpdateTask(Profile p) {
            mFirst = profile.getFirstName();
            mLast = profile.getLastName();
            mEmail = profile.getEmail();
            mPass = profile.getPassword();
            mCity = profile.getCity();
            mLat = profile.getLat();// does not edit lat or lng
            mLng = profile.getLng();//
            mDes = profile.getDescription();
            pid = profile.getPid();
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
                Log.e("First: ", mFirst);
                Log.e("Pid: ", String.valueOf(pid));
                MyBean b = myApiService.profileEdit(mFirst,mLast,mPass, mEmail, mCity, mLat, mLng, mDes,pid).execute();

                //TODO:change to profile activity if p is not null. P is saved in global profile object- 'user'

                 //Log.e("First name is: ",p.getFirstName() + ", last name is: " + p.getLastName());

                //  if(b.getBool()) {

                // }

                Log.e("Success is: ", Boolean.toString(b.getBool()));
                Log.e("Data is: ", b.getData());

                return b.getBool();

            } catch (IOException e) {
                Log.e("Success???", "IOException");
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
