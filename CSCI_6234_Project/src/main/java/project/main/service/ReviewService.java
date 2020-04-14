package project.main.service;

import project.main.model.Review;

import java.util.List;

public interface ReviewService {
    int writeReview(int memberId, String memberName, int groupId, int movieId, String review);
    List<Review> getReview(int movieId, int groupId);
}
