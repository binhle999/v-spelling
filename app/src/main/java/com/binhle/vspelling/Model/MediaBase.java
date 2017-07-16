package com.binhle.vspelling.model;

import android.app.Activity;
import android.media.MediaPlayer;

/**
 * Created by BinhLe on 6/17/2017.
 */
public class MediaBase {
    private MediaPlayer mediaPlayer;
    private int imageResourceId;
    private int viewId;

    public MediaBase() {
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(Activity activity, int resourceId) {
        if (resourceId > 0) {
            this.mediaPlayer = MediaPlayer.create(activity, resourceId);
        }
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        if (imageResourceId > 0) {
            this.imageResourceId = imageResourceId;
        }
    }
}
