package io.rolgalan.musicsearch.server;

import android.util.Log;

import io.rolgalan.musicsearch.TrackListActivity;
import io.rolgalan.musicsearch.server.model.SearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Roldán Galán on 10/11/2016.
 */

public class ApiManager {
    private static ApiManager instance;

    private ApiManager() {
    }

    public static synchronized ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    public void tracksSearch(String term, final SearchResponseInterface listener) {
        Call<SearchResponse> call = RestClient.getClient().searchSongTracks(term);
        call.enqueue(new MyCallback(listener));
    }

    public void genericSearch(String term, final SearchResponseInterface listener) {
        Call<SearchResponse> call = RestClient.getClient().search(term);
        call.enqueue(new MyCallback(listener));
    }

    /**
     * Generic callback for common error handling.
     */
    private static class MyCallback implements Callback<SearchResponse> {
        private final SearchResponseInterface listener;

        private MyCallback(SearchResponseInterface listener) {
            this.listener = listener;
        }

        @Override
        public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
            Log.d(TrackListActivity.TAG, "MyCallback.onResponse success " + (response != null ? response.isSuccessful() : "responseNULL"));
            if (response != null) {

                if (response.isSuccessful() && response.body() != null) {
                    listener.onResultsReceived(response.body());
                } else {
                    Log.e(TrackListActivity.TAG, "MyCallback.onResponse error code (" + response.code() + ") " + response.errorBody());
                    listener.onError("Unknown sever error " + response.code());
                }

            } else {
                Log.e(TrackListActivity.TAG, "MyCallback.onResponse null");
                listener.onError("Invalid data received");
            }
        }

        @Override
        public void onFailure(Call<SearchResponse> call, Throwable t) {
            Log.e(TrackListActivity.TAG, "MyCallback.onFailure " + t);
            t.printStackTrace();
            listener.onError("Error requesting data to the server");
        }
    }
}
