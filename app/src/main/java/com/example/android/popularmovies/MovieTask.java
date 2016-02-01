package com.example.android.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


//Performing network operations
public class MovieTask extends AsyncTask<Void,Void,String> {
    private Context mContext;
    private String mOrder;
    private OnTaskCompleted mListener;

    //Interface to send data back to activity
    public interface OnTaskCompleted{
         void onTaskCompleted(ArrayList<Movie> movie);
    }

    public MovieTask(Context context, String order, OnTaskCompleted listener){
        mContext = context;
        mOrder = order;
        this.mListener = listener;

    }

    @Override
    protected String doInBackground(Void... params) {
        String api_key = "api_key";
        HttpURLConnection connection;
        BufferedReader reader;
        Uri uri;
        try {
            if (mOrder.equalsIgnoreCase(mContext.getString(R.string.pref_default))) {
                uri = Uri.parse(Utility.POPULAR_URL).buildUpon()
                        .appendQueryParameter(api_key, mContext.getString(R.string.API_KEY))
                        .build();
            } else {
                uri = Uri.parse(Utility.RATING_URL).buildUpon()
                        .appendQueryParameter(api_key, mContext.getString(R.string.API_KEY))
                        .build();
            }
            URL url = new URL(uri.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            connection.disconnect();
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(String jsonStr) {
        super.onPostExecute(jsonStr);
        String results = "results";
        //array list for movie object
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        //Extract data from json
        try {
            JSONObject object = new JSONObject(jsonStr);
            JSONArray array = object.getJSONArray(results);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.getJSONObject(i);
                //creating movie object
                Movie movie = new Movie(object1.getString("original_title"),
                        (Utility.IMAGE_CONSTANT + object1.getString("poster_path")),
                        (Utility.IMAGE_CONSTANT + object1.getString("backdrop_path")),
                        object1.getString("overview"),
                        object1.getString("release_date"),
                        object1.getDouble("vote_average"));
                movieArrayList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            mListener.onTaskCompleted(movieArrayList);
        }
    }
}