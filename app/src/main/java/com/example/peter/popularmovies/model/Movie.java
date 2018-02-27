package com.example.peter.popularmovies.model;

/**
 * Created by peter on 23/02/2018.
 * This class holds a movie object
 */

public class Movie {

    // Title of the movie
    private String mTitle;

    // Image URL
    private String mPosterUrl;

    /**
     * Constructor for a Movie object
     *
     * @param title     - The title of the movie
     * @param posterURL The endpoint of the poster location. This needs to be completed
     *                  before it can point to the server
     */
    public Movie(String title, String posterURL) {
        mTitle = title;
        mPosterUrl = posterURL;
    }

    /* Returns the movie title */
    public String getTitle() {
        return mTitle;
    }

    /* Returns the movie poster image Url */
    public String getMoviePosterUrl() {
        return mPosterUrl;
    }
}
