package io.rolgalan.musicsearch.model.itunes;

import io.rolgalan.musicsearch.model.Track;
import io.rolgalan.musicsearch.server.model.ItunesObject;

/**
 * Implementation of Track for iTunes objects.
 * <p>
 * Adds formatting for some fields.
 * <p>
 * Created by Roldán Galán on 10/11/2016.
 */

public class ItunesTrack implements Track {
    private final ItunesObject itunesObject;

    public ItunesTrack(ItunesObject itunesObject) {
        this.itunesObject = itunesObject;
    }

    @Override
    public String getArtistName() {
        return itunesObject.getArtistName();
    }

    @Override
    public String getTrackName() {
        return itunesObject.getTrackName();
    }

    @Override
    public String getCollectionName() {
        return itunesObject.getCollectionName();
    }

    @Override
    public String getArtworkUrl60() {
        return itunesObject.getArtworkUrl60();
    }

    @Override
    public String getArtworkUrl100() {
        return itunesObject.getArtworkUrl100();
    }

    @Override
    public String getReleaseDate() {
        //TODO human format
        return itunesObject.getReleaseDate();
    }

    @Override
    public String getPreviewUrl() {
        return itunesObject.getPreviewUrl();
    }

    @Override
    public String getTrackPriceWithCurrency() {
        //TODO handle different currencies properly (UK pounds go before!)
        return itunesObject.getTrackPrice() + itunesObject.getCurrency();
    }

    @Override
    public double getTrackPrice() {
        return itunesObject.getTrackPrice();
    }

    @Override
    public double getCollectionPrice() {
        return itunesObject.getCollectionPrice();
    }

    @Override
    public int getTrackTimeMillis() {
        return itunesObject.getTrackTimeMillis();
    }

    @Override
    public String getTrackTime() {
        //TODO human format
        return String.valueOf(getTrackTimeMillis());
    }

    @Override
    public String getCountry() {
        return itunesObject.getCountry();
    }

    @Override
    public String getCurrency() {
        return itunesObject.getCurrency();
    }

    @Override
    public String getPrimaryGenreName() {
        return itunesObject.getPrimaryGenreName();
    }

    @Override
    public String toString() {
        return "ItunesTrack " + getTrackName() + " - " + getArtistName() + " (" + getTrackTimeMillis() + ")";
    }
}
