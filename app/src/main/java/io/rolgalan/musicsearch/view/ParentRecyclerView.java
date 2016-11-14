package io.rolgalan.musicsearch.view;

import android.support.v4.app.FragmentManager;

/**
 * Created by Roldán Galán on 11/11/2016.
 */
public interface ParentRecyclerView extends TwoPaneableActivity {

    FragmentManager getSupportFragmentManager();

    void onTrackSelected();
}
