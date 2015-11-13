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
    //private ListView lv;
    Button radiusButton;
    Button cityButton;
    Button getCityButton;
    EditText cityText;
    LinearLayout linLay;
    Context context;
    ListView userList;
    private UserListTask mAuthTask = null;
    private UserListCityTask mAuthTask2 = null;
    List<Profile> profiles;

    public static Profile selectedProf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        context = this;

        radiusButton = (Button) findViewById(R.id.radiusButton);
        cityButton = (Button) findViewById(R.id.cityButton);
        getCityButton = (Button) findViewById(R.id.citySearchButton);
        cityText = (EditText) findViewById(R.id.cityEdit);
        linLay = (LinearLayout) findViewById(R.id.cityInput);
        userList = (ListView) findViewById(R.id.userList);

        radiusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linLay.setVisibility(View.GONE);
                int pid = LoginActivity.loggedInProfile.getPid(); // TODO:change to selected users profile
                //make the radius call using pid and some radius
                double rad = 100.00; //hardcoded for now
                mAuthTask = new UserListTask(pid,rad);
                mAuthTask.execute((Void) null);

            }
        });

        cityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linLay.setVisibility(View.VISIBLE);

            }
        });

        getCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityText.getText().toString();
                if(city.length() != 0){
                    int pid = LoginActivity.loggedInProfile.getPid();
                    mAuthTask2 = new UserListCityTask(pid, city);
                    mAuthTask2.execute((Void) null);
                }
                else {
                    Toast.makeText(context, "Please enter a city", Toast.LENGTH_LONG).show();
                }
            }
        });

//        userList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedProf = profiles.get(position);
//                Intent i = new Intent(context, SelectedUserActivity.class);
//                startActivity(i);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedProf = profiles.get(position);
                Intent i = new Intent(context, SelectedUserActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_users, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

            if (success) {
                setTagsTask tagsTask = new setTagsTask(0);
                tagsTask.execute((Void) null);

            } else {
                Toast.makeText(ListUsersActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
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

            if (success) {
                setTagsTask tagsTask = new setTagsTask(0);
                tagsTask.execute((Void) null);
            } else {
                Toast.makeText(ListUsersActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }


    public class setTagsTask extends AsyncTask<Void, Void, Boolean> {

        Profile prof;
        int count;

        setTagsTask(int count) {
            prof = profiles.get(count);
            this.count = count;
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
                MyBeanCollection beanList = myApiService.profileGetTags(prof.getPid()).execute();
                List<MyBean> beans = beanList.getItems();
                prof.setTags(beans);

                return true;

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(count != (profiles.size() - 1)){
                setTagsTask tagsTask = new setTagsTask(count + 1);
                tagsTask.execute((Void) null);
            }
            else{
                userList = (ListView) findViewById(R.id.userList);
                ListAdapter custAdapt = new ProfileListAdapter(context, profiles);
                userList.setAdapter(custAdapt);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
