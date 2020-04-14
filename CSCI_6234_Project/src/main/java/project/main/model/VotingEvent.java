package project.main.model;

import java.io.Serializable;

public class VotingEvent implements Serializable {
    private int id;
    private int watchingEventId;
    private String startAt;
    private String endAt;
    private int selectedMovieId;

    public VotingEvent() {
    }

    public VotingEvent(int id, int watchingEventId, String startAt, String endAt, int selectedMovieId) {
        this.id = id;
        this.watchingEventId = watchingEventId;
        this.startAt = startAt;
        this.endAt = endAt;
        this.selectedMovieId = selectedMovieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWatchingEventId() {
        return watchingEventId;
    }

    public void setWatchingEventId(int watchingEventId) {
        this.watchingEventId = watchingEventId;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public int getSelectedMovieId() {
        return selectedMovieId;
    }

    public void setSelectedMovieId(int selectedMovieId) {
        this.selectedMovieId = selectedMovieId;
    }
}
