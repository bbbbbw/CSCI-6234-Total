package project.main.dao.impl;

import com.sun.xml.internal.bind.v2.model.core.EnumLeafInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.main.dao.GroupDao;
import project.main.model.Group;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("groupDao")
public class GroupDaoImpl implements GroupDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    static class GroupRowMapper implements RowMapper<Group> {
        @Override
        public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
            Group group = new Group(rs.getInt("id"), rs.getString("name"), rs.getInt("moderator_id"), rs.getString("invite_code"));
            return group;
        }
    }

    @Override
    public List<Group> getGroups(int userId) {
        String sql = "select * from `group` left join group_member on id = group_id where member_id = ?";
        return jdbcTemplate.query(sql, new GroupRowMapper(), userId);
    }

    @Override
    public List<Group> getModerateGroups(int userId) {
        String sql = "select * from `group` left join group_member on id = group_id where member_id = ? and relationship = ?";
        return jdbcTemplate.query(sql, new GroupRowMapper(), userId, 0);
    }

    @Override
    public Group createGroup(String name, int moderatorId, String inviteCode) {
        String sql = "insert into `group` (`name`, moderator_id, invite_code) values (?, ?, ?)";
        int affectedRows = jdbcTemplate.update(sql, name, moderatorId, inviteCode);
        if (affectedRows == 1) {
            String sql2 = "select * from `group` where `name` = ? and moderator_id = ?";
            List<Group> groups = jdbcTemplate.query(sql2, new GroupRowMapper(), name, moderatorId);
            return groups.get(groups.size() - 1);
        } else {
            return null;
        }
    }

    @Override
    public Group getGroupFromInviteCode(String inviteCode) {
        String sql = "select * from `group` where invite_code = ?";
        List<Group> groups = jdbcTemplate.query(sql, new GroupRowMapper(), inviteCode);
        if (groups.size() == 0) {
            return null;
        } else {
            return groups.get(0);
        }
    }

    @Override
    public Group getGroup(int groupId) {
        String sql = "select * from `group` where id = ?";
        return jdbcTemplate.queryForObject(sql, new GroupRowMapper(), groupId);
    }
}
