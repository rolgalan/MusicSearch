package io.rolgalan.musicsearch.server;

import java.util.concurrent.TimeUnit;

import io.rolgalan.musicsearch.server.model.SearchResponse;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Roldán Galán on 10/11/2016.
 */

public class RestClient {
    private final static String BASE_URL = "https://itunes.apple.com/";
    private static ApiInterface apiInterface;

    public synchronized static ApiInterface getClient() {
        if (apiInterface == null) {
            final OkHttpClient clientAux = new OkHttpClient.Builder().build();

            OkHttpClient okClient = clientAux.newBuilder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiInterface = client.create(ApiInterface.class);
        }

        return apiInterface;
    }

    public interface ApiInterface {
        @GET("search")
        Call<SearchResponse> search(@Query("term") String term);

        @GET("search?entity=song")
        Call<SearchResponse> searchSongTracks(@Query("term") String term);
    }
}
