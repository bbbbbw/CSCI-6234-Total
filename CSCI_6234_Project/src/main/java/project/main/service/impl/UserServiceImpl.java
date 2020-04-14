package project.main.service.impl;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.dao.UserDao;
import project.main.model.User;
import project.main.service.UserService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public User getUser(String name) {
        return userDao.getUser(name);
    }

    @Override
    public User login(String name, String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        String passwordHash = new BigInteger(1, md.digest()).toString(16).toUpperCase();
        String token = UUID.randomUUID().toString().replace("-", "");
        Date date = new Date();
        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String lastLogin = sft.format(date);

        if (userDao.login(name, passwordHash, token, lastLogin) == 1) {
            return userDao.getUser(name);
        } else {
            return null;
        }
    }

    @Override
    public boolean validateUser(int id, String token) {
        User user = userDao.getUser(id);
        if (user != null && user.getToken().equals(token)) {
            return true;
        }
        return false;
    }

    @Override
    public int createUser(String name, String password, String phone) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        String passwordHash = new BigInteger(1, md.digest()).toString(16).toUpperCase();
        return userDao.createUser(name, passwordHash, phone);
    }

    @Override
    public List<User> getGroupMember(int groupId) {
        return userDao.getGroupMember(groupId);
    }
}
