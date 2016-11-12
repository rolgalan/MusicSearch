package io.rolgalan.musicsearch.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rolgalan.musicsearch.model.Track;

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
}