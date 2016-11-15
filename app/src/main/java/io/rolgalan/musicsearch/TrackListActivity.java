package io.rolgalan.musicsearch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.rolgalan.musicsearch.data.DataProvider;
import io.rolgalan.musicsearch.model.Track;
import io.rolgalan.musicsearch.model.itunes.ItunesTracksList;
import io.rolgalan.musicsearch.server.ApiManager;
import io.rolgalan.musicsearch.server.SearchResponseInterface;
import io.rolgalan.musicsearch.server.model.SearchResponse;
import io.rolgalan.musicsearch.util.TrackMediaPlayer;
import io.rolgalan.musicsearch.view.ParentRecyclerView;
import io.rolgalan.musicsearch.view.PlayPauseTransition;
import io.rolgalan.musicsearch.view.SimpleItemRecyclerViewAdapter;
import io.rolgalan.musicsearch.view.TwoPaneableActivity;
import io.rolgalan.musicsearch.view.PlayPauseTransition;

/**
 * An activity for searching {@link Track} and representing a list of them.
 * This activity has different presentations for handset and tablet-size devices.
 * On handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TrackDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TrackListActivity extends AppCompatActivity implements ParentRecyclerView, SearchResponseInterface, TwoPaneableActivity {
    public static final String TAG = "Music";

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.floating_search_view)
    FloatingSearchView searchView;
    @BindView(R.id.track_list)
    RecyclerView recyclerView;
    @BindView(R.id.search_bar_text)
    EditText searchBarText;

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
        mTwoPane = findViewById(R.id.track_detail_container) != null;
    }

    private void setupSearchView() {
        //Fix bug in library for som Android versions https://github.com/arimorty/floatingsearchview/issues/159
        searchBarText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchBarText.setSingleLine();

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                Log.d(TAG, "onSearchTextChanged() " + newQuery);

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    searchView.clearSuggestions();
                } else {
                    //TODO load suggestions based on newQuery
                    //searchView.showProgress();
                    //and pass them with searchView.swapSuggestions(newSuggestions);
                }
            }
        });

        searchBarText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String mLastQuery = textView.getText().toString();
                Log.d(TAG, "onEditorAction() " + mLastQuery);
                searchQuery(mLastQuery);
                return true;
            }
        });

        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                String mLastQuery = searchSuggestion.getBody();
                Log.d(TAG, "onSuggestionClicked() " + mLastQuery);
                searchQuery(mLastQuery);
            }

            @Override
            public void onSearchAction(String query) {
                Log.d(TAG, "onSearchAction() " + query);
                //mLastQuery = query;
                searchQuery(query);
            }
        });

        searchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                Log.d(TAG, "onKeyListener() " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Log.d(TAG, "onKeyListener() KEYCODE_ENTER");
                    //return true;
                }
                return false;
            }
        });
    }

    private void searchQuery(String query) {
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
    public void onTrackSelected() {
        fab.animate()
                .translationYBy(-256)
                .setDuration(300)
                .setInterpolator(new LinearInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        transition.animate();
                    }
                }).start();
    }
}
