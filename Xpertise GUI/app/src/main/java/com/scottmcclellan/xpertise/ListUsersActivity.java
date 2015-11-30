package com.scottmcclellan.xpertise;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.scott.myapplication.backend.xpertiseAPI.XpertiseAPI;
import com.example.scott.myapplication.backend.xpertiseAPI.model.MyBean;
import com.example.scott.myapplication.backend.xpertiseAPI.model.MyBeanCollection;
import com.example.scott.myapplication.backend.xpertiseAPI.model.Profile;
import com.example.scott.myapplication.backend.xpertiseAPI.model.ProfileCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/*
    Need to add search bar at the top of the function
    Drop down to select what field to search on?

    - Name?
    - City
    - Tags
    - Rating?

    For now just has city and radius

 */


public class ListUsersActivity extends AppCompatActivity {


    Context context;
    ListView userList;
    private UserListTask mAuthTask = null;
    private UserListCityTask mAuthTask2 = null;
    private UserListTagTask mAuthTask3 = null;
    List<Profile> profiles;

    public static Profile selectedProf = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        context = this;

        userList = (ListView) findViewById(R.id.userList);


        Bundle b = getIntent().getExtras();
        String data = b.getString("data");
        String type = b.getString("type");

        if(type.equals("tag")){
//            int pid = LoginActivity.loggedInProfile.getPid();
//            mAuthTask3 = new UserListTagTask(pid, data);
//            mAuthTask3.execute((Void) null);

            int pid = LoginActivity.loggedInProfile.getPid();
            mAuthTask2 = new UserListCityTask(pid, data);
            mAuthTask2.execute((Void) null);
        }
        else if(type.equals("radius")){
            int pid = LoginActivity.loggedInProfile.getPid(); // TODO:change to selected users profile
            double rad = Double.parseDouble(data);
            mAuthTask = new UserListTask(pid,rad);
            mAuthTask.execute((Void) null);
        }
        else{ //city
            int pid = LoginActivity.loggedInProfile.getPid();
            mAuthTask2 = new UserListCityTask(pid, data);
            mAuthTask2.execute((Void) null);
        }

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedProf = profiles.get(position);
                Intent i = new Intent(context, SelectedUserActivity.class);
                startActivity(i);
            }
        });

    }


    public class UserListTask extends AsyncTask<Void, Void, Boolean> {
        private final int mPid;
        private final Double mRad;

        UserListTask(int pid, double rad) {
            mPid = pid;
            mRad = rad;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
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
                ProfileCollection b = myApiService.profileRadius(mPid, mRad).execute(); //TODO: API call is returning Profile collection instead of arraylist
                profiles = b.getItems();

                return b != null;

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (!success) {
                Toast.makeText(ListUsersActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
            else{
                ListAdapter custAdapter = new ProfileListAdapter(context, profiles);
                userList.setAdapter(custAdapter);
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }


    public class UserListCityTask extends AsyncTask<Void, Void, Boolean> {
        private final int mPid;
        private final String city;

        UserListCityTask(int pid, String city) {
            mPid = pid;
            this.city = city;
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
                ProfileCollection b = myApiService.profileCity(mPid, city).execute(); //TODO: API call is returning Profile collection instead of arraylist
                profiles = b.getItems();

                return b != null;

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            // TODO: register the new account here.
            //Need profile details to register, switch to new activity to register
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (!success) {
                Toast.makeText(ListUsersActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
            else{
                ListAdapter custAdapter = new ProfileListAdapter(context, profiles);
                userList.setAdapter(custAdapter);
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask2 = null;
        }
    }


    public class UserListTagTask extends AsyncTask<Void, Void, Boolean> {
        private final int mPid;
        private final String mTag;

        UserListTagTask(int pid, String tag) {
            mPid = pid;
            mTag = tag;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
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


//            try {
//
//                //TODO
//                ProfileCollection b = myApiService.ProfileSearchTag(mTag).execute();
//                profiles = b.getItems();
//
//                return b != null;
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                return false;
//            }
            return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (!success) {
                Toast.makeText(ListUsersActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
            else{
                ListAdapter custAdapter = new ProfileListAdapter(context, profiles);
                userList.setAdapter(custAdapter);
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask3 = null;
        }
    }


}
