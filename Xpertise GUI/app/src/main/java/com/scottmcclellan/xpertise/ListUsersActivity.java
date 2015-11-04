package com.scottmcclellan.xpertise;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


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

    Button radiusButton;
    Button cityButton;
    Button getCityButton;
    EditText cityText;
    LinearLayout linLay;
    Context context;
    ListView userList;

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
                try {
                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(loc != null) {
                        double lat = loc.getLatitude();
                        double longe = loc.getLongitude();
                        //query database for users within radius
                        //populate listview
                    }
                    else{
                        //we gotsa problem, no location available, async thread required
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
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
}
