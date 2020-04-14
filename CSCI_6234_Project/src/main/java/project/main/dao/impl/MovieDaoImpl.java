package project.main.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.main.dao.MovieDao;
import project.main.model.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("movieDao")
public class MovieDaoImpl implements MovieDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    static class MovieRowMapper implements RowMapper<Movie> {
        @Override
        public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
            Movie movie = new Movie(rs.getInt("id"), rs.getString("api_id"), rs.getString("name"));
            return movie;
        }
    }

    @Override
    public Movie getMovieById(int movieId) {
        String sql = "select * from movie where id = ?";
        return jdbcTemplate.queryForObject(sql, new MovieRowMapper(), movieId);
    }

    @Override
    public List<Movie> getGroupMovie(int groupId) {
        String sql = "select * from movie left join group_movie on id = movie_id where group_id = ?";
        return jdbcTemplate.query(sql, new MovieRowMapper(), groupId);
    }

    @Override
    public Movie getMovieByApiId(String apiId) {
        String sql = "select * from movie where api_id = ?";
        List<Movie> movies = jdbcTemplate.query(sql, new MovieRowMapper(), apiId);
        if (movies.size() == 0) {
            return null;
        } else {
            return movies.get(0);
        }
    }

    @Override
    public int addMovie(String apiId, String movieName) {
        String sql = "insert into movie (api_id, `name`) VALUES (?, ?)";
        return jdbcTemplate.update(sql, apiId, movieName);
    }

    @Override
    public List<Movie> getVoteMovieList(int votingEventId) {
        String sql = "select * from voting_movie left join movie on movie_id = id where voting_event_id = ?";
        return jdbcTemplate.query(sql, new MovieRowMapper(), votingEventId);
    }
}
