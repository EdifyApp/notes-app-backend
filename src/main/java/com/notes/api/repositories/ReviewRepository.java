package com.notes.api.repositories;

import com.notes.api.entities.note.Flashcard;
import com.notes.api.responses.FlashcardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.notes.api.entities.review.FlashcardReview;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<FlashcardReview, Integer> {
    FlashcardReview findByflashcard_id(long id);

    List<FlashcardReview> findDistinctUserByNextReviewLessThanEqual(LocalDateTime now);

}
