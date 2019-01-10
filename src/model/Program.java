package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Author: Sebastian Arledal c17sal
 * 2019-01-06
 *
 * Class Program:
 *
 * Class to hold info about a program
 */
public class Program {

    private String title;
    private String description;
    private String startTime;
    private String endTime;
    private String img;

    /**
     * Constructor:
     *
     * Sets title for program
     * @param title - the title
     */
    public Program(String title) {
        this.title = title;
    }

    /**
     * Method setDescription:
     *
     * Sets description of the program
     * @param description - the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method setstartTime:
     *
     * Sets start time of program and finetunes string
     * @param startTime - the start time
     */
    public void setStartTime(String startTime) {

        startTime = startTime.replaceAll("T", " ");
        startTime = startTime.replaceAll("Z", "");

        //Add one hour to the program
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = sdf.parse(startTime);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            this.startTime = sdf.format(date);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method setEndTime:
     *
     * Sets end time of program and finetunes string
     * @param endTime - the end time
     */
    public void setEndTime(String endTime) {
        endTime = endTime.replaceAll("T", " ");
        endTime = endTime.replaceAll("Z", "");

        //Add one hour to the program
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = sdf.parse(endTime);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            this.endTime = sdf.format(date);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method setImg:
     *
     * Sets img for program
     * @param img - the img
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * Method getTitle:
     *
     * Gets title of program
     * @return title - the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method getDescription:
     *
     * Gets description of program
     * @return description - the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method getstartTime:
     *
     * Gets start time of program
     * @return startTime - the start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Method getEndTime:
     *
     * Gets end time of program
     * @return endTime - the end time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Method getImg:
     *
     * Gets img from program
     * @return img - the img
     */
    public String getImg() {
        return img;
    }
}
