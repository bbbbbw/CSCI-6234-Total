package project.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.main.dao.MovieDao;
import project.main.model.Movie;
import project.main.service.MovieService;

import java.util.List;

@Service("movieService")
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieDao movieDao;

    @Override
    public List<Movie> getGroupMovie(int groupId) {
        return movieDao.getGroupMovie(groupId);
    }

    @Override
    public Movie getMovieById(int movieId) {
        return movieDao.getMovieById(movieId);
    }

    @Override
    public Movie addMovie(String apiId, String movieName) {
        Movie movie = movieDao.getMovieByApiId(apiId);
        if (movie != null) {
            return movie;
        } else {
            movieDao.addMovie(apiId, movieName);
            return movieDao.getMovieByApiId(apiId);
        }
    }

    @Override
    public List<Movie> getVoteMovieList(int votingEventId) {
        return movieDao.getVoteMovieList(votingEventId);
    }
}
