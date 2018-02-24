package com.example.peter.popularmovies.model;

/**
 * Created by peter on 23/02/2018.
 * This class holds a movie object
 */

public class Movie {

    // Title of the movie
    public final String mTitle;

    // Image URL
    public final String mImageUrl;

    // Constructor
    public Movie(String title, String imageUrl) {
        mTitle = title;
        mImageUrl = imageUrl;
    }

}
