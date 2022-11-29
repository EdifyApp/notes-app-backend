package com.notes.api.repositories;

import com.notes.api.entities.note.Flashcard;
import com.notes.api.responses.FlashcardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {
    List<FlashcardInfo> findAllByUserId(String userId);
}
