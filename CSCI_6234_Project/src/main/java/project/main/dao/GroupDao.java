package project.main.dao;

import project.main.model.Group;

import java.util.List;

public interface GroupDao {
    Group createGroup(String name, int moderatorId, String inviteCode);
    List<Group> getGroups(int userId);
    List<Group> getModerateGroups(int userId);
    Group getGroupFromInviteCode(String inviteCode);
    Group getGroup(int groupId);
}
