package com.notes.api.services;

import com.notes.api.dto.FlashcardReviewDTO;
import com.notes.api.entities.User;
import com.notes.api.entities.review.FlashcardReview;
import com.notes.api.repositories.FlashcardRepository;
import com.notes.api.repositories.ReviewRepository;
import com.notes.api.responses.FlashcardInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    ReviewRepository reviewRepository;

    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    public ReviewService(ReviewRepository repository, UserService userService) {
        this.reviewRepository = repository;
        this.userService = userService;
    }

    public List<FlashcardInfo> getAllReviewFlashcardInfo() {
        String userId = userService.getSignedOnUser().getId();
        LocalDateTime date = LocalDateTime.now();
        return reviewRepository.getAllFlashcardsByUserAndReviewDate(userId, date);
    }

    public Long saveReviewResult(FlashcardReviewDTO reviewDTO) {
        FlashcardReview reviewCard = reviewRepository.findByflashcard_id(reviewDTO.getFlashcardId());
        BucketType currentBucket = reviewCard.getBucketType();
        LocalDateTime reviewCompleted = LocalDateTime.now();
        if (reviewDTO.getRemembered()) {
            currentBucket = currentBucket.next();
            Long reviewInterval = currentBucket.getInterval();
            LocalDateTime nextReview = reviewCompleted.plusDays(reviewInterval);
            reviewCard.setNextReview(nextReview);
            reviewCard.setTimesRemembered(reviewCard.getTimesRemembered() + 1);
        } else {
            currentBucket = currentBucket.reset();
        }

        reviewCard.setLastReviewed(reviewCompleted);
        reviewCard.setBucketType(currentBucket);
        reviewCard.setTimesReviewed(reviewCard.getTimesReviewed() + 1);
        reviewRepository.save(reviewCard);
        return reviewCard.getId();
    }
}

