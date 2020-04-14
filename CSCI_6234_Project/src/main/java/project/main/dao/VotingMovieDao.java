package project.main.dao;

public interface VotingMovieDao {
    boolean votingMovieExist(int votingEventId, int movieId);
    int addVotingMovie(int votingEventId, int movieId);
}
