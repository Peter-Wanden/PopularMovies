package com.example.peter.popularmovies.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.peter.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 26/02/2018.
 * Returns a list of Movie objects using an AsyncTask to perform the network request to the
 * given URL
 */

public class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    /* Query URL */
    private int mSearchType;

    public MovieLoader(Context context, int searchType) {
        super(context);
        mSearchType = searchType;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        // Perform the network request, parse the response and extract the list of Movie objects
        return JsonUtils.getMovieData(mSearchType);
    }
}
