package io.rolgalan.musicsearch.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rolgalan.musicsearch.model.Track;
import io.rolgalan.musicsearch.model.itunes.ItunesTracksList;

/**
 * Created by Roldán Galán on 11/11/2016.
 */

public class DataProvider {
    public static final List<Track> ITEMS = new ArrayList<>();
    public static final Map<Integer, Track> ITEM_MAP = new HashMap<>();

    public static void addTrack(int i, Track track) {
        ITEMS.add(track);
        ITEM_MAP.put(i, track);
    }

    public static void clear() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static void setItunesTracksList(ItunesTracksList list) {
        clear();
        int i = 0;
        for (Track t : list) {
            DataProvider.addTrack(i++, t);
        }
    }

    public static Track getTrack(int position) {
        if (position<0) return null;
        return DataProvider.ITEM_MAP.get(position);
    }
}
