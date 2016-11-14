package io.rolgalan.musicsearch.view;

/**
 * Current search&detail could be displayed on different activities
 * or in the same one, on a two pane mode.
 * <p>
 * Fragments and inner views in these activities must know which display
 * mode is active, in order to show/hide some components.
 * <p>
 * Created by Roldán Galán on 14/11/2016.
 */

public interface TwoPaneableActivity {
    boolean isTwoPane();
}
