package io.rolgalan.musicsearch;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rolgalan.musicsearch.data.DataProvider;
import io.rolgalan.musicsearch.model.Track;
import io.rolgalan.musicsearch.view.TwoPaneableActivity;

/**
 * A fragment representing a single Track detail screen.
 * This fragment is either contained in a {@link TrackListActivity}
 * in two-pane mode (on tablets) or a {@link TrackDetailActivity}
 * on handsets.
 */
public class TrackDetailFragment extends Fragment {
    @BindView(R.id.list_album)
    TextView album;
    @BindView(R.id.list_artist)
    TextView artist;
    @BindView(R.id.list_date)
    TextView date;
    @BindView(R.id.list_genre)
    TextView genre;
    @BindView(R.id.list_length)
    TextView length;
    @BindView(R.id.list_price)
    TextView price;
    @BindView(R.id.list_title)
    TextView title;
    @BindView(R.id.list_image)
    ImageView image;

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

        ButterKnife.bind(this, rootView);
        updateViews();

        if (mTrack != null) {
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mTrack.getTrackName());
            }
        }

        return rootView;
    }

    public void updateViews() {
        album.setText(mTrack.getCollectionName());
        artist.setText(mTrack.getArtistName());
        date.setText(mTrack.getReleaseDateHuman());
        genre.setText(mTrack.getPrimaryGenreName());
        length.setText(mTrack.getTrackTime());
        price.setText(mTrack.getTrackPriceWithCurrency());

        title.setText(mTrack.getTrackName());

        twoPaneAdjusts();
    }

    private void twoPaneAdjusts() {
        boolean isTwoPane = ((TwoPaneableActivity) getActivity()).isTwoPane();

        title.setVisibility(isTwoPane ? View.VISIBLE : View.GONE);
        image.setVisibility(isTwoPane ? View.VISIBLE : View.GONE);

        String imgUrl = mTrack.getArtworkUrl100();
        if (isTwoPane && imgUrl != null && !imgUrl.isEmpty()) {
            Glide.with(this).load(imgUrl)
                    .fitCenter()
                    .placeholder(R.drawable.placeholder_256)
                    .crossFade()
                    .into(image);
        }
    }
}
