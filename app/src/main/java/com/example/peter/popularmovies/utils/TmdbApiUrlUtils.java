package com.example.peter.popularmovies.utils;

import android.net.Uri;

/**
 * Created by peter on 24/02/2018.
 * This class constructs a URL to query 'The Movie Database' API
 * <p>
 * TODO - build this class to provide a URL based on the users preferences for either most popular or highest rated
 * This
 */

public class TmdbApiUrlUtils {

    // Type of query
    private static final int MOST_POPULAR = 1;
    private static final int HIGHEST_RATED = 2;


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
    private static final String POPULARITY = "popularity.desc";

    // Country code for united states of America
    private static final String COUNTRY_CODE_FOR_US = "US";

    // Certification levels
    // Certification level 'R'
    private static final String CERTIFICATION_R = "R";

    // Sort by params
    private static final String VOTE_AVERAGE_DESCENDING = "vote_average.desc";

    // There is no need for this class to be instantiated
    public TmdbApiUrlUtils() {
    }

    // This method
    public static Uri returnUri(int userSearch) {

        Uri baseUri = Uri.parse(BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("api_key", API_KEY);

        switch (userSearch) {
            case MOST_POPULAR:
                uriBuilder.appendPath(PATH_DISCOVER);
                uriBuilder.appendPath(PATH_MOVIE);
                uriBuilder.appendQueryParameter("sort_by", POPULARITY);
                break;

            case HIGHEST_RATED:
                uriBuilder.appendQueryParameter("certification_country", COUNTRY_CODE_FOR_US);
                uriBuilder.appendQueryParameter("certification", CERTIFICATION_R);
                uriBuilder.appendQueryParameter("sort_by", VOTE_AVERAGE_DESCENDING);
                break;

            default:
                throw new IllegalArgumentException("Invalid search criteria: " + userSearch);
        }
        return uriBuilder.build();

    }
}
