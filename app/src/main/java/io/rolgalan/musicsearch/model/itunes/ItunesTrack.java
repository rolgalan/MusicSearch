package io.rolgalan.musicsearch.model.itunes;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private static final SimpleDateFormat serverParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final SimpleDateFormat viewParser = new SimpleDateFormat("MM/dd/yyyy");
    private static final DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    private final ItunesObject itunesObject;
    private Date mDate;
    private String mLength;
    private String mStringFormatted;

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
    public Date getReleaseDate() {
        if (mDate == null) {
            if (itunesObject.getReleaseDate() != null && !itunesObject.getReleaseDate().isEmpty()) {
                try {
                    mDate = serverParser.parse(itunesObject.getReleaseDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return mDate;
    }

    @Override
    public String getPreviewUrl() {
        return itunesObject.getPreviewUrl();
    }

    @Override
    public String getTrackPriceWithCurrency() {
        //TODO handle different currencies properly (UK pounds should go before!)

        return decimalFormat.format(itunesObject.getTrackPrice()) + itunesObject.getCurrency();
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
        if (mLength == null) {
            int timeSecs = getTrackTimeMillis() / 1000;
            mLength = parseSecondsToHumanReadable(timeSecs);
        }
        return mLength;
    }

    private String parseSecondsToHumanReadable(int time) {
        if (time < 86400) {
            if (time < 60) {
                String aux = time < 10 ? "0" : "";
                return aux.concat(String.valueOf(time));
            }
            int quotient = time / 60;
            int remainder = time - quotient * 60;
            return parseSecondsToHumanReadable(quotient) + ":" + parseSecondsToHumanReadable(remainder);

        }
        int quotient = time / 86400;
        int remainder = time - quotient * 86400;
        String aux = " " + (quotient > 1 ? "days" : "day") + " ";
        return quotient + aux + parseSecondsToHumanReadable(remainder);
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

    public String getReleaseDateHuman() {
        if (mStringFormatted == null) {
            if (getReleaseDate() == null) {
                return "";
            }
            mStringFormatted = viewParser.format(getReleaseDate());
        }
        return mStringFormatted;
    }

    @Override
    public String toString() {
        return "ItunesTrack " + getTrackName() + " - " + getArtistName() + " (" + getTrackTimeMillis() + ")";
    }
}
