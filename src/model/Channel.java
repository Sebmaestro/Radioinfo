package model;

public class Channel {
    private String name;
    private String ID;
    private String description;
    private String picture;

    public Channel(String name, String ID) {
        this.name = name;
        this.ID = ID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }
}
