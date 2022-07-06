package com.notes.api.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class FlashcardBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @OneToMany(cascade = CascadeType.ALL)
    List<Flashcard> flashcards;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }
}
