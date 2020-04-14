package project.main.model;

import java.io.Serializable;

public class GroupMovie implements Serializable {
    private int groupId;
    private int movieId;

    public GroupMovie() {
    }

    public GroupMovie(int groupId, int movieId) {
        this.groupId = groupId;
        this.movieId = movieId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
