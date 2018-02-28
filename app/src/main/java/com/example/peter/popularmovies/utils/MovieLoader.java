package com.example.peter.popularmovies.utils;

import com.example.peter.popularmovies.model.Movie;

import java.util.ArrayList;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by peter on 28/02/2018.
 * Loads a list of movies by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    private int mMovieSearchType;

    /**
     * Constructs a new {@link MovieLoader}
     *
     * @param context
     * @param movieSearchType - The type of search requested.
     */
    public MovieLoader(Context context, int movieSearchType) {
        super(context);
        mMovieSearchType = movieSearchType;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is done on a background thread.
     * @return - A list of Movie objects
     */
    @Override
    public ArrayList<Movie> loadInBackground() {

        // Perform the network request, parse the response, and extract a list of Movies.
        return JsonUtils.getMovieData(mMovieSearchType);
    }
}
