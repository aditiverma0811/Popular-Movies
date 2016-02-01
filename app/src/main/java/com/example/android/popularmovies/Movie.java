package com.example.android.popularmovies;


import android.os.Parcel;
import android.os.Parcelable;

/*
* Class to contain behavior and functionaly of movie objects
* */

public class Movie implements Parcelable {
    //private member variables
    private String mTitle;
    private String mPoster;
    private String mBack_poster;
    private String mSummary;
    private String mDate;
    private Double mRating;

    //Movie constructor used by parcelable
    protected Movie(Parcel in) {
        mTitle = in.readString();
        mBack_poster = in.readString();
        mSummary = in.readString();
        mDate = in.readString();
        mRating = in.readByte() == 0x00 ? null : in.readDouble();
    }

    //Movie constructor
    public Movie(String mTitle, String mPoster, String mBack_poster, String mSummary, String mDate, Double mRating) {
        this.mTitle = mTitle;
        this.mPoster = mPoster;
        this.mBack_poster = mBack_poster;
        this.mSummary = mSummary;
        this.mDate = mDate;
        this.mRating = mRating;

    }

    //Getter method for mTitle
    public String getmTitle(){ return mTitle; }
    //Getter method for mPoster
    public String getmPoster(){ return mPoster; }
    //Getter method for mBack_poster
    public String getmBack_poster(){ return mBack_poster; }
    //Getter method for mSummary
    public String getmSummary(){ return mSummary; }
    //Getter method for mDate
    public String getmDate(){ return mDate; }
    //Getter method for mRating
    public Double getmRating(){ return mRating; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mBack_poster);
        dest.writeString(mSummary);
        dest.writeString(mDate);
        if(mRating ==null){
            dest.writeByte((byte)(0));
        }else{
            dest.writeByte((byte)(1));
            dest.writeDouble(mRating);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){


        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
