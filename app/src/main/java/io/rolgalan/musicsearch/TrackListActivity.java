package io.rolgalan.musicsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.arlib.floatingsearchview.FloatingSearchView;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.rolgalan.musicsearch.data.DataProvider;
import io.rolgalan.musicsearch.model.Track;
import io.rolgalan.musicsearch.model.itunes.ItunesTracksList;
import io.rolgalan.musicsearch.server.ApiManager;
import io.rolgalan.musicsearch.server.SearchResponseInterface;
import io.rolgalan.musicsearch.server.model.SearchResponse;
import io.rolgalan.musicsearch.util.TrackMediaPlayer;
import io.rolgalan.musicsearch.view.MyFloatingSearchView;
import io.rolgalan.musicsearch.view.ParentRecyclerView;
import io.rolgalan.musicsearch.view.PlayPauseTransition;
import io.rolgalan.musicsearch.view.SimpleItemRecyclerViewAdapter;
import io.rolgalan.musicsearch.view.TrackDetailViewPager;
import io.rolgalan.musicsearch.view.TwoPaneableActivity;

/**
 * An activity for searching {@link Track} and representing a list of them.
app/src/main/java/io/rolgalan/musicsearch/TrackListActivity.java * This activity has different presentations for handset and tablet-size devices.
 * On handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TrackDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TrackListActivity extends AppCompatActivity implements ParentRecyclerView, SearchResponseInterface, TwoPaneableActivity, TrackDetailViewPager.OnPageSelectedListener, MyFloatingSearchView.SearchQueryListener {
    public static final String TAG = "Music";

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.floating_search_view)
    MyFloatingSearchView searchView;
    @BindView(R.id.track_list)
    RecyclerView recyclerView;
    @BindView(R.id.search_bar_text)
    EditText searchBarText;
    @Nullable
    @BindView(R.id.track_detail_viewpager)
    TrackDetailViewPager viewPager;

    private PlayPauseTransition transition;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_track_list);

        ButterKnife.bind(this);

        checkTwoPane();

        setupRecyclerView();

        setupSearchView();

        setupFloatingButton();
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

        fab.animate().translationYBy(256).setDuration(0).start();
    }

    /**
     * The detail container view will be present only in the
     * large-screen layouts (res/values-w900dp).
     * If this view is present, then the activity should be in two-pane mode.
     */
    private void checkTwoPane() {
        mTwoPane = viewPager != null;
    }

    private void setupSearchView() {
        searchView.init(searchBarText, this);

        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.sort_length:
                        DataProvider.sortByLength();
                        break;
                    case R.id.sort_genre:
                        DataProvider.sortByGenre();
                        break;
                    case R.id.sort_price:
                        DataProvider.sortByPrice();
                        break;
                    case R.id.sort_original:
                        DataProvider.sortByOriginalReceived();
                        break;
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    public void searchQuery(String query) {
        searchView.showProgress();
        ApiManager.getInstance().tracksSearch(query, this);
        //TODO save last queries for a search suggestion system
    }

    @Override
    public boolean isTwoPane() {
        return mTwoPane;
    }

    @Override
    public void onResultsReceived(SearchResponse response) {
        Log.i(TAG, "results: " + response.getResultCount());

        DataProvider.setItunesTracksList(new ItunesTracksList(response));

        recyclerView.getAdapter().notifyDataSetChanged();
        searchView.hideProgress();
        if (searchView.isSearchBarFocused()) {
            searchView.clearSearchFocus();
        }
    }

    @Override
    public void onError(String error) {
        Snackbar.make(fab, error, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    private void setupRecyclerView() {
        assert recyclerView != null;
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DataProvider.ITEMS));
    }

    @Override
    public void onTrackSelected(int position) {
        if (isTwoPane()) {
            if (viewPager.isInitialized()) {
                viewPager.setCurrentItem(position, true);
            } else {
                viewPager.init(position, getSupportFragmentManager(), this);
                showFabButton();
            }
        } else {
            Intent intent = new Intent(this, TrackDetailActivity.class);
            intent.putExtra(TrackDetailFragment.ARG_ITEM_ID, position);
            startActivity(intent);
        }
    }

    private void showFabButton() {
        fab.animate()
                .translationYBy(-256)
                .setDuration(300)
                .setInterpolator(new LinearInterpolator())
                .start();
    }

    @Override
    public void onPageSelected(int position) {
        TrackMediaPlayer.initMediaPlayer(DataProvider.getTrack(position));
        //TODO Add a listener to mediaplayer to start this animation
        transition.playToPauseTransition();
    }
}
