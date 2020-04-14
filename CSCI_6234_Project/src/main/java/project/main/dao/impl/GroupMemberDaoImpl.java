package project.main.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.main.dao.GroupMemberDao;
import project.main.model.GroupMember;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("groupMemberDao")
public class GroupMemberDaoImpl implements GroupMemberDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    static class GroupMemberRowMapper implements RowMapper<GroupMember> {
        @Override
        public GroupMember mapRow(ResultSet rs, int rowNum) throws SQLException {
            GroupMember groupMember = new GroupMember(rs.getInt("group_id"), rs.getInt("member_id"), rs.getInt("relationship"));
            return groupMember;
        }
    }

    @Override
    public int addMember(int groupId, int userId, int relationship) {
        String sql = "insert into group_member (group_id, member_id, relationship) values (?, ?, ?)";
        return jdbcTemplate.update(sql, groupId, userId, relationship);
    }

    @Override
    public boolean memberInGroup(int userId, int groupId) {
        String sql = "select * from group_member where member_id = ? and group_id = ?";
        List<GroupMember> temp = jdbcTemplate.query(sql, new GroupMemberRowMapper(), userId, groupId);
        return temp.size() > 0;
    }

    @Override
    public boolean moderatorOfGroup(int userId, int groupId) {
        String sql = "select * from group_member where member_id = ? and group_id = ?";
        List<GroupMember> temp = jdbcTemplate.query(sql, new GroupMemberRowMapper(), userId, groupId);
        if (temp.size() == 0) {
            return false;
        } else {
            int relationship = temp.get(0).getRelationship();
            return relationship == 0;
        }
    }

    @Override
    public int removeMember(int groupId, int userId) {
        String sql = "delete from group_member where group_id = ? and member_id = ?";
        return jdbcTemplate.update(sql, groupId, userId);
    }


}
