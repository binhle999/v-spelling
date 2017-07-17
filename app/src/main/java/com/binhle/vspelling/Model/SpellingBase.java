package com.binhle.vspelling.model;

import android.app.Activity;

import com.binhle.vspelling.common.ResourceUtil;

/**
 * The SpellingBase class
 * Created by BinhLe on 7/15/2017.
 */
public class SpellingBase {
    // The id
    private int id;
    // The name
    private String name;
    // The content
    private String content;
    // The image resource id
    private String image;
    // The sound resource id
    private String sound;

    /**
     * The constructor
     * @param id
     * @param name
     * @param content
     * @param image
     * @param sound
     */
    public SpellingBase(int id, String name, String content, String image, String sound) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.image = image;
        this.sound = sound;
    }

    /**
     * Get the id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the content
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the content
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get the image resource id
     * @return
     */
    public String getImage() {
        return image;
    }

    /**
     * Set the image resource id
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Get the sound resource id
     * @return
     */
    public String getSound() {
        return sound;
    }

    /**
     * Set the sound resource id
     * @param sound
     */
    public void setSound(String sound) {
        this.sound = sound;
    }

//    /**
//     * Set image resource id
//     * @param activity
//     */
//    public void updateImageResourceId(Activity activity) {
//        int resId = ResourceUtil.getImageResource(activity, getImage());
//        if (resId > 0) {
//            super.setImageResourceId(resId);
//        }
//    }
//
//    /**
//     * Set media player
//     * @param activity
//     */
//    public void updateMediaPlayer(Activity activity) {
//        int resId = ResourceUtil.getSoundResource(activity, getSound());
//        if (resId > 0) {
//            super.setMediaPlayer(activity, resId);
//        }
//    }
}
