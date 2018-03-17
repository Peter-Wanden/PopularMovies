package com.example.peter.popularmovies.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.peter.popularmovies.app.Constants;
import com.example.peter.popularmovies.model.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by peter on 22/02/2018.
 * Utility class with methods to parse the JSON response.
 */

final class JsonUtils {

    /* Log tag for this class */
    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    /**
     * Query the TMDb server and return a list of Movie objects based on user preferences.
     */
    static ArrayList<Movie> getMovieData(int searchType) {

        /* Create the URL object */
        URL url = NetworkUtils.getMovieSearchUrl(searchType);

        // Perform a HTTP request to the URL and receive a JSON response back.
        String jsonResponse = null;
        try {
            jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the list of {@link Movie} objects.
        return extractFeatureFromJson(jsonResponse);
    }

    /**
     * Extracts data from JSON to new Movie objects.
     *
     * @param movieJson A string containing JSON returned from the server.
     * @return An ArrayList of new Movie objects.
     */
    private static ArrayList<Movie> extractFeatureFromJson(String movieJson) {
        // If the JSON string is empty or null return immediately
        if (TextUtils.isEmpty(movieJson)) {
            return null;
        }

        // Instantiate an empty ArrayList to store new Movie objects.
        ArrayList<Movie> movies = new ArrayList<>();

        /* Try to parse the JSON response String. If there is a problem an exception will be
         * thrown which will be caught and logged. This prevents the app from crashing and provides
         * useful diagnostic information.
         *
         * Additionally if particular values are not available for a movie they are dealt with.
         */
        try {
            /* Create a JSON object from the input string */
            JSONObject baseJsonResponse = new JSONObject(movieJson);

            /* Extract the array of movie objects from the base JSON response */
            JSONArray resultsArray = baseJsonResponse.getJSONArray(Constants.MOVIE_LIST);

            /* Extract movie objects, assign their values to new Movie objects */
            for (int i = 0; i < resultsArray.length(); i++) {

                // Get a single movie object at position 'i'
                JSONObject currentMovie = resultsArray.getJSONObject(i);

                // Extract the movie ID. If not available default to -1.
                int movieId = currentMovie
                        .optInt(Constants.MOVIE_ID, -1);

                // Extract the movie title.
                String movieTitle = currentMovie
                        .optString(Constants.MOVIE_TITLE, null);

                // Extract the movie's original title
                String movieOriginalTitle = currentMovie
                        .optString(Constants.MOVIE_ORIGINAL_TITLE, null);

                // Extract the movies poster URL path
                String imagePosterPath = currentMovie
                        .optString(Constants.MOVIE_POSTER, null);

                /* We will need the movie poster path later to load the movie poster image.
                 * If no poster path is available, append the String 'no_image_available' to
                 * its last path segment. We can look for this eventuality and deal with it later
                 * in the when we construct and populate item views */
                if (imagePosterPath == null || imagePosterPath.isEmpty()
                        || imagePosterPath.equals("null")) {
                    imagePosterPath = Constants.NO_POSTER_AVAILABLE;
                }

                // Extract the movies backdrop URL path
                String imageBackdropPath = currentMovie.optString(Constants.MOVIE_BACKDROP, null);

                if (imageBackdropPath == null || imageBackdropPath.isEmpty()
                        || imageBackdropPath.equals("null")) {
                    imageBackdropPath = Constants.NO_BACKDROP_AVAILABLE;
                }

                // Extract the movies plot synopsis
                String movieSynopsis = currentMovie
                        .optString(Constants.MOVIE_PLOT_SYNOPSIS, null);

                // Extract the movies user rating
                double userRating = currentMovie
                        .optDouble(Constants.MOVIE_USER_RATING, -1);

                // Extract the movie release date
                String movieReleaseFullDate = currentMovie
                        .optString(Constants.MOVIE_RELEASE_DATE, null);

                // Empty string for movie year
                String movieReleaseYear = null;

                // If a release date is present, extract the year
                if (movieReleaseFullDate != null) {
                    String movieReleaseDatePart[] = movieReleaseFullDate.split("-", 0);
                    movieReleaseYear = movieReleaseDatePart[0];
                }

                // Create a new Movie object and pass in the required fields.
                Movie movie = new Movie(movieId, movieTitle, movieOriginalTitle, imagePosterPath,
                        imageBackdropPath, movieSynopsis, userRating, movieReleaseYear);

                // Add the new movie to the list of movies.
                movies.add(movie);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing JSON results with error: " + e);
        }
        // Return the list of movies.
        return movies;
    }
}
