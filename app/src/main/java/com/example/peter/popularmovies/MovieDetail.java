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
 */

public class MovieDetail extends AppCompatActivity {

    // Log tag for this class
    private static final String LOG_TAG = MovieDetail.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets up the view for us to bind data to.
        ActivityMovieDetailBinding detailBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        // Extract the parcelable data from the intent and turn it back into a Movie object
        Movie selectedMovie = getIntent().getParcelableExtra("selected_movie");

        // Load the backdrop
        Picasso.with(this)
                .load(NetworkUtils.getMovieImageUrl(Constants.IMAGE_SIZE_XLARGE,
                        selectedMovie.getPosterImagePath()).toString())
                .into(detailBinding.movieDetailTrailerThumbnailIv);
        Picasso.with(this)
                .load(NetworkUtils.getMovieImageUrl(Constants.IMAGE_SIZE_SMALL,
                        selectedMovie.getPosterImagePath()).toString())
                .into(detailBinding.movieDetailPosterSmallIv);

        // Set the title
        detailBinding.movieDetailTitleTv.setText(selectedMovie.getTitle());

        // Set the rating
        detailBinding.movieDetailVoteAverageTv.setText(String.valueOf(selectedMovie.getUserRating()));

        // Set the year
        detailBinding.movieDetailReleaseYear.setText(selectedMovie.getMovieReleaseDate());

        // Set the synopsis
        detailBinding.movieDetailDescriptionTv.setText(selectedMovie.getMovieSynopsis());

        setTitle(selectedMovie.getTitle());
    }
}
