package io.rolgalan.musicsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rolgalan.musicsearch.data.DataProvider;
import io.rolgalan.musicsearch.model.Track;
import io.rolgalan.musicsearch.view.TwoPaneableActivity;

/**
 * An activity representing a single Track detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link TrackListActivity}.
 */
public class TrackDetailActivity extends AppCompatActivity implements TwoPaneableActivity {
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.list_image)
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_detail);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(TrackDetailFragment.ARG_ITEM_ID, getIntent().getIntExtra(TrackDetailFragment.ARG_ITEM_ID, 0));
            TrackDetailFragment fragment = new TrackDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.track_detail_container, fragment)
                    .commit();
        }

        loadCoverOnToolbar(getIntent().getIntExtra(TrackDetailFragment.ARG_ITEM_ID, -1));
    }

    private void loadCoverOnToolbar(int pos) {
        if (pos < 0) return;

        Track track = DataProvider.ITEM_MAP.get(pos);
        if (track == null) return;

        String imgUrl = track.getArtworkUrl100();
        if (imgUrl != null && !imgUrl.isEmpty()) {
            Glide.with(this).load(imgUrl)
                    .fitCenter()
                    .placeholder(R.drawable.placeholder_256)
                    .crossFade()
                    .into(image);
        }
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
}
