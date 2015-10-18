package com.scottmcclellan.xpertise;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scott.myapplication.backend.xpertiseAPI.XpertiseAPI;
import com.example.scott.myapplication.backend.xpertiseAPI.model.Profile;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.LinkedHashMap;

public class LoginActivity extends AppCompatActivity {

    private UserLoginTask mAuthTask = null;
    Profile user;
    private EditText email;
    private EditText password;
    private Button login;
    private Button register;
    public static int loginSuccess = 0;
    public static Profile loggedInProfile;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile temp = new Profile();
                //TODO: AsyncTask call to access profile_auth method in API
                String myemail = email.getText().toString();
                String mypassword = password.getText().toString();

                mAuthTask = new UserLoginTask(myemail, mypassword);
                mAuthTask.execute((Void) null);



                //loginSuccess = 1;
                //loggedInProfile = temp;
                //Intent intent = new Intent(LoginActivity.this, DisplayProfileActivity.class);
                //startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;


        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
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
                com.example.scott.myapplication.backend.xpertiseAPI.model.Profile p = myApiService.profileAuth(mEmail, mPassword).execute();

                //TODO:change to profile activity if p is not null. P is saved in global profile object- 'user'

                Log.e("First name is: ", p.getFirstName() + ", last name is: " + p.getLastName());

                if(p != null) {
                    user = p;
                    loginSuccess = 1;
                }

                return p != null;

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

            if (success) {
//                LinkedHashMap<String, Object> obj = new LinkedHashMap<String, Object>();
//                obj.put("hashmapkey", user);
//                Intent i = new Intent(LoginActivity.this, DisplayProfileActivity.class);
//                Bundle b = new Bundle();
//                b.putSerializable("bundleobj", obj);
//                i.putExtras(b);
//                startActivity(i);
//                finish();
                Intent i = new Intent(context, DisplayProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("profile", user);
                i.putExtras(bundle);
                startActivity(i);
                finish();

            } else {
                password.setError(getString(R.string.error_incorrect_password));
                password.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
           // showProgress(false);
        }
    }


}
