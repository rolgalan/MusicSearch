package io.rolgalan.musicsearch.server;

import io.rolgalan.musicsearch.server.model.SearchResponse;

/**
 * Created by Roldán Galán on 11/11/2016.
 */

public interface SearchResponseInterface {

    void onResultsReceived(SearchResponse response);

    void onError(String error);
}
