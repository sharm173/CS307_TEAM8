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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scott.myapplication.backend.xpertiseAPI.XpertiseAPI;
import com.example.scott.myapplication.backend.xpertiseAPI.model.MyBean;
import com.example.scott.myapplication.backend.xpertiseAPI.model.Profile;
import com.example.scott.myapplication.backend.xpertiseAPI.model.Review;
import com.example.scott.myapplication.backend.xpertiseAPI.model.ReviewCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tusharsharma on 11/11/15.
 */
public class SelectedUserActivity extends AppCompatActivity {
    private Profile profile;
    //TODO: profile object to be fetched from list

    private TextView first;
    private TextView last;
    private TextView email;
   // private TextView pass;
    private TextView city;
    //    private TextView lat;
//    private TextView lng;
    private TextView desc;
    private RatingBar rating;
    private Button submit;
    private TextView comment;
    Context context;
    private ListView lv;

    private UserSetRatingTask mAuthTask = null;
    private UserGetRatingsTask mAuthTask2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_user);
        context = this;

        profile = ListUsersActivity.selectedProf;

        first  = (TextView) findViewById(R.id.firstName);
        last = (TextView) findViewById(R.id.lastName);
        email = (TextView) findViewById(R.id.email);
        //pass = (TextView) findViewById(R.id.password);
        city = (TextView) findViewById(R.id.city);
        lv = (ListView) findViewById(R.id.listView);

        desc = (TextView) findViewById(R.id.desc);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        comment = (TextView) findViewById(R.id.editText);
       // login = (Button) findViewById(R.id.login);
        submit = (Button) findViewById(R.id.button);
        first.setText(profile.getFirstName());
        last.setText(profile.getLastName());
        email.setText(profile.getEmail());
       // pass.setText(profile.getPassword());
        city.setText(profile.getCity());
        desc.setText(profile.getDescription());

        mAuthTask2 = new UserGetRatingsTask(profile);//populate listview with ratings
        mAuthTask2.execute((Void) null);
        //call user get ratings task - populate list view inside it.
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linLay.setVisibility(View.GONE);
                int pid = profile.getPid();
                String comments;
                int rate;
                comments = comment.getText().toString();
                rate = rating.getNumStars();
                mAuthTask = new UserSetRatingTask(pid, comments, rate);
                mAuthTask.execute((Void) null);

            }
        });
            }



    public class UserSetRatingTask extends AsyncTask<Void, Void, Boolean> {
        private final String comments;
        private final int rate;
        private final int pid;

        UserSetRatingTask(int pid, String comments, int rate) {
            this.comments = comments;
            this.rate =  rate;
            this.pid = pid;
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
                MyBean b = myApiService.profilePostReview(LoginActivity.loggedInProfile.getPid(), pid, rate, comments).execute();



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


                Toast.makeText(SelectedUserActivity.this, "Review submitted", Toast.LENGTH_SHORT).show();


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

    public class UserGetRatingsTask extends AsyncTask<Void, Void, Boolean> {
        //private final String comments;
        //private final int rate;
        private final Profile reviewee;
        List<Review> userRevList;
        //private final String firstname;
        //private final String lastname;


        UserGetRatingsTask(Profile p) {
          //  comments = comment.getText().toString();
           // rate =  rating.getNumStars();
            reviewee = p;
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
                ReviewCollection ret= myApiService.profileGetReviews(reviewee.getPid()).execute();
                userRevList = ret.getItems();


                return ret != null;

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

                ListAdapter custAdapt = new ReviewListAdapter(SelectedUserActivity.this, userRevList);
                lv.setAdapter(custAdapt);

                Toast.makeText(SelectedUserActivity.this, "Reviews fetched", Toast.LENGTH_SHORT).show();
                //finish();
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
