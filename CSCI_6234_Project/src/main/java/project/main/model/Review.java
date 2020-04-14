package project.main.model;

import java.io.Serializable;

public class Review implements Serializable {
    private int id;
    private int memberId;
    private String memberName;
    private int groupId;
    private int movieId;
    private String review;
    private String createdAt;

    public Review() {
    }

    public Review(int id, int memberId, String memberName, int groupId, int movieId, String review, String createdAt) {
        this.id = id;
        this.memberId = memberId;
        this.memberName = memberName;
        this.groupId = groupId;
        this.movieId = movieId;
        this.review = review;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
