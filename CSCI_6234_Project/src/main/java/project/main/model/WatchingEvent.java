package project.main.model;

import java.io.Serializable;

public class WatchingEvent implements Serializable {
    private int id;
    private int groupId;
    private String startTime;
    private String endTime;
    private String title;
    private String message;

    public WatchingEvent() {
    }

    public WatchingEvent(int id, int groupId, String startTime, String endTime, String title, String message) {
        this.id = id;
        this.groupId = groupId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
