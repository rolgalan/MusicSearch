package io.rolgalan.musicsearch.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import io.rolgalan.musicsearch.R;

/**
 * Created by Roldán Galán on 15/11/2016.
 */

public class CompatUtils {
    public static Drawable getDrawable(Context context, int id){
        if(android.os.Build.VERSION.SDK_INT >= 21){
            return context.getResources().getDrawable(id, context.getTheme());
        } else {
            return context.getResources().getDrawable(id);
        }
    }
}
