package project.main.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.main.dao.ReviewDao;
import project.main.model.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("reviewDao")
public class ReviewDaoImpl implements ReviewDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    static class ReviewRowMapper implements RowMapper<Review> {
        @Override
        public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
            Review review = new Review(rs.getInt("id"), rs.getInt("member_id"), rs.getString("member_name"),
                    rs.getInt("group_id"), rs.getInt("movie_id"), rs.getString("review"), rs.getString("created_at"));
            return review;
        }
    }

    @Override
    public int writeReview(int memberId, String memberName, int groupId, int movieId, String review, String createdAt) {
        String sql = "insert into review (member_id, member_name, group_id, movie_id, review, created_at) values (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, memberId, memberName, groupId, movieId, review, createdAt);
    }

    @Override
    public List<Review> getReview(int movieId, int groupId) {
        String sql = "select * from review where movie_id = ? and group_id = ?";
        return jdbcTemplate.query(sql, new ReviewRowMapper(), movieId, groupId);
    }
}
