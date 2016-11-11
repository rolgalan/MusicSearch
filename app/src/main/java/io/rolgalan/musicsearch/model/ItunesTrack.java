package io.rolgalan.musicsearch.model;

import io.rolgalan.musicsearch.server.model.ItunesObject;

/**
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
        return itunesObject.getReleaseDate();
    }

    @Override
    public String getPreviewUrl() {
        return itunesObject.getPreviewUrl();
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
}
