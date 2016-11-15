package io.rolgalan.musicsearch.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;

import io.rolgalan.musicsearch.util.CompatUtils;

/**
 * Created by Roldán Galán on 14/11/2016.
 */

public class PlayPauseTransition extends TransitionDrawable {
    private final static int CROSSFADE_ANIM_DURATION = 600;
    private boolean animated = false;

    public PlayPauseTransition(Context context) {
        super(getLayers(context));
        setCrossFadeEnabled(true);
    }

    private static Drawable[] getLayers(Context context) {
        Drawable[] layers = {CompatUtils.getDrawable(context, android.R.drawable.ic_media_play), CompatUtils.getDrawable(context, android.R.drawable.ic_media_pause)};
        return layers;
    }

    public void animate() {
        if (animated) {
            pauseToPlayTransition();
        } else {
            playToPauseTransition();
        }
    }

    public void playToPauseTransition() {
        super.startTransition(CROSSFADE_ANIM_DURATION);
        animated = true;
    }

    public void pauseToPlayTransition() {
        super.reverseTransition(CROSSFADE_ANIM_DURATION);
        animated = false;
    }
}
