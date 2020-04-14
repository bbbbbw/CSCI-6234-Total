package project.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.main.dao.GroupMemberDao;
import project.main.service.GroupMemberService;

@Service("groupMemberService")
public class GroupMemberServiceImpl implements GroupMemberService {
    @Autowired
    GroupMemberDao groupMemberDao;

    @Override
    public int addMember(int groupId, int userId, int relationship) {
        return groupMemberDao.addMember(groupId, userId, relationship);
    }

    @Override
    public boolean memberInGroup(int userId, int groupId) {
        return groupMemberDao.memberInGroup(userId, groupId);
    }

    @Override
    public boolean moderatorOfGroup(int userId, int groupId) {
        return groupMemberDao.moderatorOfGroup(userId, groupId);
    }

    @Override
    public int removeMember(int groupId, int userId) {
        return groupMemberDao.removeMember(groupId, userId);
    }
}
