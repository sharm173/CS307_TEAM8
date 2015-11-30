package com.scottmcclellan.xpertise;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    Button tagButton;
    Button radiusButton;
    Button cityButton;
    Button searchButton;

    LinearLayout tagLay;
    LinearLayout radiusLay;
    LinearLayout cityLay;

    EditText tagText;
    EditText radiusText;
    EditText cityText;

    boolean isTag = false;
    boolean isRadius = false;
    boolean isCity = false;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = this;

        tagButton = (Button) findViewById(R.id.tag_button);
        radiusButton = (Button) findViewById(R.id.radius_button);
        cityButton = (Button) findViewById(R.id.city_button);
        searchButton = (Button) findViewById(R.id.search_button);

        tagLay = (LinearLayout) findViewById(R.id.tag_lin);
        radiusLay = (LinearLayout) findViewById(R.id.radius_lin);
        cityLay = (LinearLayout) findViewById(R.id.city_lin);

        tagText = (EditText) findViewById(R.id.tag_text);
        radiusText = (EditText) findViewById(R.id.radius_text);
        cityText = (EditText) findViewById(R.id.city_text);


        tagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTag = true;
                isRadius = false;
                isCity = false;
                setVis();
            }
        });

        radiusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTag = false;
                isRadius = true;
                isCity = false;
                setVis();
            }
        });

        cityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTag = false;
                isRadius = false;
                isCity = true;
                setVis();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type;
                String data;

                if(isTag){
                    type = "tag";
                    data = tagText.getText().toString();
                }
                else if(isRadius){
                    type = "radius";
                    data = radiusText.getText().toString();
                }
                else{
                    type = "city";
                    data = cityText.getText().toString();
                }

                if(data.trim().length() == 0){
                    Toast.makeText(getApplicationContext(), "Please enter search criteria",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = new Intent(context, ListUsersActivity.class);
                    Bundle b = new Bundle();
                    b.putString("type", type);
                    b.putString("data", data);
                    i.putExtras(b);
                    startActivity(i);
                }
            }
        });

    }

    private void setVis(){
        tagLay.setVisibility(isTag ? View.VISIBLE : View.INVISIBLE);
        radiusLay.setVisibility(isRadius ? View.VISIBLE : View.INVISIBLE);
        cityLay.setVisibility(isCity ? View.VISIBLE : View.INVISIBLE);
        searchButton.setVisibility(View.VISIBLE);
    }

}
