package project.main.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.main.dao.GroupMovieDao;
import project.main.model.GroupMovie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("groupMovieDao")
public class GroupMovieDaoImpl implements GroupMovieDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    static class GroupMovieRowMapper implements RowMapper<GroupMovie> {
        @Override
        public GroupMovie mapRow(ResultSet rs, int rowNum) throws SQLException {
            GroupMovie groupMovie = new GroupMovie(rs.getInt("group_id"), rs.getInt("movie_id"));
            return groupMovie;
        }
    }

    @Override
    public boolean existGroupMovie(int groupId, int movieId) {
        String sql = "select * from group_movie where group_id = ? and movie_id = ?";
        List<GroupMovie> groupMovieList = jdbcTemplate.query(sql, new GroupMovieRowMapper(), groupId, movieId);
        return groupMovieList.size() > 0;
    }

    @Override
    public int populateMovie(int groupId, int movieId) {
        String sql = "insert into group_movie (group_id, movie_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql, groupId, movieId);
    }

    @Override
    public int removeMovie(int groupId, int movieId) {
        String sql = "delete from group_movie where group_id = ? and movie_id = ?";
        return jdbcTemplate.update(sql, groupId, movieId);
    }
}
