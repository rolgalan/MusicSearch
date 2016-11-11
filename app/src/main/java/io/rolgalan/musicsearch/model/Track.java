package io.rolgalan.musicsearch.model;

/**
 * Created by Roldán Galán on 11/11/2016.
 */

public interface Track {
    String getArtistName();

    String getTrackName();

    String getCollectionName();

    String getArtworkUrl60();

    String getArtworkUrl100();

    String getReleaseDate();

    String getPreviewUrl();

    String getTrackPriceWithCurrency();

    double getTrackPrice();

    double getCollectionPrice();

    int getTrackTimeMillis();

    String getTrackTime();

    String getCountry();

    String getCurrency();

    String getPrimaryGenreName();

}
