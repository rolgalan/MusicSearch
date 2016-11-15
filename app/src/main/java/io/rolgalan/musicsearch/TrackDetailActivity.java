package io.rolgalan.musicsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rolgalan.musicsearch.data.DataProvider;
import io.rolgalan.musicsearch.model.Track;
import io.rolgalan.musicsearch.util.TrackMediaPlayer;
import io.rolgalan.musicsearch.view.PlayPauseTransition;
import io.rolgalan.musicsearch.view.TrackDetailViewPager;
import io.rolgalan.musicsearch.view.TrackView;
import io.rolgalan.musicsearch.view.TwoPaneableActivity;

/**
 * An activity representing a single Track detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link TrackListActivity}.
 * <p>
 * This activity loads a ViewPager, so the user can navigate through
 * the list of items with a horizontal swipe movement.
 */
public class TrackDetailActivity extends AppCompatActivity implements TwoPaneableActivity, TrackDetailViewPager.OnPageSelectedListener {
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.list_image)
    ImageView image;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout appBarLayout;
    @BindView(R.id.track_detail_viewpager)
    TrackDetailViewPager viewPager;

    private PlayPauseTransition transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_detail);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setupFloatingButton();

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Starts our custom viewpager with the selected item to be loaded
        setViewPager(getIntent().getIntExtra(TrackDetailFragment.ARG_ITEM_ID, -1));
    }

    @Override
    public boolean isTwoPane() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, TrackListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageSelected(int position) {
        loadTrackOnToolbar(position);
    }

    private void setViewPager(int startingItem) {
        viewPager.init(startingItem, getSupportFragmentManager(), this);
    }

    private void setupFloatingButton() {
        transition = new PlayPauseTransition(this);
        fab.setImageDrawable(transition);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transition.animate();
                TrackMediaPlayer.getInstance().togglePause();
            }
        });

        transition.animate();
    }

    private void loadTrackOnToolbar(int pos) {
        if (pos < 0) return;

        Track track = DataProvider.ITEM_MAP.get(pos);

        if (track == null) return;

        TrackView.loadImage(this, false, image, track);
        appBarLayout.setTitle(track.getTrackName());
    }
}
