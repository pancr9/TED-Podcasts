package com.example.rekhansh.podcastapp;

import android.os.AsyncTask;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Rekhansh on 3/8/2017.
 */

public class GetPodcastsAsync extends AsyncTask<String, Void, ArrayList<Podcasts>> {

    PodcastData activity;

    public GetPodcastsAsync(PodcastData activity) {
        this.activity = activity;
    }


    @Override
    protected ArrayList<Podcasts> doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            int statusCode = httpURLConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStream in = httpURLConnection.getInputStream();
                return PodcastUtil.PodcastSAXParser.parsePodcast(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Podcasts> x) {
        activity.setUpData(x);
    }

    static public interface PodcastData {
        public void setUpData(ArrayList<Podcasts> x);
    }
}

