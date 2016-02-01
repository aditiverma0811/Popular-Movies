package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


public class MainFragment extends Fragment implements MovieTask.OnTaskCompleted {
    private GridView mGridView;
    private ArrayList<Movie> mMovieArrayList = new ArrayList<>();

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
       View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mGridView = (GridView)rootView.findViewById(R.id.grid_view);
        updateData();
        return rootView;
    }

    //To check setting value and call AsyncTask to get json data
    public void updateData(){
        //checking preference value
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String order = preferences.getString(getString(R.string.pref_key), getString(R.string.pref_default));
        if(Utility.checkNetwork(getActivity())){
        new MovieTask(getActivity(), order, this).execute();
        }
    }

    //Setting adapter to grid view and passing details to detail activity
    public void setAdapter(){
        mGridView.setAdapter(new ImageAdapter(getActivity(), mMovieArrayList));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Movie movie = new Movie(mMovieArrayList.get(position).getmTitle(), null,
                        mMovieArrayList.get(position).getmBack_poster(),
                        mMovieArrayList.get(position).getmSummary(),
                        mMovieArrayList.get(position).getmDate(),
                        mMovieArrayList.get(position).getmRating());
                intent.putExtra(Utility.MOVIE, movie);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SettingsActivity.REQUEST_CODE){
            updateData();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivityForResult(intent, SettingsActivity.REQUEST_CODE);
                return true;
        }
        return onOptionsItemSelected(item);
    }

   //Interface method
    @Override
    public void onTaskCompleted(ArrayList<Movie> movie) {
        mMovieArrayList = movie;
        setAdapter();
    }
}
