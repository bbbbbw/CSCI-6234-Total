package project.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.main.dao.GroupDao;
import project.main.model.Group;
import project.main.service.GroupService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Service("groupService")
public class GroupServiceImpl implements GroupService {
    @Autowired
    GroupDao groupDao;

    @Override
    public Group createGroup(String name, int moderatorId) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String randCode = new Date().toString();
        randCode += Double.toString(Math.random());
        md.update(randCode.getBytes());
        String inviteCode = new BigInteger(1, md.digest()).toString(16).toUpperCase();
        return groupDao.createGroup(name, moderatorId, inviteCode);
    }

    @Override
    public List<Group> getGroups(int userId) {
        return groupDao.getGroups(userId);
    }

    @Override
    public List<Group> getModerateGroups(int userId) {
        return groupDao.getModerateGroups(userId);
    }

    @Override
    public Group getGroupFromInviteCode(String inviteCode) {
        return groupDao.getGroupFromInviteCode(inviteCode);
    }

    @Override
    public Group getGroup(int groupId) {
        return groupDao.getGroup(groupId);
    }
}
