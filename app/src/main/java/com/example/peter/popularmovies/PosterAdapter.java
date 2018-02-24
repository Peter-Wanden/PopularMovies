package com.example.peter.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * Created by peter on 23/02/2018.
 * {@link PosterAdapter} exposes a list of Movie posters from an  TODO ArrayList
 * to an {@link android.support.v7.widget.RecyclerView}.
 */

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterAdapterViewHolder> {

    /* Log tag for this class */
    private static final String LOG_TAG = PosterAdapter.class.getSimpleName();

    /* The context we use to utility methods, app resources and layout inflaters */
    private final Context mContext;
    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    final private PosterAdapterOnClickHandler mClickHandler;
    int mNumberItems;

    /**
     * Constructor for PosterAdapter that accepts a number of items to display and the specification
     * for the PosterAdapterOnClickHandler.
     *
     * @param context  Used to talk to the UI and app resources
     * @param listener Listener for list item clicks
     */
    public PosterAdapter(Context context, PosterAdapterOnClickHandler listener) {
        mContext = context;
        mClickHandler = listener;
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

        View view = LayoutInflater.from(mContext).inflate(layoutIdForListItem, viewGroup, false);

        view.setFocusable(true);

        return new PosterAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is
     * conveniently passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(PosterAdapterViewHolder holder, int position) {

        // Log position for now.
        Log.d(LOG_TAG, "Position #: " + position);


    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available
     */
    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface PosterAdapterOnClickHandler {
        void onClick(int clickedItemIndex);
    }

    /**
     * A ViewHolder is a required part of the pattern for RecyclerViews. It mostly behaves as
     * a cache of the child views for a list / grid item. It's also a convenient place to set an
     * OnClickListener, since it has access to the adapter and the views.
     */
    class PosterAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // Displays a movie poster image
        ImageView listItemImageView;

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

            // Get a reference to the ImageView
            listItemImageView = itemView.findViewById(R.id.poster_iv);

            // Call setOnClickListener on the View passed into the constructor (use 'this' as the OnClickListener)
            itemView.setOnClickListener(this);
        }

        /**
         * Called whenever a user clicks on an item in the list. Returns the clicked position
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mClickHandler.onClick(clickedPosition);
        }
    }
}
