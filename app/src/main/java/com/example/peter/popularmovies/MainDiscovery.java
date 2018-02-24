package com.example.peter.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.peter.popularmovies.model.Movie;
import com.example.peter.popularmovies.utils.TmdbApiUrlUtils;

import java.util.List;

public class MainDiscovery extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Movie>>, PosterAdapter.PosterAdapterOnClickHandler {

    // Log tag for this class
    private static final String LOG_TAG = MainDiscovery.class.getSimpleName();

    // API Key TODO - Make this key a user input under preferences
    private static final String API_KEY = "dc92a2a6c5b67810baa769bdd918b776";

    // Base URL to latest version (3) of the API
    private static final String BASE_URL = "https://api.themoviedb.org/3";

    // Base URL for poster images with the image size appended
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w185";

    // Path 'discover' in API
    private static final String PATH_DISCOVER = "discover";

    // Path 'movie' in API
    private static final String PATH_MOVIE = "movie";

    // Path to most popular (append to DISCOVER)
    private static final String MOST_POPULAR = "popularity.desc";

    // Path to highest rated (append to DISCOVER)
    private static final String HIGHEST_RATED = "&certification_country=US&certification=R&sort_by=vote_average.desc";


    // Loader id
    private static final int POSTER_LOADER_ID = 100;

    // Adapter & recycler
    private PosterAdapter mPosterAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_discovery);

        // Get a reference to recycler xml resource
        mRecyclerView = findViewById(R.id.posters_rv);

        // GridLayoutManager is responsible for measuring and positioning item views within a
        // RecyclerView into a grid layout.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        // Set the layout manager onto our RecyclerView
        mRecyclerView.setLayoutManager(gridLayoutManager);

        // Create a new adapter that takes an empty list of movie posters as input
        mPosterAdapter = new PosterAdapter(this, this);

        // Setting the adapter attaches it to the RecyclerView in our layout.
        mRecyclerView.setAdapter(mPosterAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        /*
        * Ensures a loader is initialized and active. If the loader doesn't already exist, one is
        * created and (if the activity/fragment is currently started) starts the loader. Otherwise
        * the last created loader is re-used.
        */
        if (networkInfo != null && networkInfo.isConnected()) {
            getSupportLoaderManager().initLoader(POSTER_LOADER_ID, null, this);

        } else {
            // Otherwise, log error TODO - handle this better, see quake report
            Log.e(LOG_TAG, "Network connection error");
        }
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {

//        Uri baseUri = Uri.parse(BASE_URL);
//        Uri.Builder uriBuilder = baseUri.buildUpon();
//
//        uriBuilder.appendPath(PATH_DISCOVER);
//        uriBuilder.appendPath(PATH_MOVIE);
//        uriBuilder.appendQueryParameter("api_key", API_KEY);
//        uriBuilder.appendQueryParameter("sort_by", MOST_POPULAR);


        Uri uri = TmdbApiUrlUtils.returnUri(2);

        Log.i(LOG_TAG, "URL is: " + uri.toString());

        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {

    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        // TODO - Why is this not working?? mPosterAdapter.clear();
    }

    /**
     * This method is for responding to clicks from our list.
     *
     * @param clickedItemIndex is the position in the RecyclerView.
     */
    @Override
    public void onClick(int clickedItemIndex) {

    }
}
