package com.notes.api.repositories;

import com.notes.api.entities.note.Flashcard;
import com.notes.api.responses.FlashcardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.notes.api.entities.review.FlashcardReview;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface Review extends JpaRepository<FlashcardReview, Integer> {
    FlashcardReview findByFlashcardId(long id);

    @Query("select fc from Flashcard fc join FlashcardReview fcr " +
            "where fcr.flashcardId = :userId AND fcr.nextReview <= :today")
    List<FlashcardInfo> findAllByUserIdAndNextReviewLessThanEqual(@Param("userId") String userId,
                                                                  @Param("today") LocalDateTime today);
}
