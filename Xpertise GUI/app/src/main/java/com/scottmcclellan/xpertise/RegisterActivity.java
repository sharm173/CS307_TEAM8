package com.scottmcclellan.xpertise;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
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
import java.util.List;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    private UserRegisterTask mAuthTask = null;
    private EditText first;
    private EditText last;
    private EditText email;
    private EditText pass;
    private EditText city;
    private EditText description;
    private Button submit;
    private double loclong =0.0;
    private double loclat =0.0;
    public static Profile myProfile = new Profile();
    Context context;
    boolean flag;
    private LocationManager locationManager=null;
    private LocationListener locationListener=null;

    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;

        first = (EditText) findViewById(R.id.firstName);
        last = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        city = (EditText) findViewById(R.id.city);
        description = (EditText) findViewById(R.id.description);
        submit = (Button) findViewById(R.id.submit);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        flag = displayGpsStatus();
        if (flag) {

            //Log.v(TAG, "onClick");

            //editLocation.setText("Please!! move your device to"+
              //      " see the changes in coordinates."+"\nWait..");

           // pb.setVisibility(View.VISIBLE);
            locationListener = new MyLocationListener();
            try {
                locationManager.requestLocationUpdates(LocationManager
                        .GPS_PROVIDER, 5000, 10, locationListener);
            }
            catch (SecurityException e) {

            }
        } else {
            //alertbox("Gps Status!!", "Your GPS is: OFF");
        }


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
                    myProfile.setLat(loclong);
                    myProfile.setLng(loclat);
                    myProfile.setDescription(description.getText().toString());
                    //TODO: API call to store profile object
                    mAuthTask = new UserRegisterTask(myProfile);
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
                Toast.makeText(RegisterActivity.this, "Your profile has been successfully created!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);


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
            mAuthTask = null;
            // showProgress(false);
        }
    }

    /*---------- Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
          //  editLocation.setText("");
          //  pb.setVisibility(View.INVISIBLE);
            loclong = loc.getLongitude();
            loclat = loc.getLatitude();
            Toast.makeText(
                    getBaseContext(),
                    "Location changed: Lat: " + loclat + " Lng: "
                            + loclong, Toast.LENGTH_SHORT).show();


            //String longitude = "Longitude: " + loc.getLongitude();
            //Log.v(TAG, longitude);
            //String latitude = "Latitude: " + loc.getLatitude();
            //Log.v(TAG, latitude);



          //  String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
            //        + cityName;
           // editLocation.setText(s);
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }



}
