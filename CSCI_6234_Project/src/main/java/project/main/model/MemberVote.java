package project.main.model;

import java.io.Serializable;

public class MemberVote implements Serializable {
    private int votingEventId;
    private int voterId;
    private int movieId;
    private int forOrAgainst; // Either 0 or 1. 1: vote for, 0: vote against

    public MemberVote() {
    }

    public MemberVote(int votingEventId, int voterId, int movieId, int forOrAgainst) {
        this.votingEventId = votingEventId;
        this.voterId = voterId;
        this.movieId = movieId;
        this.forOrAgainst = forOrAgainst;
    }

    public int getVotingEventId() {
        return votingEventId;
    }

    public void setVotingEventId(int votingEventId) {
        this.votingEventId = votingEventId;
    }

    public int getVoterId() {
        return voterId;
    }

    public void setVoterId(int voterId) {
        this.voterId = voterId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getForOrAgainst() {
        return forOrAgainst;
    }

    public void setForOrAgainst(int forOrAgainst) {
        this.forOrAgainst = forOrAgainst;
    }
}
