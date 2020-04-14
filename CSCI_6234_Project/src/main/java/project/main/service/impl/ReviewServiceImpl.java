package project.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.dao.ReviewDao;
import project.main.model.Review;
import project.main.service.ReviewService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewDao reviewDao;

    @Override
    public int writeReview(int memberId, String memberName, int groupId, int movieId, String review) {
        Date date = new Date();
        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String createdAt = sft.format(date);
        return reviewDao.writeReview(memberId, memberName, groupId, movieId, review, createdAt);
    }

    @Override
    public List<Review> getReview(int movieId, int groupId) {
        return reviewDao.getReview(movieId, groupId);
    }
}
