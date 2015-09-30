package com.example.team8.team8;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Context context;
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //this is always run when this activity is started, to start a
        //new activity call the intent of that activity.

        //Also, I would recommend GenyMotion for a good android emulator

        super.onCreate(savedInstanceState);
        //this tells the activity which xml file to use
        setContentView(R.layout.activity_main);

        context = this;

        text1 = (TextView) findViewById(R.id.text1);
        final EditText edit1 = (EditText) findViewById(R.id.edit1);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This handler is called whenever the button is pressed,
                //most things in android have a handler, or many different handlers

                Test t = new Test();

                text1.setText(edit1.getText().toString());

                //text1.setText(t.t);

                //must use "context" instead of "this" because button handler is an inner class
                //Intent i = new Intent(context, Main2Activity.class);
                //startActivity(i);
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
