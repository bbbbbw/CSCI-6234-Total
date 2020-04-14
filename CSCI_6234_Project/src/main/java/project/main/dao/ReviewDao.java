package project.main.dao;

import project.main.model.Review;

import java.util.List;

public interface ReviewDao {
    int writeReview(int memberId, String memberName, int groupId, int movieId, String review, String createdAt);
    List<Review> getReview(int movieId, int groupId);
}
