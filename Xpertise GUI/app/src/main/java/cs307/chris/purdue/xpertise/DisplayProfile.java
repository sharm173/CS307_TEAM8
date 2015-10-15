package cs307.chris.purdue.xpertise;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.Scott.myapplication.backend.Profile;

import java.util.HashMap;

public class DisplayProfile extends AppCompatActivity {

    Button Return;
    TextView FirstName;
    TextView LastName;
    TextView Password;
    TextView Email;
    TextView City;
    TextView Latitude;
    TextView Longitude;
    TextView Description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Profile temp = new Profile();
        //TODO INVOKE API CALL, call method Profile_AUTH, store return in profile object (variables above)
        try {
            setContentView(R.layout.activity_display_profile);
            Bundle bn = new Bundle();
            bn = getIntent().getExtras();
            HashMap<String, Object> getobj = new HashMap<String, Object>();
            getobj = (HashMap<String, Object>) bn.getSerializable("bundleobj");
            temp = (Profile) getobj.get("hashmapkey");
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }
        FirstName.setText(temp.getFirstName());
        LastName.setText(temp.getLastName());
        Password.setText(temp.getPassword());
        Email.setText(temp.getEmail());
        City.setText(temp.getCity());
        Description.setText(temp.getDescription());
        Latitude.setText(Double.toString(temp.getLat()));
        Longitude.setText(Double.toString(temp.getLng()));
        Return.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                Intent intention = new Intent(DisplayProfile.this, LoginActivity.class);
                startActivity(intention);
            }

        });
    }

}
