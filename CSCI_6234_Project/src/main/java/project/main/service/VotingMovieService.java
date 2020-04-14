package project.main.service;

public interface VotingMovieService {
    boolean votingMovieExist(int votingEventId, int movieId);
    int addVotingMovie(int votingEventId, int movieId);
}
