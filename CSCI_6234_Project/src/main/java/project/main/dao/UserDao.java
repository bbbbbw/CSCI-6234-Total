package project.main.dao;

import project.main.model.User;

import java.util.List;

public interface UserDao {
    User getUser(int id);
    User getUser(String name);
    int login(String name, String passwordHash, String token, String lastLogin);
    int createUser(String name, String password, String phone);
    List<User> getGroupMember(int groupId);
}
