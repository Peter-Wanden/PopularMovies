package com.example.peter.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.peter.popularmovies.app.Constants;
import com.example.peter.popularmovies.model.Movie;
import com.example.peter.popularmovies.utils.NetworkUtils;
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
    private final ArrayList<Movie> mMovies;

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
    @NonNull
    @Override
    public PosterAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        int layoutIdForListItem = R.layout.poster_item_view;
        View view = LayoutInflater
                .from(mContext)
                .inflate(layoutIdForListItem, viewGroup, false);

        view.setFocusable(true);

        return new PosterAdapterViewHolder(view);
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
    public void onBindViewHolder(@NonNull PosterAdapterViewHolder posterAdapterViewHolder, int position) {

        // Get the current Movie object
        Movie currentMovie = mMovies.get(position);

        // Get the image path of the movie poster URL so we can check for a valid image path.
        String imagePath = currentMovie.getPosterImagePath();

        // Set the movie title
        posterAdapterViewHolder.movieTitleTextView.setText(currentMovie.getTitle());

        /* If a valid movie poster URL endpoint is not available */
        if (imagePath.equals("no_image_available")) {

            // Swap the visibilities of the various views
            posterAdapterViewHolder.listItemImageView.setVisibility(View.GONE);
            posterAdapterViewHolder.noPosterAvailableTextView.setVisibility(View.VISIBLE);
            posterAdapterViewHolder.listItemNoImageImageView.setVisibility(View.VISIBLE);

            // Set the image to be a tMDB logo
            posterAdapterViewHolder.listItemNoImageImageView
                    .setImageDrawable(mContext.getResources()
                            .getDrawable(R.drawable.ic_powered_by_rectangle_green));

            posterAdapterViewHolder.noPosterAvailableTextView
                    .setText(R.string.no_poster);

        } else {

            /* If a valid movie poster URL endpoint is available:
             * - Display the movies poster in the current ViewHolder
             */
            posterAdapterViewHolder.listItemNoImageImageView.setVisibility(View.GONE);
            posterAdapterViewHolder.noPosterAvailableTextView.setVisibility(View.INVISIBLE);
            posterAdapterViewHolder.listItemImageView.setVisibility(View.VISIBLE);

            Picasso.with(mContext)
                    .load(String.valueOf(NetworkUtils.getMovieImageUrl
                            (Constants.IMAGE_SIZE_MEDIUM, currentMovie.getPosterImagePath())))
                    .placeholder(R.drawable.ic_powered_by_rectangle_blue)
                    .into(posterAdapterViewHolder.listItemImageView);
        }
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
        void onClick(Movie clickedMovie, int adapterPosition);
    }

    /**
     * A ViewHolder is a required part of the pattern for RecyclerViews. It mostly behaves as
     * a cache of the child views for a list / grid item. It's also a convenient place to set an
     * OnClickListener, since it has access to the adapter and the views.
     */
    class PosterAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // Holder to display a movie poster
        final ImageView listItemImageView;

        // Holder to display a placeholder when an image is not available
        final ImageView listItemNoImageImageView;

        // Holder to display the movie title if no image is available
        final TextView movieTitleTextView;

        // Holder to display 'No image available', if no movie poster is available
        final TextView noPosterAvailableTextView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * ImageView and set an onClickListener to listen for clicks, which will be handled in the
         * onClick method below.
         *
         * @param itemView The View that you inflated in
         *                 {@link PosterAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        PosterAdapterViewHolder(View itemView) {
            super(itemView);

            // Get a reference to the views
            listItemImageView = itemView.findViewById(R.id.poster_iv);
            listItemNoImageImageView = itemView.findViewById(R.id.no_poster_available_iv);
            movieTitleTextView = itemView.findViewById(R.id.movie_title_poster_tv);
            noPosterAvailableTextView = itemView.findViewById(R.id.no_poster_available_tv);

            // Call setOnClickListener on the View passed into the constructor (use 'this' as the OnClickListener)
            itemView.setOnClickListener(this);
        }

        /**
         * Called whenever a user clicks on an item in the list. Returns the clicked position
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Movie currentMovie = mMovies.get(clickedPosition);
            mClickHandler.onClick(currentMovie, getAdapterPosition());
        }
    }
}
