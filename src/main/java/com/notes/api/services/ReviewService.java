package com.notes.api.services;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.notes.api.dto.FlashcardReviewDTO;
import com.notes.api.entities.User;
import com.notes.api.entities.review.FlashcardReview;
import com.notes.api.repositories.FlashcardRepository;
import com.notes.api.repositories.ReviewRepository;
import com.notes.api.responses.FlashcardInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    UserService userService;

    MailService mailService;

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);
    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    public ReviewService(ReviewRepository repository, UserService userService, MailService mailService) {
        this.reviewRepository = repository;
        this.userService = userService;
        this.mailService = mailService;
    }

    public List<FlashcardInfo> getAllReviewFlashcardInfo() {
        String userId = userService.getSignedOnUser().getId();
        LocalDateTime date = LocalDateTime.now();
        return flashcardRepository.findAllByNoteUserIdAndReviewScheduleNextReviewLessThanEqual(userId, date);
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
            reviewCard.setNextReview(reviewCompleted.plusDays(currentBucket.getInterval()));
        }

        reviewCard.setLastReviewed(reviewCompleted);
        reviewCard.setBucketType(currentBucket);
        reviewCard.setTimesReviewed(reviewCard.getTimesReviewed() + 1);
        reviewRepository.save(reviewCard);
        return reviewCard.getId();
    }

    @Scheduled(cron = "0 19 * * *")
    @Transactional(readOnly = true)
    public void sendEmail(){
        List<FlashcardReview> reviews = reviewRepository.findDistinctUserByNextReviewLessThanEqual(LocalDateTime.now());
        reviews.forEach(review -> {
            String userEmail = review.getUser().getEmailAddress();
            logger.info(userEmail);
            try {
                mailService.sendSimpleMessage(userEmail);
            } catch (UnirestException e) {
                logger.info("Email failed to send for" + review.getUser().getName());
            }
        });
    }
}

