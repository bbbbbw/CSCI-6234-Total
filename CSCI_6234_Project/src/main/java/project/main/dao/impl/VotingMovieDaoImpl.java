package project.main.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.main.dao.VotingMovieDao;
import project.main.model.VotingMovie;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository("votingMovieDao")
public class VotingMovieDaoImpl implements VotingMovieDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    static class VotingMovieRowMapper implements RowMapper<VotingMovie> {
        @Override
        public VotingMovie mapRow(ResultSet rs, int rowNum) throws SQLException {
            VotingMovie votingMovie = new VotingMovie(rs.getInt("voting_event_id"), rs.getInt("movie_id"));
            return votingMovie;
        }
    }

    @Override
    public boolean votingMovieExist(int votingEventId, int movieId) {
        String sql = "select * from voting_movie where voting_event_id = ? and movie_id = ?";
        return jdbcTemplate.query(sql, new VotingMovieRowMapper(), votingEventId, movieId).size() > 0;
    }

    @Override
    public int addVotingMovie(int votingEventId, int movieId) {
        String sql = "insert into voting_movie (voting_event_id, movie_id) values (?, ?)";
        return jdbcTemplate.update(sql, votingEventId, movieId);
    }
}
