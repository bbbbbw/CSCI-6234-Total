package project.main.dao;

public interface GroupMemberDao {
    int addMember(int groupId, int userId, int relationship);
    boolean memberInGroup(int userId, int groupId);
    boolean moderatorOfGroup(int userId, int groupId);
    int removeMember(int groupId, int userId);
}
