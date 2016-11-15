package io.rolgalan.musicsearch;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.rolgalan.musicsearch.data.DataProvider;
import io.rolgalan.musicsearch.model.Track;
import io.rolgalan.musicsearch.util.TrackMediaPlayer;
import io.rolgalan.musicsearch.view.TrackView;
import io.rolgalan.musicsearch.view.TwoPaneableActivity;

/**
 * A fragment representing a single Track detail screen.
 * This fragment is either contained in a {@link TrackListActivity}
 * two-pane mode (on tablets) or a {@link TrackDetailActivity} on handsets.
 */
public class TrackDetailFragment extends Fragment {
    private TrackView trackView;

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
            mTrack = DataProvider.ITEM_MAP.get(getArguments().getInt(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.track_detail, container, false);

        trackView = new TrackView(mTrack, rootView);

        updateViews();

        return rootView;
    }

    public void updateViews() {
        trackView.updateViews();

        boolean isTwoPane = ((TwoPaneableActivity) getActivity()).isTwoPane();

        trackView.getTitleView().setVisibility(isTwoPane ? View.VISIBLE : View.GONE);
        trackView.getImageView().setVisibility(isTwoPane ? View.VISIBLE : View.GONE);

        if (isTwoPane) trackView.loadImage(getContext(), false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //initMediaPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        TrackMediaPlayer.releasePlayer();
    }

    private void initMediaPlayer() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                TrackMediaPlayer.releasePlayer();
                TrackMediaPlayer.getInstance().startPlayingTrack(mTrack);
            }
        };
        new Handler().post(r);
    }
}
