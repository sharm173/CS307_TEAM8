package cs307.chris.purdue.xpertise;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Scott.myapplication.backend.Profile;

import org.w3c.dom.Text;

public class Registration extends AppCompatActivity {


    Button submit;
    EditText FirstName;
    EditText LastName;
    EditText Email;
    EditText Password;
    EditText City;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirstName = (EditText) findViewById(R.id.FirstName);
        LastName = (EditText) findViewById(R.id.LastName);
        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        City = (EditText) findViewById(R.id.City);
        submit = (Button) findViewById(R.id.Submit);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                Profile temp = new Profile();
                temp.setCity(City.getText().toString());
                temp.setEmail(Email.getText().toString());
                temp.setPassword(Password.getText().toString());
                temp.setFirstName(FirstName.getText().toString());
                temp.setLastName(LastName.getText().toString());
                temp.setLat(0.00);
                temp.setLng(0.00);
                temp.setDescription("none");

                if(temp.getCity() == null || temp.getEmail() == null || temp.getPassword() == null
                        || temp.getFirstName() == null || temp.getLastName() == null){
                    Toast.makeText(Registration.this, "Please fill out all fields.", Toast.LENGTH_LONG).show();

                }
                else {

                    //TODO: INVOKE API CALL
                    Intent intention = new Intent(Registration.this, LoginActivity.class);
                    startActivity(intention);
                }
            }
        });

    }

}
