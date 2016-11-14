package io.rolgalan.musicsearch.server.model;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Roldán Galán on 14/11/2016.
 */
public class ItunesObjectTest {

    @Test
    public void test_createItunesObject() throws Exception {
        final String release = "2013-04-03T07:00:00Z";
        final int time = 256;
        ItunesObject io = createItunesObject(release, time);

        assertEquals(io.getReleaseDate(), release);
        assertEquals(io.getTrackTimeMillis(), time);
        assertNotEquals(io.getTrackTimeMillis(), time - 50);
        assertNotEquals(io.getReleaseDate(), "asdf" + release);
    }

    public static ItunesObject createItunesObject(String releaseDate, int timeMillis) {
        ItunesObject io = ItunesObject.fake();
        setTimeMillis(io, timeMillis);
        setReleaseDate(io, releaseDate);
        return io;
    }

    public static void setTimeMillis(ItunesObject io, int timeMillis) {
        try {
            Field field = io.getClass().getDeclaredField("trackTimeMillis");
            field.setAccessible(true);
            field.set(io, timeMillis);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void setReleaseDate(ItunesObject io, String releaseDate) {
        try {
            Field field = io.getClass().getDeclaredField("releaseDate");
            field.setAccessible(true);
            field.set(io, releaseDate);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static ItunesObject createItunesObjectForPrice(double trackPrice, String currency) {
        ItunesObject io = ItunesObject.fake();
        setTrackPrice(io, trackPrice);
        setCurrency(io, currency);
        return io;
    }

    public static void setTrackPrice(ItunesObject io, double trackPrice) {
        try {
            Field field = io.getClass().getDeclaredField("trackPrice");
            field.setAccessible(true);
            field.set(io, trackPrice);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setCurrency(ItunesObject io, String currency) {
        try {
            Field field = io.getClass().getDeclaredField("currency");
            field.setAccessible(true);
            field.set(io, currency);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    //  return itunesObject.getTrackPrice() + itunesObject.getCurrency();
}
