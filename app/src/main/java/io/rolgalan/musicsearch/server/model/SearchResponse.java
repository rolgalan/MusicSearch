package io.rolgalan.musicsearch.server.model;

import java.util.List;

/**
 * The iTunes API always returns an object with an array and total count
 * for any request.
 * <p>
 * Created by Roldán Galán on 10/11/2016.
 */

public class SearchResponse {
    private int resultCount;
    private List<ItunesObject> results;

    public int getResultCount() {
        return resultCount;
    }

    public List<ItunesObject> getResults() {
        return results;
    }
}
