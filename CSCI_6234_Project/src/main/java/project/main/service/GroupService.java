package project.main.service;

import project.main.model.Group;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface GroupService {
    Group createGroup(String name, int moderatorId) throws NoSuchAlgorithmException;
    List<Group> getGroups(int userId);
    List<Group> getModerateGroups(int userId);
    Group getGroupFromInviteCode(String inviteCode);
    Group getGroup(int groupId);
}
