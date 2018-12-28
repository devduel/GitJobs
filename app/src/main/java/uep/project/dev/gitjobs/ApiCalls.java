package uep.project.dev.gitjobs;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiCalls extends AsyncTask<String, Void, Void> {
    String receivedData = "";
    List<JobOffer> jobOffers = new ArrayList<>();

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL apiURL = new URL("https://jobs.github.com/positions.json?description=python&full_time=true&location=sf");
            HttpURLConnection httpURLConnection = (HttpURLConnection) apiURL.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";

            while (line != null) {
                line = bufferedReader.readLine();
                receivedData += line;
            }

            jobOffers = ParseJobOfferJSON.parseFrom(receivedData);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("GitJobs", Integer.toString(jobOffers.size()));
    }
}
