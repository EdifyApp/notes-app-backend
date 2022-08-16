package com.notes.api.mappers;

import com.notes.api.dto.FlashcardDTO;
import com.notes.api.entities.note.Flashcard;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FlashcardDTOToFlashcardMapper {

    public Flashcard toFlashcard(FlashcardDTO flashcardDTO) {
        Flashcard flashcard = new Flashcard();
        flashcard.setId(flashcardDTO.getId());
        flashcard.setAnswer(flashcardDTO.getAnswer());
        flashcard.setQuestion(flashcardDTO.getQuestion());
        return flashcard;
    }

    public abstract List<Flashcard> toFlashcards(List<FlashcardDTO> flashcardDTOS);

    public FlashcardDTO toFlashcard(Flashcard flashcard) {
        FlashcardDTO flashcardDTO = new FlashcardDTO();
        flashcardDTO.setId(flashcard.getId());
        flashcardDTO.setQuestion(flashcard.getQuestion());
        flashcardDTO.setAnswer(flashcard.getAnswer());
        return flashcardDTO;
    }

    public abstract List<FlashcardDTO> toFlashcardDTOs(List<Flashcard> flashcards);
}
