package com.example.peter.popularmovies;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.peter.popularmovies.model.Movie;

import java.util.List;

public class MainDiscovery extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    // API Key TODO - Make this key a user input under preferences
    private static final String API_KEY = "api_key=dc92a2a6c5b67810baa769bdd918b776";

    // Base URL to latest version (3) of the API
    private static final String BASE_URL = "https://api.themoviedb.org/3";

    // Base URL for poster images with the image size appended
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w185";

    // Discover path in API
    private static final String DISCOVER = "/discover/movie?";

    // Path to most popular (append to DISCOVER)
    private static final String MOST_POPULAR = "&sort_by=popularity.desc";

    // Path to highest rated (append to DISCOVER)
    private static final String HIGHEST_RATED = "&certification_country=US&certification=R&sort_by=vote_average.desc";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_discovery);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {

    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }
}
