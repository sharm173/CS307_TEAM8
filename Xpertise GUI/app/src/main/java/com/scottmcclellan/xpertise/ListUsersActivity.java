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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.scott.myapplication.backend.xpertiseAPI.XpertiseAPI;
import com.example.scott.myapplication.backend.xpertiseAPI.model.MyBean;
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
    private ListView lv;
    Button radiusButton;
    Button cityButton;
    Button getCityButton;
    EditText cityText;
    LinearLayout linLay;
    Context context;
    //ListView userList;
    private UserListTask mAuthTask = null;
    private UserListCityTask mAuthTask2 = null;
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
        //userList = (ListView) findViewById(R.id.userList);

        radiusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linLay.setVisibility(View.GONE);
                int pid = LoginActivity.loggedInProfile.getPid();
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
                int pid = LoginActivity.loggedInProfile.getPid();
                mAuthTask2 = new UserListCityTask(pid);
                mAuthTask2.execute((Void) null);
            }
        });

        getCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityText.getText().toString();
                if(city.length() != 0){
                    //query database for cities
                    //populate listview
                }
                else {
                    Toast.makeText(context, "Please enter a city", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_users, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class UserListTask extends AsyncTask<Void, Void, Boolean> {
        private final int mPid;
        private final Double mRad;
        List<Profile> userRadList;



        UserListTask(int pid, double rad) {
            mPid = pid;
            mRad = rad;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
           // Context context = ListUsersActivity.this;
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
                ProfileCollection b = myApiService.profileRadius(mPid, mRad).execute(); //TODO: API call is returning Profile collection instead of arraylist
                userRadList = b.getItems();

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

            Log.e("Succes is: ", Boolean.toString(success));

            if (success) {

               // ListAdapter listAdapter = new ArrayAdapter<Profile>(this, R.layout.user_list, userRadList);
               // Toast.makeText(RegisterActivity.this, "Your profile has been successfully created!", Toast.LENGTH_SHORT).show();
                lv = (ListView) findViewById(R.id.userList);
                ArrayAdapter<Profile> arrayAdapter = new ArrayAdapter<Profile>(
                        ListUsersActivity.this,
                        android.R.layout.simple_list_item_1,
                        userRadList);

                lv.setAdapter(arrayAdapter);
                Toast.makeText(ListUsersActivity.this, "List view should be populated", Toast.LENGTH_SHORT).show();

                // Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
               // startActivity(intent);


                //finish();


            } else {
                //password.setError(getString(R.string.error_incorrect_password));
                //password.requestFocus();
                Toast.makeText(ListUsersActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            // showProgress(false);
        }
    }
    public class UserListCityTask extends AsyncTask<Void, Void, Boolean> {
        private final int mPid;
       // private final Double mRad;
        List<Profile> userRadList;



        UserListCityTask(int pid) {
            mPid = pid;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            // Context context = ListUsersActivity.this;
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
                ProfileCollection b = myApiService.profileCity(mPid).execute(); //TODO: API call is returning Profile collection instead of arraylist
                userRadList = b.getItems();

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

            Log.e("Succes is: ", Boolean.toString(success));

            if (success) {

                // ListAdapter listAdapter = new ArrayAdapter<Profile>(this, R.layout.user_list, userRadList);
                // Toast.makeText(RegisterActivity.this, "Your profile has been successfully created!", Toast.LENGTH_SHORT).show();
                lv = (ListView) findViewById(R.id.userList);
                ArrayAdapter<Profile> arrayAdapter = new ArrayAdapter<Profile>(
                        ListUsersActivity.this,
                        android.R.layout.simple_list_item_1,
                        userRadList);

                lv.setAdapter(arrayAdapter);
                Toast.makeText(ListUsersActivity.this, "List view should be populated", Toast.LENGTH_SHORT).show();

                // Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                // startActivity(intent);


               // finish();


            } else {
                //password.setError(getString(R.string.error_incorrect_password));
                //password.requestFocus();
                Toast.makeText(ListUsersActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            // showProgress(false);
        }
    }
}
