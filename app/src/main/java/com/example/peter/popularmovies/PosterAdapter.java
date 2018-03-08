package com.example.peter.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.peter.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by peter on 23/02/2018.
 * {@link PosterAdapter} exposes a list of Movie posters from an ArrayList
 * to an {@link android.support.v7.widget.RecyclerView}.
 */

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterAdapterViewHolder> {

    /* Log tag for this class */
    private static final String LOG_TAG = PosterAdapter.class.getSimpleName();

    /* The context we use to reach utility methods, app resources and layout inflaters */
    private final Context mContext;

    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    final private PosterAdapterOnClickHandler mClickHandler;

    /* Instantiate an ArrayList of Movies as the data source */
    private ArrayList<Movie> mMovies;

    /**
     * Constructor for PosterAdapter that accepts a number of items to display and the specification
     * for the PosterAdapterOnClickHandler.
     *
     * @param context Used to talk to the UI and app resources
     * @param listener Listener for list item clicks
     */
    public PosterAdapter(Context context, PosterAdapterOnClickHandler listener) {
        mContext = context;
        mClickHandler = listener;

        // Instantiate a blank list ready to be populated by the results
        mMovies = new ArrayList<>();
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If the RecyclerView has more than one type of item we can use this viewType
     *                  integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new PosterAdapterViewHolder that holds the fragment View for each list item
     */
    @Override
    public PosterAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutIdForListItem = R.layout.poster_item_view;
        View view = LayoutInflater
                .from(mContext)
                .inflate(layoutIdForListItem, viewGroup, false);

        view.setFocusable(true);

        return new PosterAdapterViewHolder(viewGroup, view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is
     * conveniently passed into us.
     *
     * @param posterAdapterViewHolder   The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(PosterAdapterViewHolder posterAdapterViewHolder, int position) {

        // Get the current Movie object
        Movie currentMovie = mMovies.get(position);

        // Get the last path segment of the movie poster URL so we can check for an image path.
        String lastPathSegment = Uri
                .parse(currentMovie
                        .getMoviePosterUrl()
                        .toString())
                .getLastPathSegment();


        /* If a valid movie poster URL endpoint is not available:
         * - Turn the visibility of movieTitleTextView and noPosterAvailableTextView to on
         * - Display the movie title + (No movie poster available)         *
         */

        if (lastPathSegment.equals("no_image_available")) {
            // If the standard height of an image has been previously set
            Picasso.with(mContext)
                    .load(R.drawable.popular_movies_poster)
                    .into(posterAdapterViewHolder.listItemImageView);

            posterAdapterViewHolder.movieTitleTextView.setVisibility(View.VISIBLE);
            posterAdapterViewHolder.movieTitleTextView.setText(currentMovie.getTitle());

            posterAdapterViewHolder.noPosterAvailableTextView.setVisibility(View.VISIBLE);
            posterAdapterViewHolder.noPosterAvailableTextView.setText(R.string.no_poster);

        } else {

        /* If a valid movie poster URL endpoint is available:
         * - Display the movies poster in the current ViewHolder
         */
            posterAdapterViewHolder.movieTitleTextView.setVisibility(View.INVISIBLE);
            posterAdapterViewHolder.noPosterAvailableTextView.setVisibility(View.INVISIBLE);

            Picasso.with(mContext)
                    .load(currentMovie.getMoviePosterUrl().toString())
                    .into(posterAdapterViewHolder.listItemImageView);
        }
        // Add the attribution logo to each view
        posterAdapterViewHolder.tmdbLogoImageView.setImageResource(R.drawable.ic_stacked_green);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * by LayoutManager to help layout our Views and for animations.
     *
     * @return The number of items available
     */
    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void updateMovies(ArrayList<Movie> movies) {
        if (movies != null && !movies.isEmpty()) {
            mMovies.clear();
            mMovies.addAll(movies);
        } else {
            mMovies.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface PosterAdapterOnClickHandler {
        void onClick(Movie clickedMovie);
    }

    /**
     * A ViewHolder is a required part of the pattern for RecyclerViews. It mostly behaves as
     * a cache of the child views for a list / grid item. It's also a convenient place to set an
     * OnClickListener, since it has access to the adapter and the views.
     */
    class PosterAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // Holder to display a movie poster image
        ImageView listItemImageView;

        // Holder to display the movie title if no image is available
        TextView movieTitleTextView;

        // Holder to display 'No image available', if no movie poster is available
        TextView noPosterAvailableTextView;

        ImageView tmdbLogoImageView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * ImageView and set an onClickListener to listen for clicks, which will be handled in the
         * onClick method below.
         *
         * @param itemView The View that you inflated in
         *                 {@link PosterAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        PosterAdapterViewHolder(ViewGroup parent, View itemView) {
            super(itemView);

            // Get a reference to the views
            listItemImageView = itemView.findViewById(R.id.poster_iv);
            movieTitleTextView = itemView.findViewById(R.id.movie_title_tv);
            noPosterAvailableTextView = itemView.findViewById(R.id.no_poster_available_tv);
            tmdbLogoImageView = itemView.findViewById(R.id.tmdb_logo);

            // Call setOnClickListener on the View passed into the constructor (use 'this' as the OnClickListener)
            itemView.setOnClickListener(this);

            // Set minimum height
            itemView.setMinimumHeight(parent.getMeasuredHeight() / 4);
        }

        /**
         * Called whenever a user clicks on an item in the list. Returns the clicked position
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Movie currentMovie = mMovies.get(clickedPosition);
            mClickHandler.onClick(currentMovie);
        }
    }
}
