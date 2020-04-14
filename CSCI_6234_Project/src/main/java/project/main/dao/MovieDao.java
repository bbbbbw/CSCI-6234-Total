package project.main.dao;

import project.main.model.Movie;

import java.util.List;

public interface MovieDao {
    Movie getMovieById(int movieId);
    List<Movie> getGroupMovie(int groupId);
    Movie getMovieByApiId(String apiId);
    int addMovie(String apiId, String movieName);
    List<Movie> getVoteMovieList(int votingEventId);
}
