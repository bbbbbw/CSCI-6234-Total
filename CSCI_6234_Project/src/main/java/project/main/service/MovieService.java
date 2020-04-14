package project.main.service;

import project.main.model.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getGroupMovie(int groupId);
    Movie getMovieById(int movieId);
    Movie addMovie(String apiId, String movieName);
    List<Movie> getVoteMovieList(int votingEventId);
}
