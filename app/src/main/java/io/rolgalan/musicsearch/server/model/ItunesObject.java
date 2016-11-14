package io.rolgalan.musicsearch.server.model;

import java.util.UUID;

/**
 * This class represents all the fields received in responses from iTunes API.
 * For now, we're just interested in track song objects, but it could be
 * extended in the future to support artist/video/album searching.
 * <p>
 * Created by Roldán Galán on 10/11/2016.
 */

public class ItunesObject {
    private String wrapperType;
    private String kind;
    private String trackName;
    private String artistName;
    private String collectionName;
    private String artworkUrl60;
    private String artworkUrl100;
    private String releaseDate;
    private String previewUrl;
    private double trackPrice;
    private double collectionPrice;
    private String trackCount;
    private int trackTimeMillis;
    private String country;
    private String currency;
    private String primaryGenreName;
    private boolean isStreamable;

    //Other fields, probably not required yet.
    //Uncomment just the required fields for a better performance.
    /*
    private String viewURL;
    private int primaryGenreId;
    private String censoredName;
    private String explicitness;
    private String artistType;
    private String artistLinkUrl;
    private int artistId;
    private int amgArtistId;
    private String collectionType;
    private int collectionId;
    private String collectionCensoredName;
    private String artistViewUrl;
    private String collectionViewUrl;
    private String collectionExplicitness;
    private String copyright;
    private int trackId;
    private String trackCensoredName;
    private String trackViewUrl;
    private String artworkUrl30;
    private String trackExplicitness;
    private int discCount;
    private int discNumber;
    private int trackNumber;
    */

    public static ItunesObject fake() {
        ItunesObject io = new ItunesObject();
        io.wrapperType = "track";
        io.kind = "song";
        io.artistName = "artistName " + Long.toHexString(Double.doubleToLongBits(Math.random()));
        io.collectionName = "collectionName " + UUID.randomUUID().toString();
        io.trackName = "trackName " + Long.toHexString(Double.doubleToLongBits(Math.random()));
        io.primaryGenreName = "primaryGenreName " + Long.toHexString(Double.doubleToLongBits(Math.random()));
        io.trackPrice = (int) Math.round(Math.random() * 25);
        io.currency = "€";
        io.trackTimeMillis = 50000 + (int) Math.round(Math.random() * 120000);
        return io;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getWrapperType() {
        return wrapperType;
    }

    public String getKind() {
        return kind;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public double getTrackPrice() {
        return trackPrice;
    }

    public double getCollectionPrice() {
        return collectionPrice;
    }

    public String getTrackCount() {
        return trackCount;
    }

    public int getTrackTimeMillis() {
        return trackTimeMillis;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public boolean isStreamable() {
        return isStreamable;
    }

    @Override
    public String toString() {
        return "ItunesObject " + wrapperType + " - " + kind;
    }
}
