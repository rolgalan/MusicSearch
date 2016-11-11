package io.rolgalan.musicsearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.rolgalan.musicsearch.dummy.DummyContent;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rolgalan.musicsearch.model.Track;
import io.rolgalan.musicsearch.model.itunes.ItunesTracksList;
import io.rolgalan.musicsearch.server.ApiManager;
import io.rolgalan.musicsearch.server.SearchResponseInterface;
import io.rolgalan.musicsearch.server.model.SearchResponse;

import static io.rolgalan.musicsearch.dummy.DummyContent.addItem;

/**
 * An activity representing a list of Tracks. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TrackDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TrackListActivity extends AppCompatActivity {
    public static final String TAG = "Music";

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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

        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                ApiManager.getInstance().tracksSearch("Michael Jackson", new SongsSearchResponseInterface());
            }
        });

        setupRecyclerView();

        if (findViewById(R.id.track_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private final class SongsSearchResponseInterface implements SearchResponseInterface {

        @Override
        public void onResultsReceived(SearchResponse response) {
            Log.i(TAG, "results: " + response.getResultCount());
            DummyContent.clear();
            int i = 0;
            for (Track t : new ItunesTracksList(response)) {
                DummyContent.DummyItem item = new DummyContent.DummyItem(String.valueOf(i++), t);
                addItem(item);
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
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.track_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(TrackDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        TrackDetailFragment fragment = new TrackDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.track_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, TrackDetailActivity.class);
                        intent.putExtra(TrackDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
