package io.rolgalan.musicsearch;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.rolgalan.musicsearch.data.DataProvider;
import io.rolgalan.musicsearch.model.Track;

/**
 * A fragment representing a single Track detail screen.
 * This fragment is either contained in a {@link TrackListActivity}
 * in two-pane mode (on tablets) or a {@link TrackDetailActivity}
 * on handsets.
 */
public class TrackDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The track content this fragment is presenting.
     */
    private Track mTrack;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TrackDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the Track content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mTrack = DataProvider.ITEM_MAP.get(getArguments().getInt(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mTrack.getArtistName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.track_detail, container, false);

        // Show the Track content as text in a TextView.
        if (mTrack != null) {
            ((TextView) rootView.findViewById(R.id.track_detail)).setText(mTrack.getTrackName());
        }

        return rootView;
    }
}
