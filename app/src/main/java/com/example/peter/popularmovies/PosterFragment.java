package com.example.peter.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by peter on 22/02/2018.
 * This class is responsible for inflating poster fragments
 */

public class PosterFragment extends Fragment {

    // Final Strings to store state information about the list of posters and list index
    public static final String POSTER_ID_LIST = "image_ids";
    public static final String LIST_INDEX = "list_index";
    // Log tag for class
    private static final String LOG_TAG = PosterFragment.class.getSimpleName();
    // Variables to store a list of image resources provided by Picasso and the index of the image
    // that this fragment displays
    private List<Integer> mImageIds;
    private int mListIndex;

    // Obligatory empty constructor for the FragmentManager to instantiate this class
    public PosterFragment() {
    }

    // Inflates the fragment layout and sets the correct resource for the image to display
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Check for and load saved state if available
        if (savedInstanceState != null) {
            mImageIds = savedInstanceState.getIntegerArrayList(POSTER_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_INDEX);
        }

        // Inflate the poster layout
        View rootView = inflater.inflate(R.layout.fragment_poster, container, false);

        // Get a reference to the image view in the fragment layout
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_poster_iv);

        // If a list of poster ids exists, set the poster resource to the correct item in the list
        // Otherwise, create a Log statement that indicates that the list was not found
        if (mImageIds != null) {
            // Set the image resource to the list item at the stored index
            imageView.setImageResource(mImageIds.get(mListIndex));

            // Set a click listener on the image view
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Increment position as long as the index remains <= the size of the image ids list
                    if (mListIndex < mImageIds.size() - 1) {
                        mListIndex++;
                    } else {
                        // The end of list has been reached, so return to beginning index
                        mListIndex = 0;
                    }
                    // Set the image resource to the new list item
                    imageView.setImageResource(mImageIds.get(mListIndex));
                }
            });

        } else {
            Log.v(LOG_TAG, "This fragment has a null list of image id's");
        }

        // Return the view
        return rootView;
    }
}
