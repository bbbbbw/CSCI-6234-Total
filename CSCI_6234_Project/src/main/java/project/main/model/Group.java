package project.main.model;

import java.io.Serializable;

public class Group implements Serializable {
    private int id;
    private String name;
    private int moderatorId;
    private String inviteCode;

    public Group() {
    }

    public Group(int id, String name, int moderatorId, String inviteCode) {
        this.id = id;
        this.name = name;
        this.moderatorId = moderatorId;
        this.inviteCode = inviteCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(int moderatorId) {
        this.moderatorId = moderatorId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
