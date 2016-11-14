package io.rolgalan.musicsearch.model;

import java.util.Date;

/**
 * Core model data consumed by the app.
 * Represents all the information displayable or required for a certaing track song.
 * <p>
 * Created by Roldán Galán on 11/11/2016.
 */

public interface Track {
    String getArtistName();

    String getTrackName();

    String getCollectionName();

    String getArtworkUrl60();

    String getArtworkUrl100();

    Date getReleaseDate();

    String getReleaseDateHuman();

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
