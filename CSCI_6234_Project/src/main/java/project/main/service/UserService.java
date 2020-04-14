package project.main.service;

import project.main.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    User getUser(int id);
    User getUser(String name);
    User login(String name, String password) throws NoSuchAlgorithmException;
    boolean validateUser(int id, String token);
    int createUser(String name, String password, String phone) throws NoSuchAlgorithmException;
    List<User> getGroupMember(int groupId);
}
