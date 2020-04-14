package project.main.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.main.dao.WatchingEventDao;
import project.main.model.WatchingEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("watchingEventDao")
public class WatchingEventDaoImpl implements WatchingEventDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    static class WatchingEventRowMapper implements RowMapper<WatchingEvent> {
        @Override
        public WatchingEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
            WatchingEvent watchingEvent = new WatchingEvent(rs.getInt("id"), rs.getInt("group_id"),
                    rs.getString("start_time"), rs.getString("end_time"),
                    rs.getString("title"), rs.getString("message"));
            return watchingEvent;
        }
    }

    @Override
    public int createWatchingEvent(int groupId, String newStartTime, String newEndTime, String message) {
        String sql = "insert into watching_event (group_id, start_time, end_time, message) values (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, groupId, newStartTime, newEndTime, message);
    }

    @Override
    public List<WatchingEvent> getWatchingEvent(int groupId) {
        String sql = "select * from watching_event where group_id = ?";
        return jdbcTemplate.query(sql, new WatchingEventRowMapper(), groupId);
    }

    @Override
    public WatchingEvent getWatchingEventById(int watchingEventId) {
        String sql = "select * from watching_event where id = ?";
        List<WatchingEvent> watchingEventList = jdbcTemplate.query(sql, new WatchingEventRowMapper(), watchingEventId);
        if (watchingEventList.size() == 0) {
            return null;
        } else {
            return watchingEventList.get(0);
        }
    }
}
