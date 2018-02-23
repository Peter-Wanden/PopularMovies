package com.example.peter.popularmovies.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.peter.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 22/02/2018.
 * Utility class with methods to help perform the HTTP request and parse the JSON response
 */

public final class JsonUtils {

    // Log tag for this class
    public static final String LOG_TAG = JsonUtils.class.getSimpleName();

    /**
     * Private constructor to prevent the creation of a {@link JsonUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from its class name, therefore an object instance is not needed
     */
    private JsonUtils() {
    }

    /**
     * Query the TMDb database and return a list of Movie objects
     */
    public static List<Movie> getMovieData(String requestUrl) {
        // Create the URL object
        URL url = createUrl(requestUrl);

        // Perform a HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request: " + e);
        }
        // Extract relevant fields from the response and create a list of {@link Movie} objects
        List<Movie> movies = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Movie} objects
        return movies;
    }

    /**
     * Makes a HTTP request to the given URL and return a string as a response
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, return early
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200)
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error message code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the movie JSON results with error: " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a string that contains the whole JSON response
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static List<Movie> extractFeatureFromJson(String movieJson) {
        // If the JSON string is empty or null return immediately
        if (TextUtils.isEmpty(movieJson)) {
            return null;
        }

        // Instantiate an empty ArrayList
        List<Movie> movies = new ArrayList<>();

        // Try to parse the JSON response String. If there is a problem an exception will be
        // thrown which will be caught and logged. This prevents the app from crashing and provides
        // useful diagnostic information.
        try {
            // Create a JSON object from the input string
            JSONObject baseJsonResponse = new JSONObject(movieJson);

            // Extract the array of movie objects from the base JSON response
            JSONArray resultsArray = baseJsonResponse.getJSONArray("results");

            // Iterate through the array extracting the movie objects and assign their values
            // to Movie objects
            for (int i = 0; i < resultsArray.length(); i++) {

                // Get a single movie object at position 'i'
                JSONObject currentMovie = resultsArray.getJSONObject(i);

                // Extract the movit title
                String movieTitle = (String) currentMovie.get("title");

                // Extract the poster path
                String posterPath = (String) currentMovie.get("poster_path");

                // Create a new Movie object and pass in the require fields
                Movie movie = new Movie(movieTitle, posterPath);

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
