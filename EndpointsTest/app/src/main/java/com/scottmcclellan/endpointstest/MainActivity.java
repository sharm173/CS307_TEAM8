package com.scottmcclellan.endpointstest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.scott.myapplication.backend.myApi.model.MyBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private Button helloButton;
    private Button listButton;
    private ListView nameList;
    public static List<String> names;
    ArrayAdapter<String> arrayAdapter;
    int first = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.nameField);
        helloButton = (Button) findViewById(R.id.helloButton);
        listButton = (Button) findViewById(R.id.listButton);
        nameList = (ListView) findViewById(R.id.listNamesView);

        helloButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EndpointsAsyncTask().execute(new Pair<Context, String>(MainActivity.this, name.getText().toString()));
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                names = new ArrayList<String>();
                new EndpointsAsyncTask2().execute(new Pair<Context, String>(MainActivity.this, null));
                try {
                    TimeUnit.SECONDS.sleep(2);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (first == 0) {
                    arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, names);
                }
                else arrayAdapter.notifyDataSetChanged();
                nameList.setAdapter(arrayAdapter);
                Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
