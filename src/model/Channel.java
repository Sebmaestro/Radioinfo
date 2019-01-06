package model;

/**
 * Author: Sebastian Arledal
 * 2019-01-06
 *
 * Class Channel:
 *
 * Holds channel info
 */
public class Channel {
    private String name;
    private String ID;
    private String description;
    private String picture;

    /**
     * Constructor:
     *
     * Sets name and ID for channel
     * @param name - name of channel
     * @param ID - ID of channel
     */
    public Channel(String name, String ID) {
        this.name = name;
        this.ID = ID;
    }

    /**
     * Method setDescription:
     *
     * Sets the description
     * @param description - the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method setPicture:
     *
     * Sets the picture
     * @param picture - the picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * Method getName:
     *
     * Gets name
     * @return name - the name
     */
    public String getName() {
        return name;
    }

    /**
     * Method getID:
     *
     * Gets ID
     * @return ID - the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Method getDescription:
     *
     * Gets description
     * @return description - the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method getPicture:
     *
     * Gets picture
     * @return - picture - the picture
     */
    public String getPicture() {
        return picture;
    }
}
