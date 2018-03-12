package com.example.peter.popularmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.example.peter.popularmovies.BuildConfig;
import com.example.peter.popularmovies.MovieDiscovery;
import com.example.peter.popularmovies.app.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by peter on 24/02/2018.
 * This class constructs a URL and then queries 'The Movie Database' API.
 * Each URL returns a list of either the most popular or highest rated movies.
 * If available, each result is accompanied by an image URL pointing to its corresponding
 * movie poster.
 *
 * TODO - Limit the search results to a reasonable amount.
 * TODO - Display a message if there is no results.
 * TODO - Display a message if there is no network connection.
 *
 */

public final class NetworkUtils {

    /* URL search type for most popular */
    public static final int MOST_POPULAR = 0;

    /* URL search type for highest rated */
    public static final int HIGHEST_RATED = 1;

    /* Log tag for this class */
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    /* API key Key pair identifier. */
    private static final String API_KEY = "api_key";

    /* API Key */
    private static final String API_KEY_VALUE = BuildConfig.API_KEY;

    /* Base URL for a search. */
    private static final String BASE_SEARCH_URL = "https://api.themoviedb.org/3";

    /* There are three ways (paths) to search and find what you want on TMDB.
        /search   - a text based search
        /discover - search based on filters or definable values like ratings, certifications
                    or release dates.
        /find     - using external id's such as using the IMDB ID of a movie

        For this application we only need to use the discover path
    */
    private static final String PATH_DISCOVER = "discover";

    /* There are two further paths in under discover.
        /movie  - for movie information
        /tv     - for TV information

        For this application we only need the path to movies
     */
    private static final String PATH_MOVIE = "movie";

    /* Tells the API that we would like to sort the results */
    private static final String SORT_BY = "sort_by";

    /* Sort criteria for most popular */
    private static final String POPULARITY = "POPULARITY.desc";

    /* Sort criteria for highest rated */
    private static final String RATING = "vote_average.desc";

    /**
     * Create a private constructor because no one should ever create a {@link NetworkUtils}
     * object. This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name TmdbApiUtils (an object instance of QueryUtils is not needed).
     */
    private NetworkUtils() {
    }

    /**
     * Checks for network connectivity.
     *
     * @param context - Context of the class requesting connectivity.
     * @return - true for access.
     */
    public static boolean getNetworkStatus(Context context) {

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(MovieDiscovery.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        // If there is an active network return true, else return false
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * This method constructs and returns a URL that will search by 'POPULARITY' or 'RATING'
     * depending on the args sent to it.
     *
     * @param searchType - Defines the type of search URL to build.
     * @return - A URL converted to a String.
     */
    public static URL getMovieSearchUrl(int searchType) {

        Uri.Builder searchUri = Uri.parse(BASE_SEARCH_URL).buildUpon()
                .appendPath(PATH_DISCOVER)
                .appendPath(PATH_MOVIE);

        switch (searchType) {
            case MOST_POPULAR:
                searchUri.appendQueryParameter(SORT_BY, POPULARITY);
                break;

            case HIGHEST_RATED:
                searchUri.appendQueryParameter(SORT_BY, RATING);
                break;

            default:
                // An incorrect argument should not crash the app. Instead return MOST_POPULAR
                searchUri.appendQueryParameter(SORT_BY, POPULARITY);
                // Log the error
                Log.e(LOG_TAG, "Search criteria not supported for search type: "
                        + searchType);
        }

        // Append the API key, and return the completed search URL as a String.
        searchUri.appendQueryParameter(API_KEY, API_KEY_VALUE).build();

        try {
            URL url = new URL(searchUri.toString());
            Log.e(LOG_TAG, "Search URL is: " + url);
            return url;

            // return new URL(searchUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Constructs and returns a URL that will return a specified image from the server
     *
     * @param imageType - See {@link Constants} - the size of image
     * @param imagePath - The path to the image for the selected movie.
     * @return          - A complete Url that returns an image of the correct type and size.
     */
    public static URL getMovieImageUrl(String imageType, String imagePath) {

        //TODO Convert to switch statement if possible
        // Build the base Uri
        Uri.Builder getImageUrl = Uri.parse(Constants.BASE_IMAGE_URL).buildUpon();

        // Add the required image size
        switch (imageType) {
            case Constants.IMAGE_SIZE_SMALL:
                getImageUrl.appendPath(Constants.IMAGE_SIZE_SMALL);
                break;
            case Constants.IMAGE_SIZE_MEDIUM:
                getImageUrl.appendPath(Constants.IMAGE_SIZE_MEDIUM);
                break;
            case Constants.IMAGE_SIZE_LARGE:
                getImageUrl.appendPath(Constants.IMAGE_SIZE_LARGE);
                break;
            case Constants.IMAGE_SIZE_XLARGE:
                getImageUrl.appendPath(Constants.IMAGE_SIZE_XLARGE);
                break;
            default:
                Log.i(LOG_TAG, "Image size not supported");
        }

        // Add the image path
        getImageUrl.appendEncodedPath(imagePath);

        // Return the Url
        try {
            Log.e(LOG_TAG, "Movie image URL: " + getImageUrl.toString());
            return new URL(getImageUrl.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }
}
