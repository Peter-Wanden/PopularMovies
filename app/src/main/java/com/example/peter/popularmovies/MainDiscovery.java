package com.example.peter.popularmovies;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.peter.popularmovies.model.Movie;
import com.example.peter.popularmovies.utils.MovieLoader;
import com.example.peter.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;


public class MainDiscovery extends AppCompatActivity implements
        LoaderCallbacks<ArrayList<Movie>>,
        PosterAdapter.PosterAdapterOnClickHandler,
        SharedPreferences.OnSharedPreferenceChangeListener {

    // Log tag for this class
    private static final String LOG_TAG = MainDiscovery.class.getSimpleName();

    // Loader id
    private static final int POSTER_LOADER_ID = 100;

    // Data source
    private ArrayList<Movie> mMovies;

    // Adapter
    private PosterAdapter mPosterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_discovery);

        /* Get a reference to the recycler view */
        RecyclerView mRecyclerView = findViewById(R.id.posters_rv);

        /* GridLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a grid layout.
         */
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        /* Connect the layout manager to the RecyclerView */
        mRecyclerView.setLayoutManager(layoutManager);

        /* Developer docs recommend using this performance improvement if all of the views are the
         * same size
         */
        mRecyclerView.setHasFixedSize(true);

        /* Instantiate the data source for the adapter */
        mMovies = new ArrayList<>();

        /* Create a new adapter that takes an empty list of Movie objects */
        mPosterAdapter = new PosterAdapter(this, mMovies, this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mPosterAdapter);

        /* Ensures a loader is initialized and active. If the loader doesn't already exist, one is
         * created and (if the activity/fragment is currently started) starts the loader. Otherwise
         * the last created loader is re-used.
         */
        if (NetworkUtils.getNetworkStatus(this)) {
            getLoaderManager().initLoader(POSTER_LOADER_ID, null, this);

        } else {
            // Otherwise, log error
            Log.e(LOG_TAG, "Network connection error");
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (key.equals(getString(R.string.settings_order_by_key))) {
            // Clear the ListView as a new query will be kicked off
            mMovies.clear();

            // Restart the loader to requery the USGS as the query settings have been updated
            getLoaderManager().restartLoader(POSTER_LOADER_ID, null, this);
        }
    }

    /**
     * Instantiates and returns a new loader for the given loaderId
     *
     * @param loaderId - A unique integer that identifies the loader.
     * @param bundle   - additional data.
     * @return         - to onLoadFinished an ArrayList of Movie objects.
     */
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int loaderId, Bundle bundle) {

        // Get the user preference for the move search type
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int orderBy = Integer.parseInt(preferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        ));

        // On a background thread return an ArrayList of movies
        return new MovieLoader(this, orderBy);
    }

    /**
     * Called when a previously created loader has finished its load
     *
     * @param loaderId - The loader id. In this case POSTER_LOADER_ID
     * @param movies - A list of Movie objects returned from the onCreateLoader method.
     */
    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loaderId, ArrayList<Movie> movies) {

        if (movies != null && !movies.isEmpty()) {
            // Update the data source in the adapter
            mPosterAdapter.updateMovies(movies);
            // Tell the adapter it has new data
            mPosterAdapter.notifyDataSetChanged();
        } else {
            Log.i(LOG_TAG, "No data returned in onLoadFinished");
        }
    }

    /**
     * Called when a previously created loader is being reset, thus making its data unavailable.
     * @param loader - The ID of the loader to reset.
     */
    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        mPosterAdapter = null;
    }

    /**
     * This method is for responding to clicks from our list.
     *
     * @param clickedItemIndex is the position in the RecyclerView.
     */
    @Override
    public void onClick(int clickedItemIndex) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
