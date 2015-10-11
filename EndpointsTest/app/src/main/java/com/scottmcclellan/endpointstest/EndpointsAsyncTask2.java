package com.scottmcclellan.endpointstest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.example.scott.myapplication.backend.myApi.MyApi;
import com.example.scott.myapplication.backend.myApi.model.MyBean;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Scott on 10/11/2015.
 */
public class EndpointsAsyncTask2 extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("https://endpoints-test-1095.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        context = params[0].first;

        try {
            MainActivity.beans = (ArrayList<MyBean>) myApiService.getNames().execute().getItems();
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        for (int i = 0; i < MainActivity.beans.size(); i++) {
            MyBean bean = MainActivity.beans.get(i);
            Toast.makeText(context, bean.getData(), Toast.LENGTH_LONG).show();
        }
    }
}
