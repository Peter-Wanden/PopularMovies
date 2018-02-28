package com.example.peter.popularmovies.utils;

import android.text.TextUtils;
import android.util.Log;
import com.example.peter.popularmovies.model.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by peter on 22/02/2018.
 * Utility class with methods to perform the HTTP request and parse the JSON response
 */

public final class JsonUtils {

    // Log tag for this class
    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    /**
     * Query the TMDb server and return a list of Movie objects based on user preferences
     * TODO - User preferences
     */
    public static ArrayList<Movie> getMovieData(int searchType) {
        /* Create the URL object */
        URL url = NetworkUtils.getMovieSearchUrl(searchType);

        // Perform a HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the list of {@link Movie} objects
        return extractFeatureFromJson(jsonResponse);
    }

    private static ArrayList<Movie> extractFeatureFromJson(String movieJson) {
        // If the JSON string is empty or null return immediately
        if (TextUtils.isEmpty(movieJson)) {
            return null;
        }

        // Instantiate an empty ArrayList
        ArrayList<Movie> movies = new ArrayList<>();

        // Try to parse the JSON response String. If there is a problem an exception will be
        // thrown which will be caught and logged. This prevents the app from crashing and provides
        // useful diagnostic information.
        try {
            // Create a JSON object from the input string
            JSONObject baseJsonResponse = new JSONObject(movieJson);

            // Extract the array of movie objects from the base JSON response
            JSONArray resultsArray = baseJsonResponse.getJSONArray("results");

            // Iterate through the array extracting the movie objects and assign their values
            // to new Movie objects
            for (int i = 0; i < resultsArray.length(); i++) {

                // Get a single movie object at position 'i'
                JSONObject currentMovie = resultsArray.getJSONObject(i);

                // Extract the movie title
                String movieTitle = currentMovie.optString("title");

                // Extract the poster path and build the URL for the image
                String posterPath = currentMovie.optString("poster_path");
                URL posterUrl = NetworkUtils.getMoviePosterUrl(posterPath);

                // Create a new Movie object and pass in the required fields
                Movie movie = new Movie(movieTitle, posterUrl);

                // Add the new movie to the list of movies
                movies.add(movie);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing JSON results with error: " + e);
        }
        // Return the list of movies
        return movies;
    }
}
