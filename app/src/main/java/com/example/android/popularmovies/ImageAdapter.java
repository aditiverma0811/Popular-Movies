package com.example.android.popularmovies;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

// Adapter to bind images to grid view

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Movie> movieArrayList;

    public ImageAdapter(Context context, ArrayList<Movie> movies) {
        mContext = context;
        movieArrayList = movies;
        notifyDataSetChanged();
    }

    //View holder class to make scrolling smooth
    static class ViewHolder{
        @Bind(R.id.image_view)ImageView imageView;
        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getCount() {
        return movieArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieArrayList.get(position).getmPoster();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater = ((MainActivity)mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.image_view, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        Picasso.with(mContext).load(movieArrayList.get(position).getmPoster()).into(holder.imageView);
        return convertView;
    }

}
