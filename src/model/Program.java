package model;

public class Program {

    private String title;
    private String description;
    private String startTime;
    private String endTime;
    private String img;

    public Program(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartTime(String startTime) {
        startTime = startTime.replaceAll("T", " ");
        startTime = startTime.replaceAll("Z", "");
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        endTime = endTime.replaceAll("T", " ");
        endTime = endTime.replaceAll("Z", "");
        this.endTime = endTime;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getImg() {
        return img;
    }
}
