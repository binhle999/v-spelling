package com.binhle.vspelling.Model;

/**
 * Created by BinhLe on 5/1/2017.
 */
public class Letter extends CustomMedia {
    private String id;
    private String name;
    private String description;
    private String imagePath;
    private String soundPath;

    public Letter() {
        super();
    }

    public Letter( String id, String name, String description, String imagePath, String soundPath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.soundPath = soundPath;
    }

    public Letter( String id, String imagePath) {
        this.id = id;
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }
}
