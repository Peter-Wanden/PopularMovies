package com.example.peter.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainDiscovery extends AppCompatActivity {

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
}
