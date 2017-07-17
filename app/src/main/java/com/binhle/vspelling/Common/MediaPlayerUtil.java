package com.binhle.vspelling.common;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.util.List;
import java.util.Map;

/**
 * Created by BinhLe on 4/9/2017.
 */
public final class MediaPlayerUtil {

    public static void playSound(MediaPlayer player, boolean isLoop, float leftVol, float rightVol) {
        player.setLooping(isLoop);
        player.setVolume(leftVol, rightVol);
        player.start();
    }

    /**
     *
     * @param activity
     * @param resourceId
     * @return
     */
    public static MediaPlayer buildMediaPlayer(Activity activity, int resourceId) {
        if (activity != null && resourceId > 0) {
            return MediaPlayer.create(activity, resourceId);
        }
        return null;
    }
    public static void playSound(MediaPlayer player) {
        if (player != null) {
            player.start();
        }
    }

    public static void stopSound(MediaPlayer player) {
        if (player != null) {
            player.stop();
        }
    }


    public static MediaPlayer initMediaPlayer(Context context, Uri uri) {
        return MediaPlayer.create(context, uri);
    }

    public static void setUpMediaPlayer(MediaPlayer mediaPlayer,
                                        Map<MediaPlayerConstants.MediaPlayerSettting, List<String>> settingMap) {
        for (Map.Entry<MediaPlayerConstants.MediaPlayerSettting, List<String>>
                entry : settingMap.entrySet()) {
            setUpForSettingEntry(mediaPlayer, entry);
        }
    }

    private static void setUpForSettingEntry(MediaPlayer mediaPlayer,
                                             Map.Entry<MediaPlayerConstants.MediaPlayerSettting, List<String>> entry) {
        switch (entry.getKey()) {
            case ISLOOPING:
                setUpLooping(mediaPlayer, entry.getValue());
                break;
            case VOLUME:
                setVolume(mediaPlayer, entry.getValue());
                break;
        }
    }

    private static void setUpLooping(MediaPlayer mediaPlayer, List<String> isLooping) {
        if (isLooping.size() >= 1) {
            mediaPlayer.setLooping(Boolean.parseBoolean(isLooping.get(0)));
        }

    }

    private static void setVolume(MediaPlayer mediaPlayer, List<String> volumeValue) {
        if (volumeValue.size() >= 1) {
            mediaPlayer.setVolume(
                    Float.parseFloat(volumeValue.get(0)), Float.parseFloat(volumeValue.get(1))
            );
        }
    }
}
