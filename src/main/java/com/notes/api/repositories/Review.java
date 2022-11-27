package com.notes.api.repositories;

import com.notes.api.responses.FlashcardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.notes.api.entities.review.FlashcardReview;

import java.util.Date;
import java.util.List;

@Repository
public interface Review extends JpaRepository<FlashcardReview, Integer> {
    FlashcardReview findByFlashcardId(long id);

    List<FlashcardInfo> findAllByUserIdAndLastReviewedLessThanEqual(String userId, Date today);
}
