package io.rolgalan.musicsearch.model.itunes;

import java.util.LinkedList;

import io.rolgalan.musicsearch.model.Track;
import io.rolgalan.musicsearch.model.TracksList;
import io.rolgalan.musicsearch.server.model.ItunesObject;
import io.rolgalan.musicsearch.server.model.SearchResponse;

/**
 * Created by Roldán Galán on 11/11/2016.
 */

public class ItunesTracksList extends LinkedList<Track> implements TracksList {
    public ItunesTracksList(SearchResponse searchResponse) {
        super();
        filterSongs(searchResponse);
    }

    /**
     * A SearchResponse can contain different data types that we are not interested in.
     * Here we filter just the song tracks to add to this list.
     *
     * @param searchResponse
     */
    private void filterSongs(SearchResponse searchResponse) {
        final String wrapper = "track";
        final String kind = "song";

        if (searchResponse != null && searchResponse.getResultCount() > 0 && searchResponse.getResults() != null) {
            for (ItunesObject o : searchResponse.getResults()) {
                if (o != null && wrapper.equals(o.getWrapperType()) && kind.equals(o.getKind())) {
                    add(new ItunesTrack(o));
                }
            }
        }
    }
}
