package com.notes.api.services;

import com.notes.api.dto.FlashcardReviewDTO;
import com.notes.api.entities.User;
import com.notes.api.entities.review.FlashcardReview;
import com.notes.api.repositories.Review;
import com.notes.api.responses.FlashcardInfo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Service
public class ReviewService {
    Review reviewRepository;

    UserService userService;

    public ReviewService(Review repository) {
        this.reviewRepository = repository;
    }

    public List<FlashcardInfo> getAllReviewFlashcardInfo() {
        User user = userService.getSignedOnUser();
        LocalDateTime date = LocalDateTime.now();
        return reviewRepository.findFlashcardReview(user.getId(), date);
    }

//    TODO - Fix date conversions: Javascript sends a date in local browser time format. Next review
//    TODO - date should be calculated in local browser time format too.
    public Long saveReviewResult(FlashcardReviewDTO reviewDTO) {
        FlashcardReview review = reviewRepository.findByflashcard_id(reviewDTO.getFlashcardId());
        BucketType currentBucket = review.getBucketType();
        if (reviewDTO.getRemembered()) {
            currentBucket = currentBucket.next();
            Long reviewInterval = currentBucket.getInterval();
            LocalDateTime nextReview = review.getLastReviewed().plusDays(reviewInterval);
            review.setNextReview(nextReview);
            review.setTimesRemembered(review.getTimesRemembered() + 1);
        } else {
            currentBucket = currentBucket.reset();
        }
        review.setBucketType(currentBucket);
        review.setTimesReviewed(review.getTimesReviewed() + 1);
        reviewRepository.save(review);
        return review.getId();
    }

    private Date convertToDate(LocalDateTime plusDays) {
        return Date.from(plusDays.toInstant(ZoneOffset.UTC));
    }
}
