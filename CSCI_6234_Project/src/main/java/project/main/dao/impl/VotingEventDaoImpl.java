package project.main.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.main.dao.VotingEventDao;
import project.main.model.VotingEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("votingEventDao")
public class VotingEventDaoImpl implements VotingEventDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    static class VotingEventRowMapper implements RowMapper<VotingEvent> {
        @Override
        public VotingEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
            VotingEvent votingEvent = new VotingEvent(rs.getInt("id"), rs.getInt("watching_event_id"),
                    rs.getString("start_at"), rs.getString("end_at"), rs.getInt("selected_movie_id"));
            return votingEvent;
        }
    }

    @Override
    public int createVotingEvent(int watchingEventId, String startAt, String endAt) {
        String sql = "insert into voting_event (watching_event_id, start_at, end_at) values (?, ?, ?)";
        return jdbcTemplate.update(sql, watchingEventId, startAt, endAt);
    }

    @Override
    public VotingEvent getVotingEvent(int watchingEventId) {
        String sql = "select * from voting_event where watching_event_id = ?";
        List<VotingEvent> eventList = jdbcTemplate.query(sql, new VotingEventRowMapper(), watchingEventId);
        if (eventList.size() == 0) {
            return null;
        } else {
            return eventList.get(0);
        }
    }

    @Override
    public int updateVotingEvent(int watchingEventId, String startAt, String endAt) {
        String sql = "update voting_event set start_at = ?, end_at = ? where watching_event_id = ?";
        return jdbcTemplate.update(sql, startAt, endAt, watchingEventId);
    }

    @Override
    public int updateVotingEventWinner(int votingEventId, int winnerMovieId) {
        String sql = "update voting_event set selected_movie_id = ? where id = ?";
        return jdbcTemplate.update(sql, winnerMovieId, votingEventId);
    }
}
