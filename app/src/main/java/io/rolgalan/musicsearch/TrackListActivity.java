package io.rolgalan.musicsearch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rolgalan.musicsearch.model.Track;
import io.rolgalan.musicsearch.model.itunes.ItunesTracksList;
import io.rolgalan.musicsearch.server.ApiManager;
import io.rolgalan.musicsearch.server.SearchResponseInterface;
import io.rolgalan.musicsearch.server.model.SearchResponse;

import io.rolgalan.musicsearch.data.DataProvider;
import io.rolgalan.musicsearch.view.ParentRecyclerView;
import io.rolgalan.musicsearch.view.SimpleItemRecyclerViewAdapter;

/**
 * An activity for searching {@link Track} and representing a list of them.
 * This activity has different presentations for handset and tablet-size devices.
 * On handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TrackDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TrackListActivity extends AppCompatActivity implements ParentRecyclerView {
    public static final String TAG = "Music";

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.floating_search_view)
    FloatingSearchView searchView;
    @BindView(R.id.track_list)
    RecyclerView recyclerView;

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Remove. Just for search tracks faster while developing
                searchQuery("Michael Jackson");
            }
        });

        setupRecyclerView();

        setupSearchView();

        checkTwoPane();
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
        ((TextView) searchView.findViewById(R.id.search_bar_text)).setImeOptions(EditorInfo.IME_ACTION_SEARCH);

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
    }

    private void searchQuery(String query) {
        //TODO Do not generate new response interface each time
        ApiManager.getInstance().tracksSearch(query, new SongsSearchResponseInterface());

    }

    @Override
    public boolean isTwoPane() {
        return mTwoPane;
    }

    /**
     * TODO Make this class static with a weak reference for performance's sake
     */
    private final class SongsSearchResponseInterface implements SearchResponseInterface {

        @Override
        public void onResultsReceived(SearchResponse response) {
            Log.i(TAG, "results: " + response.getResultCount());
            DataProvider.clear();
            int i = 0;
            for (Track t : new ItunesTracksList(response)) {
                DataProvider.addTrack(i++, t);
            }
            recyclerView.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void onError(String error) {
            Snackbar.make(fab, error, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    private void setupRecyclerView() {
        assert recyclerView != null;
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DataProvider.ITEMS));
    }
}
