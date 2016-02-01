package com.example.android.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //checking preference value
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String order = preferences.getString(getString(R.string.pref_key), getString(R.string.pref_default));
        //setting action bar title
        if (getSupportActionBar() != null) {
            if (order.equalsIgnoreCase(getString(R.string.pref_default))) {
                getSupportActionBar().setTitle(getString(R.string.popular_title));
            } else {
                getSupportActionBar().setTitle(getString(R.string.rated_title));
            }
        }
    }
}
