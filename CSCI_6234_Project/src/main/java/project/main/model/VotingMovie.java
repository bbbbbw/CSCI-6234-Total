package project.main.model;

import java.io.Serializable;

public class VotingMovie implements Serializable {
    private int votingEventId;
    private int movieId;

    public VotingMovie() {
    }

    public VotingMovie(int votingEventId, int movieId) {
        this.votingEventId = votingEventId;
        this.movieId = movieId;
    }

    public int getVotingEventId() {
        return votingEventId;
    }

    public void setVotingEventId(int votingEventId) {
        this.votingEventId = votingEventId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
