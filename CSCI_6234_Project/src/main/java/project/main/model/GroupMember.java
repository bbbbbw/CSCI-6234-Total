package project.main.model;

import java.io.Serializable;

public class GroupMember implements Serializable {
    private int groupId;
    private int memberId;
    private int relationship; // What role a member plays in a group (0: moderator, 1: member)

    public GroupMember() {
    }

    public GroupMember(int groupId, int memberId, int relationship) {
        this.groupId = groupId;
        this.memberId = memberId;
        this.relationship = relationship;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getRelationship() {
        return relationship;
    }

    public void setRelationship(int relationship) {
        this.relationship = relationship;
    }
}
