package io.rolgalan.musicsearch.model.itunes;

import org.junit.Test;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import io.rolgalan.musicsearch.model.Track;
import io.rolgalan.musicsearch.server.model.ItunesObject;
import io.rolgalan.musicsearch.server.model.ItunesObjectTest;

import static org.junit.Assert.assertEquals;

/**
 * Created by Roldán Galán on 14/11/2016.
 */
public class ItunesTrackTest {
    @Test
    public void getTrackTime() throws Exception {
        assertTime(310, "05:10");
        assertTime(0, "00");
        assertTime(60, "01:00");
        assertTime(3600, "01:00:00");
        assertTime(49, "49");
        assertTime(7 + 12 * 60, "12:07");
        assertTime(40 * 60, "40:00");
        assertTime(2 + 8 * 60 + 15 * 3600, "15:08:02");
        assertTime(2 + 8 * 60 + 15 * 3600 + 9 * 24 * 3600, "9 days 15:08:02");
    }

    private void assertTime(int timeSeconds, String formatted) {
        ItunesObject obj = ItunesObjectTest.createItunesObject("", 1000 * timeSeconds);
        assertEquals(formatted, new ItunesTrack(obj).getTrackTime());
    }

    @Test
    public void getReleaseDateHuman() throws Exception {
        ItunesObject obj = ItunesObjectTest.createItunesObject("2013-04-03T07:00:00Z", 0);
        Track track = new ItunesTrack(obj);
        assertEquals("04/03/2013", track.getReleaseDateHuman());

        ItunesObjectTest.setReleaseDate(obj, "2004-02-29T07:00:00Z");
        track = new ItunesTrack(obj);
        assertEquals("02/29/2004", track.getReleaseDateHuman());
    }

    @Test
    public void getReleaseDate() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        cal.set(2004, 01, 29, 7, 00);
        assertDate("2004-02-29T07:00:00Z", cal);
        cal.set(2013, 03, 03, 7, 00);
        assertDate("2013-04-03T07:00:00Z", cal);
    }

    private void assertDate(String dateCreation, Calendar cal) {
        ItunesObject obj = ItunesObjectTest.createItunesObject(dateCreation, 0);
        Date date = new Date(cal.getTimeInMillis());
        assertEquals(date, new ItunesTrack(obj).getReleaseDate());
    }

    @Test
    public void getTrackPriceWithCurrency() throws Exception {
        DecimalFormat df = new DecimalFormat();
        String separator = String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator());

        Track track = new ItunesTrack(ItunesObjectTest.createItunesObjectForPrice(0.99, "€"));
        assertEquals("0" + separator + "99€", track.getTrackPriceWithCurrency());

        track = new ItunesTrack(ItunesObjectTest.createItunesObjectForPrice(0.991787, "€"));
        assertEquals("0" + separator + "99€", track.getTrackPriceWithCurrency());

        track = new ItunesTrack(ItunesObjectTest.createItunesObjectForPrice(12, "€"));
        assertEquals("12" + separator + "00€", track.getTrackPriceWithCurrency());

        track = new ItunesTrack(ItunesObjectTest.createItunesObjectForPrice(2.98, "$"));
        assertEquals("2" + separator + "98$", track.getTrackPriceWithCurrency());
    }

    @Test
    public void getTrackTimeMillis() throws Exception {
        ItunesObject obj = ItunesObjectTest.createItunesObject("", 530);
        Track track = new ItunesTrack(obj);
        assertEquals(530, track.getTrackTimeMillis());
    }


}
