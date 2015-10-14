package cs307.chris.purdue.xpertise;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;


import com.example.Scott.myapplication.backend.API;
//import com.example.scott.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by tusharsharma on 10/12/15.
 */
class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private API myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            API.Builder builder = new API.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://xpertiseservergae.appspot.com/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
        }

        //TODO: use the below code snippet to define what API function you want to use

        context = params[0].first;
        String name = params[0].second;


        try {
            return myApiService.authProfile();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     *The below function receives the output from the previous API method call
     *TODO: use this to either pass things back to the GUI or perform computations on the information
    */
    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

}
