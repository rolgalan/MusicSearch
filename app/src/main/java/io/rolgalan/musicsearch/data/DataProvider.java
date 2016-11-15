package io.rolgalan.musicsearch.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private static final Map<Integer, Track> ITEM_MAP = new HashMap<>();

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
            addTrack(i++, t);
        }
    }

    public static Track getTrack(int position) {
        if (position < 0) return null;
        return ITEMS.get(position);
    }

    public static void sortByOriginalReceived() {
        ITEMS.clear();
        for (int i = 0; i < ITEM_MAP.size(); i++) {
            ITEMS.add(ITEM_MAP.get(i));
        }
    }

    public static void sortByGenre() {
        sortList(getGenreComparator());
    }

    public static void sortByLength() {
        sortList(getTimeComparator());
    }

    public static void sortByPrice() {
        sortList(getPriceComparator());
    }

    private static void sortList(Comparator<Track> comparator) {
        Collections.sort(ITEMS, comparator);
    }

    private static boolean timeSort, priceSort, genreSort;

    private static Comparator<Track> getTimeComparator() {
        timeSort = !timeSort;
        return new Comparator<Track>() {
            @Override
            public int compare(Track lhs, Track rhs) {
                if (timeSort) {
                    return Long.valueOf(lhs.getTrackTimeMillis()).compareTo(Long.valueOf(rhs.getTrackTimeMillis()));
                } else {
                    return Long.valueOf(rhs.getTrackTimeMillis()).compareTo(Long.valueOf(lhs.getTrackTimeMillis()));
                }

            }
        };
    }

    private static Comparator<Track> getGenreComparator() {
        genreSort = !genreSort;
        return new Comparator<Track>() {
            @Override
            public int compare(Track lhs, Track rhs) {
                if (genreSort) {
                    return lhs.getPrimaryGenreName().compareTo(rhs.getPrimaryGenreName());
                } else {
                    return rhs.getPrimaryGenreName().compareTo(lhs.getPrimaryGenreName());
                }

            }
        };
    }

    private static Comparator<Track> getPriceComparator() {
        priceSort = !priceSort;
        return new Comparator<Track>() {
            @Override
            public int compare(Track lhs, Track rhs) {
                if (priceSort) {
                    return Double.compare(lhs.getTrackPrice(), rhs.getTrackPrice());
                } else {
                    return Double.compare(rhs.getTrackPrice(), lhs.getTrackPrice());
                }

            }
        };
    }
}
