package project.main.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.main.dao.UserDao;
import project.main.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("password_hash"),
                    rs.getString("phone"), rs.getString("token"), rs.getString("last_login"));
            return user;
        }
    }

    static class UserRowMapper2 implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("phone"));
            return user;
        }
    }

    @Override
    public User getUser(int id) {
        String sql = "select * from `user` where id = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }

    @Override
    public User getUser(String name) {
        String sql = "select * from user where name = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), name);
        if (users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public int login(String name, String passwordHash, String token, String lastLogin) {
        String sql = "update `user` set token = ?, last_login = ? where `name` = ? and password_hash = ?";
        return jdbcTemplate.update(sql, token, lastLogin, name, passwordHash);
    }

    @Override
    public int createUser(String name, String passwordHash, String phone) {
        String sql = "insert into `user` (`name`, password_hash, phone) values (?, ?, ?)";
        return jdbcTemplate.update(sql, name, passwordHash, phone);
    }

    @Override
    public List<User> getGroupMember(int groupId) {
        String sql = "select id, `name`, phone from `user` left join group_member on id = member_id where group_id = ?";
        return jdbcTemplate.query(sql, new UserRowMapper2(), groupId);
    }
}
