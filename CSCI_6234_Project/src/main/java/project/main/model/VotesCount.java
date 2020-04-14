package project.main.model;

import java.io.Serializable;

public class VotesCount implements Serializable {
    private int movieId;
    private int voteScore;

    public VotesCount() {
    }

    public VotesCount(int movieId, int voteScore) {
        this.movieId = movieId;
        this.voteScore = voteScore;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getVoteScore() {
        return voteScore;
    }

    public void setVoteScore(int voteScore) {
        this.voteScore = voteScore;
    }
}
