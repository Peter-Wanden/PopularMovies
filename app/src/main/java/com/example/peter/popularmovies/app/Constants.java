package com.example.peter.popularmovies.app;

/**
 * Created by peter on 09/03/2018.
 * Contains the applications constant fields
 */

public class Constants {

    // Base URL for poster images
    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p";

    // Image sizes
    public static final String IMAGE_SIZE_SMALL = "w185";
    public static final String IMAGE_SIZE_MEDIUM = "w342";
    public static final String IMAGE_SIZE_LARGE = "w500";
    public static final String IMAGE_SIZE_XLARGE = "w780";


    // If no image is available
    public static final String NO_POSTER_AVAILABLE = "no_image_available";
    public static final String NO_BACKDROP_AVAILABLE = "no_backdrop_available";

    /* JSON movie elements */
    public static final String MOVIE_LIST = "results";
    public static final String MOVIE_ID = "id";
    public static final String MOVIE_TITLE = "title";
    public static final String MOVIE_ORIGINAL_TITLE = "original_title";
    public static final String MOVIE_POSTER = "poster_path";
    public static final String MOVIE_BACKDROP = "backdrop_path";
    public static final String MOVIE_PLOT_SYNOPSIS = "overview";
    public static final String MOVIE_USER_RATING = "vote_average";
    public static final String MOVIE_RELEASE_DATE = "release_date";

    /* Strings used as Key - pair's */
    public static final String SELECTED_MOVIE_KEY = "selected_movie";
}
