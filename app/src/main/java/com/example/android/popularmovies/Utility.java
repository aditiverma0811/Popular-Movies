package com.example.android.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

//Class containing other useful methods and variables

public class Utility {
    //constants for downloadng data from api
    public static final String POPULAR_URL = "http://api.themoviedb.org/3/movie/popular?";
    public static final String RATING_URL = "http://api.themoviedb.org/3/movie/top_rated?";
    public static final String IMAGE_CONSTANT = "http://image.tmdb.org/t/p/w185/";
    //constants for intents
    public static final String MOVIE = "movie";

    //To check network state i.e connected or not
    public static boolean checkNetwork(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(MainActivity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
