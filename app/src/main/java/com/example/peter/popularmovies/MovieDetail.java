package com.example.peter.popularmovies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.peter.popularmovies.app.Constants;
import com.example.peter.popularmovies.databinding.ActivityMovieDetailBinding;
import com.example.peter.popularmovies.model.Movie;
import com.example.peter.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

/**
 * Created by peter on 06/03/2018.
 * Manages the detail view
 * TODO - Pass in the adapter position so we can return to it when the back button is pressed
 * TODO - Set the title to the title of the movie (maybe have tmdb logo on the bar??)
 *
 */

public class MovieDetail extends AppCompatActivity {

    // Log tag for this class
    private static final String LOG_TAG = MovieDetail.class.getSimpleName();

    /*
     * This field is used for data binding. Normally, we would have to call findViewById many
     * times to get references to the Views in this Activity. With data binding however, we only
     * need to call DataBindingUtil.setContentView and pass in a Context and a layout, as we do
     * in onCreate of this class. Then, we can access all of the Views in our layout
     * programmatically without cluttering up the code with findViewById.
     */
    private ActivityMovieDetailBinding mDetailBinding;

    // Tracks the current movie
    private Movie mSelectedMovie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets up the view for us to bind data to.
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        // Extract the parcelable data from the intent and turn it back into a Movie object
        mSelectedMovie = getIntent().getParcelableExtra("selected_movie");

        mDetailBinding.movieDetailTitleTv.setText(mSelectedMovie.getTitle());

        URL backdropUrl = NetworkUtils.getMovieImageUrl(Constants.IMAGE_SIZE_XLARGE,
                mSelectedMovie.getBackdropImagePath());

        Log.e(LOG_TAG, "Backdrop Url: " + backdropUrl.toString());

        Picasso.with(this)
                .load(backdropUrl.toString())
                .into(mDetailBinding.movieDetailTrailerThumbnailIv);
    }
}
