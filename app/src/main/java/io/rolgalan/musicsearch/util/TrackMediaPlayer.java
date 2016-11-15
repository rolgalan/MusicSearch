package io.rolgalan.musicsearch.util;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import io.rolgalan.musicsearch.TrackListActivity;
import io.rolgalan.musicsearch.model.Track;

/**
 * Created by Roldán Galán on 14/11/2016.
 */

public class TrackMediaPlayer extends MediaPlayer {
    private static TrackMediaPlayer instance;

    public static TrackMediaPlayer getInstance() {
        if (instance == null) instance = new TrackMediaPlayer();
        return instance;
    }

    private TrackMediaPlayer() {
        super();
        setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public static void releasePlayer() {
        if (instance != null) {
            getInstance().releaseThePlayer();
            instance = null;
        }
    }

    public void togglePause() {
        if (isPlaying()) {
            pause();
        } else {
            start();
        }
    }

    private void releaseThePlayer() {
        try {
            if (isPlaying()) pause();
            stop();
            release();
        } catch (Exception e) {
            Log.e(TrackListActivity.TAG, "Error releasing mediaplayer " + e + ": " + e.getMessage());
        }
    }

    /**
     * If a current media player instance exists is released, and
     * initialize a new one for this {@link Track}
     *
     * @param track The track to play
     */
    public static void initMediaPlayer(final Track track) {
        if (track != null) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    TrackMediaPlayer.releasePlayer();
                    TrackMediaPlayer.getInstance().startPlayingTrack(track);
                }
            };
            new Handler().post(r);
        }
    }

    /**
     * Set the datasource for this {@link Track}, prepare the mediaPlayer
     * asynchronously, and starts playing the track then
     *
     * @param track The track to be played
     */
    private void startPlayingTrack(Track track) {
        Log.i(TrackListActivity.TAG, "startPlayingTrack " + track.getTrackName());
        final String url = track.getPreviewUrl();
        try {
            setDataSource(url);
            setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    start();
                }
            });
            prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
