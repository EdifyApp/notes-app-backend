package com.notes.api.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class FlashcardBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Flashcard> flashcards;

    @Column
    private long locationIndex;

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

    public long getLocationIndex() {
        return locationIndex;
    }

    public void setLocationIndex(long locationIndex) {
        this.locationIndex = locationIndex;
    }
}
