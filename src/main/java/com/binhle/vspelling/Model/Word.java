package com.binhle.vspelling.Model;

/**
 * Created by BinhLe on 5/1/2017.
 */
public class Word extends CustomMedia {
    private String id;
    private String name;
    private String displayText;
    private String description;
    private String imagePath;
    private String soundPath;
    private String letterId;

    public Word(String id, String name, String displayText,
                String description, String imagePath, String soundPath, String letterId) {
        this.id = id;
        this.name = name;
        this.displayText = displayText;
        this.description = description;
        this.imagePath = imagePath;
        this.soundPath = soundPath;
        this.letterId = letterId;
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

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
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

    public String getLetterId() {
        return letterId;
    }

    public void setLetterId(String letterId) {
        this.letterId = letterId;
    }
}
