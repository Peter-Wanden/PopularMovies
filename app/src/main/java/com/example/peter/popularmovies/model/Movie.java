package com.example.peter.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by peter on 23/02/2018.
 * This class holds a movie object and implements parcelable so we can move instances of it
 * between classes and saved instance state.
 * @link hhttps://plus.google.com/u/0/events/cfftk1qo4tjn7enecof6f9oes0o?authkey=CNu5uui-k5qAtQE
 *
 */
public class Movie implements Parcelable {

    /* Parcelable CREATOR */
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        /* This calls the private 'Movie(Parcel in)' constructor and passes along the Parcel
         * and returns a new object.
         */
        @Override
        public Movie createFromParcel(Parcel in) {
                return new Movie(in);
        }

        /* Can pass an array of this class */
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    // The movies ID.
    private final int mId;
    // Title of the movie.
    private final String mTitle;
    // The movies original title.
    private final String mOriginalTitle;
    // The movies poster image URL.
    private final String mImagePosterPath;
    // The movies backdrop image URL.
    private final String mImageBackdropPath;
    // The movies synopsis.
    private final String mSynopsis;
    // The movies user rating.
    private final double mUserRating;
    // The movies release date.
    private final String mMovieReleaseDate;

    /**
     * Constructor for a Movie object.
     * @param Id                The ID of the movie.
     * @param title             The title of the movie.
     * @param originalTitle     The movies original title.
     * @param posterURL         The endpoint of the poster location. This needs to be completed
     *                          before it can point to the server.
     * @param backDropUrl       The movies backdrop
     * @param synopsis          The movies description
     * @param userRating        A rating out of 10
     * @param movieReleaseDate  The Year the movie was released
     */
    public Movie(int Id, String title, String originalTitle, String posterURL, String backDropUrl,
                 String synopsis, double userRating, String movieReleaseDate) {
        mId = Id;
        mTitle = title;
        mOriginalTitle = originalTitle;
        mImagePosterPath = posterURL;
        mImageBackdropPath = backDropUrl;
        mSynopsis = synopsis;
        mUserRating = userRating;
        mMovieReleaseDate = movieReleaseDate;
    }

    /* This is where we retrieve the values that were originally written to the parcel.
     * This constructor is usually private so that only the CREATOR can access it.
     */
    private Movie(Parcel in) {
        this.mId = in.readInt();
        this.mTitle = in.readString();
        this.mOriginalTitle = in.readString();
        this.mImagePosterPath = in.readString();
        this.mImageBackdropPath = in.readString();
        this.mSynopsis = in.readString();
        this.mUserRating = in.readDouble();
        this.mMovieReleaseDate = in.readString();
    }

    /* Returns the movie title */
    public String getTitle() {
        return mTitle;
    }

    /* Returns the movies original title */
    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    /* Returns the movie poster image Url path */
    public String getPosterImagePath() {
        return mImagePosterPath;
    }

    /* Returns the movie backdrop image Url path */
    public String getBackdropImagePath() {
        return mImageBackdropPath;
    }

    /* Returns the movies synopsis */
    public String getMovieSynopsis() {
        return mSynopsis;
    }

    /* Returns the movies user rating */
    public double getUserRating() {
        return mUserRating;
    }

    /* Returns the movies release date */
    public String getMovieReleaseDate() {
        return mMovieReleaseDate;
    }

    /* Used by Parcelable */
    @Override
    public int describeContents() {
        return 0;
    }

    /* This is where we write the values we want to save to the parcel */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mId);
        out.writeString(mTitle);
        out.writeString(mOriginalTitle);
        out.writeString(mImagePosterPath);
        out.writeString(mImageBackdropPath);
        out.writeString(mSynopsis);
        out.writeDouble(mUserRating);
        out.writeString(mMovieReleaseDate);
    }
}
