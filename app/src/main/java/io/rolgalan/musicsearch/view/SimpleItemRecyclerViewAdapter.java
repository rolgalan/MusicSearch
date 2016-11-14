package io.rolgalan.musicsearch.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.rolgalan.musicsearch.R;
import io.rolgalan.musicsearch.TrackDetailActivity;
import io.rolgalan.musicsearch.TrackDetailFragment;
import io.rolgalan.musicsearch.model.Track;

/**
 * Created by Roldán Galán on 11/11/2016.
 */

public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final List<Track> mValues;
    private final ParentRecyclerView parent;

    public SimpleItemRecyclerViewAdapter(ParentRecyclerView parent, List<Track> items) {
        this.parent = parent;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setTrack(mValues.get(position));
        holder.updateViews();

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parent.isTwoPane()) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(TrackDetailFragment.ARG_ITEM_ID, position);
                    TrackDetailFragment fragment = new TrackDetailFragment();
                    fragment.setArguments(arguments);
                    parent.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.track_detail_container, fragment)
                            .commit();
                    parent.onTrackSelected();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, TrackDetailActivity.class);
                    intent.putExtra(TrackDetailFragment.ARG_ITEM_ID, position);

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
        private View mView;
        private TrackView trackView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            trackView = new TrackView(mView);
        }

        public void updateViews() {
            trackView.updateViews();
            trackView.loadImage(mView.getContext(), true);
        }

        public void setTrack(Track track) {
            trackView .setTrack(track);
        }
    }
}
